package domain.entidades.notificaciones.notificacion;

import domain.entidades.servicios.Incidente;

public class NotificacionRevision extends Notificacion {

    public NotificacionRevision(Incidente unIncidente) {
        incidenteAsociado = unIncidente;
        this.crearAsunto();
        this.crearCuerpo();
    }

    @Override
    public void crearAsunto() {
        asunto =  "Revision manual de incidente. \n Un incidente ubicado cerca suyo en " + incidenteAsociado.getEstablecimiento() + " puede ser revisado!";
    }

    @Override
    public void crearCuerpo() {
        cuerpo = "El servicio" + incidenteAsociado.getServicio() + " presenta un problema. Por favor, le pedimos amablemente si se puede acercar al" +
                " mismo para verificar si el mismo ya fue arreglado." +
                " \n Las observaciones dejadas por la persona que abrio el incidente son: \n" + incidenteAsociado.getObservaciones();
    }

}
