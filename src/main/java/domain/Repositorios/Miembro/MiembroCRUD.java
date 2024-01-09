package domain.Repositorios.Miembro;

import domain.entidades.comunidad.Miembro;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;


import java.util.List;

public interface MiembroCRUD extends WithSimplePersistenceUnit {
    Miembro guardar(Miembro miembro);
    Miembro buscarPorId(Long id);
    List<Miembro> buscarTodos();
    void actualizar(Miembro miembro);
    void eliminar(Long id);
}
