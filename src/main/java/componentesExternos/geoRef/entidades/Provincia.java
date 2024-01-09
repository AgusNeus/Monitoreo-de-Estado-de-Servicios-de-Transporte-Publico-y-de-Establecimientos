package componentesExternos.geoRef.entidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Getter
@Setter
@Embeddable
public class Provincia {
  @Transient
  public int id;
  @Column(name="provincia")
  public String nombre;

  public int id() {
    return id;
  }
}
