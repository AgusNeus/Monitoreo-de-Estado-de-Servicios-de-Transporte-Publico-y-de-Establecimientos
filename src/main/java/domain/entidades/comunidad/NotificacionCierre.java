package domain.entidades.comunidad;

public class NotificacionCierre extends FactoryNotificacion {

    @Override
    protected FactoryNotificacion crearNotificacion() {
        return new NotificacionCierre();
    }

}
