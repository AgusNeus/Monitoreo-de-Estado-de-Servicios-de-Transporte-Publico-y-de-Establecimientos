package domain.Repositorios.Usuario;

import domain.entidades.comunidad.Miembro;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.Servicio;
import domain.entidades.signin.Usuario;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;


public class RepositorioDeUsuarios implements UsuarioCRUD {

    public RepositorioDeUsuarios() {}

    @PersistenceContext
    @Override
    public Usuario guardar(Usuario usuario) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(usuario);
        tx.commit();

        return usuario;

    }

    @Override
    public Usuario buscarPorId(int id) {
        return entityManager().find(Usuario.class,id);
    }
    public Usuario buscarPorId2(Integer id) {
        return entityManager().find(Usuario.class, id);
    }
    public Usuario buscarPorNombre(String nombre) {
        return entityManager()
                .createQuery("SELECT i FROM Usuario i WHERE i.nombre = :nombre", Usuario.class)
                .setParameter("nombre", nombre)
                .getSingleResult();
    }
    public Usuario buscarPorUsername(String nombre) {
        try {
            return entityManager()
                    .createQuery("SELECT i FROM Usuario i WHERE i.nombreUsuario = :nombre", Usuario.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            // El nombre de usuario no se encontró, devolver null
            return null;
        }
    }
    @Override
    public List<Usuario> buscarTodos() {
        return entityManager().createQuery("from " + Usuario.class.getName()).getResultList();
    }

    @Override
    public void actualizar(Usuario usuario) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();
        entityManager().merge(usuario);
        tx.commit();
    }

    @Override
    public void eliminar(String nombreUsuario) {
        Usuario usuario = entityManager().find(Usuario.class, nombreUsuario);
        if (usuario != null) {
            entityManager().remove(usuario);
        }
    }
}
