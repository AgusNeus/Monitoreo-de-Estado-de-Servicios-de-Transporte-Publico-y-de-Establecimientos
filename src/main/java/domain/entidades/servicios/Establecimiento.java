package domain.entidades.servicios;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import domain.Persistencia.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Table
@Entity
public class Establecimiento extends EntidadPersistente {

  @Column
  @JsonProperty("nombre")
  private String nombre;

  @Embedded
  @JsonIgnore
  private Ubicacion ubicacion;

  @ManyToMany
  @JsonIgnore
  public List<Servicio> servicios = new ArrayList<>();

  @Column
  @JsonIgnore
  private String tipoEstablecimiento;

  @ManyToOne
  @JoinColumn(name = "entidad_id", referencedColumnName = "id")
  @JsonIgnore
  private Entidad entidadALaQuePertenece;

  // pensar si vamos a hacer la ubicacion como lat y long separado o q onda todo
  // id entidad ?? todo

  public Establecimiento(String nombre, Ubicacion ubicacion) {
    this.nombre = nombre;
    this.ubicacion = ubicacion;
  }

  public Establecimiento() {

  }

  public void agregarServicio(Servicio servicio) {
    servicios.add(servicio);
  }

  public void eliminarServicio(Servicio servicio) {
    servicios.remove(servicio);
  }

  public void modificarPrestacion(Servicio servicio, Estado estado) {
    servicio.setEstado(estado);
  }

  public List<Servicio> serviciosConProblemasDe(List<TipoDeServicio> unosServicios) {
    List<Servicio> serviciosDenegados =  servicios.stream().filter(unServicio -> unServicio.estaDenegado()).collect(Collectors.toList());
    return serviciosDenegados.stream().filter(unServicio -> unosServicios.contains(unServicio.getTipoServicio())).collect(Collectors.toList());
  } // devuelve una lista con los servicios que hay que agregar a los servicios con interes

}

