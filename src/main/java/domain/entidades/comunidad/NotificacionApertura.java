package domain.entidades.comunidad;

public class NotificacionApertura extends FactoryNotificacion {

    @Override
    protected FactoryNotificacion crearNotificacion() {
        return new NotificacionApertura();
    }


}
