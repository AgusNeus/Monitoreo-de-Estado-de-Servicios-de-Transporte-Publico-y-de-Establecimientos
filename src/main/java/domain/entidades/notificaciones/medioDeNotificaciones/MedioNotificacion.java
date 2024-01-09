package domain.entidades.notificaciones.medioDeNotificaciones;

import domain.entidades.comunidad.Miembro;
import domain.entidades.notificaciones.notificacion.Notificacion;
import org.apache.commons.mail.EmailException;

public interface MedioNotificacion {
    public void notificar(Notificacion notificacion, Miembro miembro) throws EmailException;


}
