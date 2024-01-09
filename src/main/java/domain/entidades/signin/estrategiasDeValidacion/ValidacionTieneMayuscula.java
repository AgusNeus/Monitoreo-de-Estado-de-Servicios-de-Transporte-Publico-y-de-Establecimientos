package domain.entidades.signin.estrategiasDeValidacion;

import domain.entidades.signin.EstrategiaValidacion;

public class ValidacionTieneMayuscula implements EstrategiaValidacion {

  public boolean verificarContrasenia(String contrasenia) {
    return contrasenia.matches(".*[A-Z].*");
  }
  }


//contrasenia.matches("(?=.*[A-Z])")