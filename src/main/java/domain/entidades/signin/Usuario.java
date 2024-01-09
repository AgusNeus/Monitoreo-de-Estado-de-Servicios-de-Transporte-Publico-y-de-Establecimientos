package domain.entidades.signin;

import domain.Persistencia.EntidadPersistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Table
@Entity
public class Usuario extends EntidadPersistente {

  @Column
  private String nombre;

  @Column
  private String nombreUsuario;

  @Column
  private String contrasenia;

  @Column
  private String apellido;

  @Column
  private String email;

  @Column
  @Enumerated(EnumType.STRING)
  private RolUsuario rolUsuario = RolUsuario.MIEMBRO;

  @Column
  private Boolean validado = false;
}