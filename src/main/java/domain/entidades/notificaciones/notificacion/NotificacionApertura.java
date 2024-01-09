package domain.entidades.notificaciones.notificacion;

import domain.entidades.servicios.Incidente;

public class NotificacionApertura extends Notificacion {

    public NotificacionApertura(Incidente unIncidente) {
        incidenteAsociado = unIncidente;
        this.crearAsunto();
        this.crearCuerpo();
    }

   @Override
    public void crearAsunto() {
        asunto =  "Se creo un nuevo incidente del servicio" + incidenteAsociado.getServicio(); // SE DEBERIA AGREGAR INFO PROPIA DEL INCIDENTE
    }

    @Override
    public void crearCuerpo(){
        cuerpo =  incidenteAsociado.getObservaciones(); // a chequear idealizando lo que manda mas adelante
    }

}
