package domain.entidades.notificaciones;

import domain.entidades.notificaciones.notificacion.NotificacionRevision;
import domain.entidades.comunidad.Miembro;
import domain.entidades.servicios.Incidente;

import java.util.List;

public class NotificarRevisionManual implements Notificador {

    public void notificar(Incidente unIncidente, List<Miembro> unosMiembros) {

        NotificacionRevision notificacion = new NotificacionRevision(unIncidente);

        unosMiembros.forEach(unMiembro -> unMiembro.getFormaNotificacion().notificar(notificacion, unMiembro));
    }
}