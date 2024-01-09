package componentesExternos.geoRef.entidades;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Municipio {
  @Transient
  public int id;
  @Column(name="municipio")
  public String nombre;


}

// ESTAS COSAS DEBERIAN SER ENTIDADES ?? TODO
