package domain.entidades.signin;

import java.util.ArrayList;
import java.util.List;

public class ControladorDeEstrategiaValidacion{

  private List<EstrategiaValidacion> estrategiasDeValidacion = new ArrayList<>();

  public void agregarTodasLasEstrategias(EstrategiaValidacion... estrategias) {
    estrategiasDeValidacion.addAll(List.of(estrategias));
  }

  public void agregarEstrategia(EstrategiaValidacion estrategia) {
    estrategiasDeValidacion.add(estrategia);
  }


  public boolean verificarContrasenia(String contrasenia) {

    return  estrategiasDeValidacion.stream().allMatch(b -> b.verificarContrasenia(contrasenia));
  }

}
