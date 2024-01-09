package componentesExternos.geoRef.entidades;

import java.util.List;
import java.util.Optional;

public class ListadoDeMunicipios {
  public int cantidad;
  public int total;
  public int inicio;
  public Parametro parametros;
  public List<Municipio> municipios;

  private class Parametro {
    public List<String> campos;
    public int max;
    public List<String> provincia;
  }

  public Optional<Municipio> municipioDeId(int id){
    return this.municipios.stream()
            .filter(p -> p.id == id)
            .findFirst();
  }
}