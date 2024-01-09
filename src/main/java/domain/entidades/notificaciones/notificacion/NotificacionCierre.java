package domain.entidades.notificaciones.notificacion;

import domain.entidades.servicios.Incidente;

public class NotificacionCierre extends Notificacion {

    public NotificacionCierre(Incidente unIncidente) {
        incidenteAsociado = unIncidente;
        this.crearAsunto();
        this.crearCuerpo();
    }

    @Override
    public void crearAsunto() {
        asunto = "Se ha cerrado el incidente "; // SE DEBERIA AGREGAR INFO PROPIA DEL INCIDENTE
    }

    @Override
    public void crearCuerpo() {
        asunto =  incidenteAsociado.getObservaciones(); // a chequear idealizando lo que manda mas adelante
    }
}