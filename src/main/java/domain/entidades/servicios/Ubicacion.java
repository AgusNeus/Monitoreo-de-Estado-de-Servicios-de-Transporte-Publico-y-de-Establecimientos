package domain.entidades.servicios;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Embeddable
public class Ubicacion {
  @Column(columnDefinition = "FLOAT")
  private Float latitud;
  @Column(columnDefinition = "FLOAT")
  private Float longitud;

  public Ubicacion(Float latitud, Float longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public Ubicacion() {

  }
}
