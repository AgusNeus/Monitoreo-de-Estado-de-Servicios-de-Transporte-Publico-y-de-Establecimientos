package domain.Repositorios.Comunidad;

import domain.entidades.comunidad.Comunidad;
import domain.entidades.comunidad.Miembro;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;


import java.util.List;

public interface ComunidadCRUD extends WithSimplePersistenceUnit {
    Comunidad guardar(Comunidad comunidad);
    Comunidad buscarPorId(Long id);
    List<Comunidad> buscarTodos();
    void actualizar(Comunidad comunidad);
    void eliminar(Long id);
    public List<Comunidad> getComunidadesDelMiembro(Integer miembroId);
    
    public List<Miembro> getMiembrosDeComunidad(Comunidad comunidad);
}
