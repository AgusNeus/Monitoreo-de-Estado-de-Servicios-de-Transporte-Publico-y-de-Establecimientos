package domain.entidades.comunidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import domain.Persistencia.EntidadPersistente;
import domain.Persistencia.FormaNotificacionConverter;
import domain.Persistencia.LocalTimeConverter;
import domain.Persistencia.MedioNotificacionConverter;
import componentesExternos.geoRef.entidades.Localidad;
import componentesExternos.geoRef.entidades.Municipio;
import componentesExternos.geoRef.entidades.Provincia;
import domain.entidades.signin.RolUsuario;
import domain.entidades.geolocalizacion.GestorGeolocalizacion;
import domain.entidades.notificaciones.formaDeNotificacion.FormaNotificacion;
import domain.entidades.notificaciones.medioDeNotificaciones.MedioNotificacion;
import domain.entidades.servicios.*;
import domain.entidades.servicios.Incidente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Getter
@Setter
@Entity
@Table
public class Miembro extends EntidadPersistente {
  @Column
  private RolUsuario rol = RolUsuario.MIEMBRO;

  @Column
  @Setter
  @JsonProperty("nombre")
  private String nombre;

  @Column
  @JsonIgnore
  private String apellido;

  @Column
  @JsonIgnore
  private String correoElectronico;

  @Column
  @JsonIgnore
  private String telefono;

  @Transient
  @JsonIgnore
  private Integer gradoDeConfianza;

  @Transient
  @Getter
  @JsonIgnore
  private Map<Comunidad, RolComunidad> comunidadesPertenecientes;

  @ManyToMany
  @JsonIgnore
  private List<Entidad> entidadesAsociadas;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  @CollectionTable(name = "servicios_miembro", joinColumns = @JoinColumn(name = "miembro_id",referencedColumnName = "id"))
  @Column(unique = true)
  @JsonIgnore
  private List<TipoDeServicio> serviciosAsociados;

  //@Embedded
  @JsonIgnore
  private String localizacionProvincia; // para facilitar las cosas los hice string

  //@Embedded
  @JsonIgnore
  private String localizacionMunicipio;

  //@Embedded
  @JsonIgnore
  private String localizacionDepartamento;

  @Convert(converter = MedioNotificacionConverter.class)
  @Column(columnDefinition = "VARCHAR(20)")
  @Setter
  @JsonIgnore
  private MedioNotificacion medioDeNotificacion;

  @Convert(converter = FormaNotificacionConverter.class)
  @Column(columnDefinition = "VARCHAR(20)")
  @Setter
  @JsonIgnore
  private FormaNotificacion formaNotificacion;

  @Embedded
  @JsonIgnore
  private Ubicacion ubicacionActual;

  @Transient
  @JsonIgnore
  private List<Incidente> incidentesDeInteresPropio;

  @Transient
  @JsonIgnore
  private Map<TipoDeServicio, RolServicio> rolesServicios;


  //private List<Rol_Servicio> rolXServicio = new ArrayList<>();

  @Convert(converter = LocalTimeConverter.class)
  @Column(columnDefinition = "TIME")
  @JsonIgnore
  private LocalTime horarioElegido;

  public Miembro() {
    rolesServicios = new HashMap<>();
    incidentesDeInteresPropio = new ArrayList<>();
    serviciosAsociados = new ArrayList<>();
    entidadesAsociadas = new ArrayList<>();
    comunidadesPertenecientes = new HashMap<>();
  }

  //private Rol rol; // se puede cambiar entre roles con el setter

  public void setUbicacionActual(Ubicacion ubicacionActual) {
    this.ubicacionActual = ubicacionActual;
    List<Incidente> incidentesAbiertosDeLasComunidades = new ArrayList<>();
    comunidadesPertenecientes.forEach((unaComunidad, unRol) -> incidentesAbiertosDeLasComunidades.addAll(unaComunidad.getIncidentesAbiertos()));

    GestorGeolocalizacion gestorGeolocalizacion = new GestorGeolocalizacion();
    gestorGeolocalizacion.incidentesCercaDelMiembro(Miembro.this, incidentesAbiertosDeLasComunidades);
  }

