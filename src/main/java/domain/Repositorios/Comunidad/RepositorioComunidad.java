package domain.Repositorios.Comunidad;

import controllers.EntidadesYOrganismosController;
import domain.entidades.comunidad.Comunidad;
import domain.entidades.comunidad.Miembro;
import domain.entidades.comunidad.RolComunidad;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.Servicio;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class RepositorioComunidad implements ComunidadCRUD {
    @PersistenceContext

    @Override
    public Comunidad guardar(Comunidad comunidad) {
        EntityTransaction tx = entityManager().getTransaction();
        if (!tx.isActive())
            tx.begin();

        entityManager().persist(comunidad);
        tx.commit();

        return comunidad;
    }

    @Override
    public Comunidad buscarPorId(Long id) {
        return entityManager().find(Comunidad.class, id);
    }
    public Comunidad buscarPorId2(Integer id) {
        return entityManager().find(Comunidad.class, id);}

    @Override
    public List<Comunidad> buscarTodos() {
        return entityManager().createQuery("SELECT e FROM Comunidad e", Comunidad.class)
                .getResultList();
    }
    @Override
    public List<Comunidad> getComunidadesDelMiembro(Integer miembroId) {
        return entityManager()
                .createQuery("SELECT c FROM Comunidad c JOIN c.miembrosNuestro m WHERE KEY(m).id = :miembroId", Comunidad.class)
                .setParameter("miembroId", miembroId)
                .getResultList();
    }

    @Override
    public List<Miembro> getMiembrosDeComunidad(Comunidad comunidad) {
        return comunidad.getMiembrosNuestro().keySet().stream().toList();
    }
    @Override
    public void actualizar(Comunidad comunidad) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();
        entityManager().merge(comunidad);
        tx.commit();

    }

    @Override
    public void eliminar(Long id) {
        Comunidad comunidad = entityManager().find(Comunidad.class, id);
        if (comunidad != null) {
            entityManager().remove(comunidad);
        }
    }

    public Comunidad buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("SELECT i FROM Comunidad i WHERE i.nombre = :nombre", Comunidad.class)
                .setParameter("nombre", nombre) // Establece el valor del parámetro "nombre"
                .getSingleResult();
    }

    public void agregarMiembro(Comunidad comunidad, Miembro miembro, String funcion) {
        RolComunidad rol;
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        logger.info("EL ROL ELEGIDO ES "+funcion);

        if("miembro".equals(funcion)) {
            rol = RolComunidad.AFECTADO;
        }
        else {
            rol = RolComunidad.ADMINISTRADOR;
        }

        comunidad.agregarMiembro(miembro, rol);
        //this.actualizar(comunidad);
        this.guardar(comunidad);
        //entityManager().merge(this);
        //entityManager().flush();
    }
}