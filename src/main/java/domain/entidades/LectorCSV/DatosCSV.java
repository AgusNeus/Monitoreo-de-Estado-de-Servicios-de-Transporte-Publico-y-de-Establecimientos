package domain.entidades.LectorCSV;

import lombok.Getter;

@Getter
public class DatosCSV {
    private String nombre;
    private String apellido;
    private String contacto;
    private String nombreOrganismo;
    private String tipoDato;


    public DatosCSV(String nombre, String apellido,String contacto, String nombreOrganismo, String tipoDato) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contacto = contacto;
        this.nombreOrganismo = nombreOrganismo;
        this.tipoDato = tipoDato;
    }
}
