package domain.entidades.notificaciones;

import domain.entidades.comunidad.Miembro;
import domain.entidades.servicios.Incidente;

import java.util.List;


public interface Notificador {

    public default void notificar(Incidente unIncidente, List<Miembro> unosMiembros){


    }



}
