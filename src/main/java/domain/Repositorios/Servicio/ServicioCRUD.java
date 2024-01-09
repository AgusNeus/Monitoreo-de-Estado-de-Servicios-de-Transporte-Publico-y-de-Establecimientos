package domain.Repositorios.Servicio;


import domain.entidades.servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.util.List;

public interface ServicioCRUD extends WithSimplePersistenceUnit {
    Servicio guardar(Servicio servicio);
    Servicio buscarPorId(Long id);
    List<Servicio> buscarTodos();
    void actualizar(Servicio servicio);
    void eliminar(Long id);
}
