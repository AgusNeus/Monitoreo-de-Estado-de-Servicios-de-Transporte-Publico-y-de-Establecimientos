package domain.Repositorios.EntidadPrestadora;

import controllers.EntidadesYOrganismosController;
import domain.entidades.servicios.EntidadPrestadora;
import domain.entidades.servicios.Establecimiento;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepositorioEntidadPrestadora implements EntidadPrestadoraCRUD {

    @Override
    public EntidadPrestadora guardar(EntidadPrestadora entidadPrestadora) {
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        logger.info("GUARDANDO ENTIDAD PRESTADORA");
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(entidadPrestadora);
        tx.commit();

        return entidadPrestadora;
    }

    @Override
    public EntidadPrestadora buscarPorId(Long id) {
        return entityManager().find(EntidadPrestadora.class, id);
    }

    @Override
    public List<EntidadPrestadora> buscarTodos() {
        return entityManager().createQuery("from " + EntidadPrestadora.class.getName()).getResultList();
    }

    @Override
    public void actualizar(EntidadPrestadora entidadPrestadora) {
        entityManager().merge(entidadPrestadora);
    }

    @Override
    public void eliminar(Long id) {
        EntidadPrestadora entidadPrestadora = entityManager().find(EntidadPrestadora.class, id);
        if (entidadPrestadora != null) {
            entityManager().remove(entidadPrestadora);
        }
    }
}
