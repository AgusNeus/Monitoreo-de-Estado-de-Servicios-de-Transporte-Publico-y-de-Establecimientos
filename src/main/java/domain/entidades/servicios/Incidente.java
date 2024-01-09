package domain.entidades.servicios;

import domain.Persistencia.EntidadPersistente;
import domain.Persistencia.LocalDateTimeConverter;
import domain.entidades.comunidad.Comunidad;
import domain.entidades.comunidad.Miembro;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table
public class Incidente extends EntidadPersistente {
    @Column
    private String nombre;
    @Column
    private String observaciones;
    @ManyToOne
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    private Comunidad comunidad;
    @OneToOne
    @JoinColumn(name = "quienabrio_id", referencedColumnName = "id")
    private Miembro quienAbrio;
    @OneToOne
    @JoinColumn(name = "quiencerro_id", referencedColumnName = "id")
    private Miembro quienCerro;
    @OneToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;
    @OneToOne
    @JoinColumn(name = "establecimiento_id", referencedColumnName = "id")
    private Establecimiento establecimiento;
    @OneToOne
    @JoinColumn(name = "entidad_id", referencedColumnName = "id")
    private Entidad entidad;
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "fechaHoraApertura", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraApertura;
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "fechaHoraCierre", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraCierre;
    @Column
    private String token;
    public Incidente() {

    }

    // hacer el converter de la fecha todo
    // hacer bien el oneToOne todo
    // no entendi bien como seria el mapeo con servicios y establecimientos todo


    public long duracion(){
        Duration duracion = Duration.between(fechaHoraApertura, fechaHoraCierre);
        long horasTranscurridas = duracion.toMinutes() % 60;
        return horasTranscurridas;
    }

    public Incidente(Comunidad comunidad, Miembro quienAbrio, Servicio servicio, Establecimiento establecimiento, Entidad entidad, String observaciones, LocalDateTime fechaHoraApertura, LocalDateTime fechaHoraCierre) {
        this.comunidad = comunidad;
        this.quienAbrio = quienAbrio;
        this.servicio = servicio;
        this.establecimiento = establecimiento;
        this.entidad = entidad;
        this.observaciones = observaciones;
        this.fechaHoraApertura = fechaHoraApertura;
        this.fechaHoraCierre = fechaHoraCierre;
    }


    public void ponerDisponible() {
        servicio.disponible();
    }

    public Boolean estadoAbierto() {
return (fechaHoraCierre == null);
    }

    public boolean tieneEsteServicio(Servicio unServicio) {
        return servicio == unServicio;
    }

}
