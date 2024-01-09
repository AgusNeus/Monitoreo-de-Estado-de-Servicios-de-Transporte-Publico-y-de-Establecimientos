package domain.Repositorios.EntidadPrestadora;

import domain.entidades.servicios.EntidadPrestadora;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public interface EntidadPrestadoraCRUD extends WithSimplePersistenceUnit {
    EntidadPrestadora guardar(EntidadPrestadora entidadPrestadora);
    EntidadPrestadora buscarPorId(Long id);
    List<EntidadPrestadora> buscarTodos();
    void actualizar(EntidadPrestadora entidadPrestadora);
    void eliminar(Long id);
}
