package domain.Persistencia;

import domain.entidades.notificaciones.formaDeNotificacion.AlertarCuandoSucede;
import domain.entidades.notificaciones.formaDeNotificacion.AlertarSinApuro;
import domain.entidades.notificaciones.formaDeNotificacion.FormaNotificacion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class FormaNotificacionConverter implements AttributeConverter<FormaNotificacion, String> {
    @Override
    public String convertToDatabaseColumn(FormaNotificacion formaNotificacion) {
        if (formaNotificacion == null) {
            return null;
        } else {
            String nombreFormaNotificacion = formaNotificacion.getClass().getName().equals("AlertarCuandoSucede") ? "Cuando sucede" : "Sin apuro";
            return nombreFormaNotificacion;
        }
    }

    @Override
    public FormaNotificacion convertToEntityAttribute(String formaNotificacion) {
        FormaNotificacion formaNotificacion2 = null;

        if ("Cuando sucede".equals(formaNotificacion)) {
            formaNotificacion2 = new AlertarCuandoSucede();
        } else if ("Sin apuro".equals(formaNotificacion)) {
            formaNotificacion2 = new AlertarSinApuro();
        }

        return formaNotificacion2;
    }

}