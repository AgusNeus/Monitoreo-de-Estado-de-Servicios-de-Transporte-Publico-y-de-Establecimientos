package domain.entidades.notificaciones.medioDeNotificaciones;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import domain.entidades.comunidad.Miembro;
import domain.entidades.notificaciones.notificacion.Notificacion;

public class WhatsappSender {
    // Find your Account Sid and Token at twilio.com/console
    private String ACCOUNT_SID = "AC8089600acca9fc93c63ba5bee993936f";
    private String AUTH_TOKEN = "941e609076b6204db1db10a32c1b0f77";


    public void main(Notificacion notificacion, Miembro miembro) {
        String cuerpo = notificacion.asunto;
        cuerpo.concat("\n\n");
        cuerpo.concat(notificacion.cuerpo);

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("whatsapp: " + miembro.getTelefono()),
                new PhoneNumber("whatsapp:+14155238886"),
                cuerpo).create();

        System.out.println(message.getSid());
    }
    public void notificar(Notificacion notificacion, Miembro unMiembro) {
        WhatsappSender whatsappSender = new WhatsappSender();
        whatsappSender.main(notificacion, unMiembro);
    }
}