  public Miembro(String nombre, String apellido, String correoElectronico, String telefono) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.correoElectronico = correoElectronico;
    this.telefono = telefono;
    //this.comunidadesPertenecientes = comunidadesPertenecientes; // cambiar
    //this.entidadesAsociadas = entidadesAsociadas;// cambiar
    //this.serviciosAsociados = serviciosAsociados;// cambiar
  }

  // SE LLAMA A ESTA FUNCION LUEGO DE CREAR UN MIEMBRO.
  public void definirRoles() {

    for (TipoDeServicio clave : rolesServicios.keySet()) {
      if (serviciosAsociados.contains(clave)) {
        rolesServicios.put(clave, RolServicio.AFECTADO);
      } else {
        rolesServicios.put(clave, RolServicio.OBSERVADOR);
      }
    }
  }

  public void cambiarRolManualmente(TipoDeServicio tipoDeServicio, RolServicio rolServicio) {
    rolesServicios.put(tipoDeServicio, rolServicio);
  }

  public Boolean esAdminEn(Comunidad comunidad) {
    return comunidad.esAdmin(this);
  }

  public List<Servicio> serviciosDeInteres() {

    // Hay que filtrar todas las entidades por las que le interesan al usuario. Luego, dentro de esas
    // entidades ascociadas hay que filtrar por los servicios asociados. Por ultimo, hay que filtrar
    // los servicios que tienen problemas dentro de los que le interesan.

    List<Servicio> serviciosConProblemasConRepetidos = new ArrayList<>();
    List<Servicio> serviciosQueInteresan = new ArrayList<>();
    for (Entidad entidad : entidadesAsociadas) {
      serviciosQueInteresan.addAll(entidad.conseguirServiciosConProblemasDe(serviciosAsociados));// aca
    }

    serviciosConProblemasConRepetidos.addAll(serviciosQueInteresan);
    Set<Servicio> conjunto = new HashSet<>(serviciosConProblemasConRepetidos);
    List<Servicio> serviciosConProblemasSinRepetidos = new ArrayList<>(conjunto);
    return serviciosConProblemasSinRepetidos;
  }

  public void informarIncidente(Establecimiento establecimiento, Entidad entidad, Servicio servicio, String descripcion)
  {
    FactoryIncidente factoryIncidente = FactoryIncidente.getInstance();
    factoryIncidente.crearIncidente(comunidadesPertenecientes, this, servicio, establecimiento, entidad, descripcion);
  }

  public void cerrarIncidente(Incidente incidente) {
    incidente.setQuienCerro(this);
    incidente.setFechaHoraCierre(LocalDateTime.now());
    incidente.getComunidad().cerrarIncidente(incidente, this);
  }

  public void agregarAComunidad(Comunidad comunidad, RolComunidad rolComunidad) {
    comunidadesPertenecientes.put(comunidad, rolComunidad);
  }


  @Transient
  private Timer timer;

  //public void main(String[] args) {
    //ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    // Programa la tarea para que se ejecute en la hora exacta
    //executor.schedule(() -> {
      //timer = new Timer();
      //timer.schedule(task, 0, 30);
    //}, 0, TimeUnit.MINUTES);

    // Cierra el executor después de ejecutar la tarea
   // executor.shutdown();
 // }

  public Boolean leInteresaElIncidente(Incidente unIncidente) {
    List<Servicio> serviciosDeInteres = this.serviciosDeInteres();

    return serviciosDeInteres.contains(unIncidente.getServicio());
  }

  public Boolean esLaHora() {
    return LocalTime.now() == horarioElegido;
  }

 // TimerTask task = new TimerTask() {
  //  public void run() {
   //   List<Incidente> incidentesAbiertosDeLasComunidades = new ArrayList<>();
  //    comunidadesPertenecientes.forEach(unaComunidad -> incidentesAbiertosDeLasComunidades.addAll(unaComunidad.getIncidentesAbiertos()));

   //   GestorGeolocalizacion.incidentesCercaDelMiembro(Miembro.this, incidentesAbiertosDeLasComunidades);
  //  }
 // };


}
