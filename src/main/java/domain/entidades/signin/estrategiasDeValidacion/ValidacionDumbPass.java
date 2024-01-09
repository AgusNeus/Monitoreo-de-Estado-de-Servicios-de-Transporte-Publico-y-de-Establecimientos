package domain.entidades.signin.estrategiasDeValidacion;

import com.unicodelabs.jdp.core.DumbPassword;
import com.unicodelabs.jdp.core.exceptions.IsNullException;
import domain.entidades.signin.EstrategiaValidacion;
import java.io.IOException;

public class ValidacionDumbPass implements EstrategiaValidacion {

  public boolean verificarContrasenia(String contrasenia) {
    boolean valido = false;
    try {
      DumbPassword dumbPasswords = new DumbPassword();
      if (dumbPasswords.checkPassword(contrasenia)) {
        System.out.println("Contraseña débil. Intente una mas fuerte.");
      } else {
        System.out.println("Contraseña válida");
        valido = true;

      }
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    } catch (IsNullException ex) {
      System.out.println(ex.getMessage());
    }
    return valido;
  }
}
