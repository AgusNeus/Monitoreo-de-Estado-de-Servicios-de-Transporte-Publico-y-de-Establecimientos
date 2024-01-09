package domain.Repositorios.Establecimiento;


import domain.entidades.servicios.Establecimiento;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public interface EstablecimientoCRUD extends WithSimplePersistenceUnit {
    Establecimiento guardar(Establecimiento establecimiento);
    Establecimiento buscarPorId(Long id);
    List<Establecimiento> buscarTodos();
    void actualizar(Establecimiento establecimiento);
    void eliminar(Long id);
}
