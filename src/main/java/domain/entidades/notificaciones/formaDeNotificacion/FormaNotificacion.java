package domain.entidades.notificaciones.formaDeNotificacion;

import domain.entidades.comunidad.Miembro;
import domain.entidades.notificaciones.notificacion.Notificacion;

public interface FormaNotificacion {
    public void notificar(Notificacion unaNotificacion, Miembro unMiembro);

}