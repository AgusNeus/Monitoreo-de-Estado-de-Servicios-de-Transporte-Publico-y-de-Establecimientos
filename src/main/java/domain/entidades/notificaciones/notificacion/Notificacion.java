package domain.entidades.notificaciones.notificacion;
import domain.entidades.servicios.Incidente;
import lombok.Getter;
import lombok.Setter;


@Setter@Getter
public abstract class  Notificacion { //todo hacer abstract para no repetir la logica

    public String asunto;
    public String cuerpo;
    public Incidente incidenteAsociado;

    public abstract void crearAsunto();

    public abstract void crearCuerpo();



}