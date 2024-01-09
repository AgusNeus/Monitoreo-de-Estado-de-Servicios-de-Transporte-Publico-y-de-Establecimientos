package domain.Repositorios.Entidad;

import domain.entidades.servicios.Entidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public interface EntidadCRUD extends WithSimplePersistenceUnit {
    Entidad guardar(Entidad entidad);
    Entidad buscarPorId(Long id);
    List<Entidad> buscarTodos();
    void actualizar(Entidad entidad);
    void eliminar(Long id);
}
