package domain.Repositorios.Entidad;

import domain.entidades.servicios.Entidad;

import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioEntidad implements EntidadCRUD {

    @Override
    public Entidad guardar(Entidad entidad) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(entidad);
        tx.commit();

        return entidad;
    }

    @Override
    public Entidad buscarPorId(Long id) {
        return entityManager().find(Entidad.class, id);
    }

    @Override
    public List<Entidad> buscarTodos() {
        return entityManager().createQuery("from " + Entidad.class.getName()).getResultList();
    }

    @Override
    public void actualizar(Entidad entidad) {
        entityManager().merge(entidad);
    }

    @Override
    public void eliminar(Long id) {
        Entidad entidad = entityManager().find(Entidad.class, id);
        if (entidad != null) {
            entityManager().remove(entidad);
        }
    }
}
