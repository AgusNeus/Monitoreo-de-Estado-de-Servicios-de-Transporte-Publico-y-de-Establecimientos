package domain.entidades.notificaciones;

import domain.entidades.comunidad.RolComunidad;
import domain.entidades.notificaciones.notificacion.NotificacionApertura;
import domain.entidades.comunidad.Miembro;
import domain.entidades.servicios.Incidente;

import java.util.Map;


public class NotificarAperturaAMiembros implements Notificador {

    public void notificar(Incidente unIncidente, Map<Miembro, RolComunidad> unosMiembros) {

        NotificacionApertura notificacion = new NotificacionApertura(unIncidente);

        unosMiembros.forEach((unMiembro, unRol) -> unMiembro.getFormaNotificacion().notificar(notificacion, unMiembro));


    }
}
