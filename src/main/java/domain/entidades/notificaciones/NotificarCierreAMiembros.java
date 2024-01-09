package domain.entidades.notificaciones;

import domain.entidades.comunidad.RolComunidad;
import domain.entidades.notificaciones.notificacion.NotificacionCierre;
import domain.entidades.comunidad.Miembro;
import domain.entidades.servicios.Incidente;

import java.util.Map;

public class NotificarCierreAMiembros implements Notificador{


    public void notificar(Incidente unIncidente, Map<Miembro, RolComunidad> unosMiembros) {

        NotificacionCierre notificacion = new NotificacionCierre(unIncidente);

        unosMiembros.forEach((unMiembro, unRol) -> unMiembro.getFormaNotificacion().notificar(notificacion, unMiembro));

    }
}
