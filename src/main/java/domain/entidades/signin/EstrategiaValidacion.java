package domain.entidades.signin;


public interface EstrategiaValidacion {
  
  boolean verificarContrasenia(String contrasenia);

}


// REGEX:("^(?!.*(.)\1{3,})(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$");