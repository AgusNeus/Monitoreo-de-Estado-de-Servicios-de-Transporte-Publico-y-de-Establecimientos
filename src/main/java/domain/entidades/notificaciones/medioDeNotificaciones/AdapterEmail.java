package domain.entidades.notificaciones.medioDeNotificaciones;

import domain.entidades.comunidad.Miembro;
import domain.entidades.notificaciones.notificacion.Notificacion;

public class AdapterEmail implements MedioNotificacion {
    private EmailSender adapter;

    public void notificar(Notificacion notificacion, Miembro miembro){
        this.adapter.notificar(notificacion, miembro);
    }


}
