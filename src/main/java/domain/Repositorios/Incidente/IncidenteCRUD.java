package domain.Repositorios.Incidente;


import domain.entidades.servicios.Incidente;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public interface IncidenteCRUD extends WithSimplePersistenceUnit {
    Incidente guardar(Incidente incidente);
    Incidente buscarPorId(Long id);
    List<Incidente> buscarTodos();
    void actualizar(Incidente incidente);
    void eliminar(Long id);
}
