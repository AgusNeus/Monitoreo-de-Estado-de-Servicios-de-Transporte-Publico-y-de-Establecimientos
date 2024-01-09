package domain.entidades.servicios;

public class IncidentePuestos {
    private  Incidente incidente;
    private String nombreIncidente;
    private int puesto;

    public IncidentePuestos(Incidente incidente, int puesto) {
        this.incidente = incidente;
        this.nombreIncidente = incidente.getNombre();  // Agrega el nombre del incidente
        this.puesto = puesto;
    }
    public String getNombre() {
        return nombreIncidente;
    }

    public String getComunidad() {
        return incidente.getComunidad().getNombre();
    }

    public String getQuienAbrio() {
        return incidente.getQuienAbrio().getNombre();
    }

    // Getter para incidente
    public Incidente getIncidente() {
        return incidente;
    }

    // Getter para puesto
    public int getPuesto() {
        return puesto;
    }
}
