package domain.entidades.comunidad;

import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.Establecimiento;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.Servicio;

import java.time.LocalDateTime;
import java.util.Map;


public class FactoryIncidente {

    public static FactoryIncidente instance;

    private FactoryIncidente() {
    }

    public static FactoryIncidente getInstance() {
        if (instance == null) {
            instance = new FactoryIncidente();
        }
        return instance;
    }

    public void crearIncidente(Map<Comunidad, RolComunidad> comunidades, Miembro miembro, Servicio servicio, Establecimiento establecimiento, Entidad entidad, String descripcion) {
        for (Comunidad comunidad : comunidades.keySet()) {
            Incidente incidente = new Incidente(
                    comunidad,
                    miembro,
                    servicio,
                    establecimiento,
                    entidad,
                    descripcion,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            comunidad.agregarIncidente(incidente, miembro);
            //GeneradorRanking.agregarIncidente(incidente);
        }
    }
}
