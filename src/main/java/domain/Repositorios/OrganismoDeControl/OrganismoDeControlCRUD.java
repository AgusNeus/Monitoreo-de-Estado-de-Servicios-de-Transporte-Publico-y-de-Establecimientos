package domain.Repositorios.OrganismoDeControl;


import domain.entidades.servicios.OrganismoDeControl;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public interface OrganismoDeControlCRUD extends WithSimplePersistenceUnit {
    OrganismoDeControl guardar(OrganismoDeControl organismoDeControl);
    OrganismoDeControl buscarPorId(Long id);
    List<OrganismoDeControl> buscarTodos();
    void actualizar(OrganismoDeControl organismoDeControl);
    void eliminar(Long id);
}
