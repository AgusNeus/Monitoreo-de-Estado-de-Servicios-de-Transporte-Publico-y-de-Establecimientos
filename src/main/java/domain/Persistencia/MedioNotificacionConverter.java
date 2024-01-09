package domain.Persistencia;

import domain.entidades.notificaciones.medioDeNotificaciones.AdapterEmail;
import domain.entidades.notificaciones.medioDeNotificaciones.AdapterWhatsApp;
import domain.entidades.notificaciones.medioDeNotificaciones.MedioNotificacion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MedioNotificacionConverter implements AttributeConverter<MedioNotificacion, String> {
    @Override
    public String convertToDatabaseColumn(MedioNotificacion medioNotificacion) {
        if (medioNotificacion == null) {
            return null;
        }

        String nombreMedioNotificacion = (medioNotificacion instanceof AdapterWhatsApp) ? "Whatsapp" : "Email";
        return nombreMedioNotificacion;
    }



    @Override
    public MedioNotificacion convertToEntityAttribute(String medioNotificacion) {
        MedioNotificacion medioNotificacion1 = null;

        if ("Whatsapp".equals(medioNotificacion)) {
            medioNotificacion1 = new AdapterWhatsApp();
        } else if ("Email".equals(medioNotificacion)) {
            medioNotificacion1 = new AdapterEmail();
        }

        return medioNotificacion1;
    }

}