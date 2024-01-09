package domain.Repositorios.OrganismoDeControl;


import controllers.EntidadesYOrganismosController;
import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.OrganismoDeControl;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RepositorioOrganismoDeControl implements OrganismoDeControlCRUD {

    @Override
    public OrganismoDeControl guardar(OrganismoDeControl organismoDeControl) {
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        logger.info("GUARDANDO ORGANISMO DE CONTROL");

        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(organismoDeControl);
        tx.commit();

        return organismoDeControl;
    }

    @Override
    public OrganismoDeControl buscarPorId(Long id) {
        return entityManager().find(OrganismoDeControl.class, id);
    }

    @Override
    public List<OrganismoDeControl> buscarTodos() {
        return entityManager().createQuery("from " + OrganismoDeControl.class.getName()).getResultList();
    }

    @Override
    public void actualizar(OrganismoDeControl organismoDeControl) {
        entityManager().merge(organismoDeControl);
    }

    @Override
    public void eliminar(Long id) {
        OrganismoDeControl organismoDeControl = entityManager().find(OrganismoDeControl.class, id);
        if (organismoDeControl != null) {
            entityManager().remove(organismoDeControl);
        }
    }
}
