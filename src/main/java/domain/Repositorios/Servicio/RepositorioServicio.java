package domain.Repositorios.Servicio;


import domain.entidades.comunidad.Comunidad;
import domain.entidades.servicios.OrganismoDeControl;
import domain.entidades.servicios.Servicio;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioServicio implements ServicioCRUD {

    @Override
    public Servicio guardar(Servicio servicio) {
        EntityTransaction tx = entityManager().getTransaction();
        if (!tx.isActive())
            tx.begin();

        entityManager().persist(servicio);
        tx.commit();

        return servicio;
    }

    @Override
    public Servicio buscarPorId(Long id) {
        return entityManager().find(Servicio.class, id);
    }

    @Override
    public List<Servicio> buscarTodos() {
        return entityManager().createQuery("from " + Servicio.class.getName()).getResultList();
    }

    @Override
    public void actualizar(Servicio servicio) {
        entityManager().merge(servicio);
    }

    @Override
    public void eliminar(Long id) {
        Servicio servicio = entityManager().find(Servicio.class, id);
        if (servicio != null) {
            entityManager().remove(servicio);
        }
    }

    public Servicio buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("SELECT i FROM Servicio i WHERE i.nombre = :nombre", Servicio.class)
                .setParameter("nombre", nombre) // Establece el valor del parámetro "nombre"
                .getSingleResult();
    }

}

