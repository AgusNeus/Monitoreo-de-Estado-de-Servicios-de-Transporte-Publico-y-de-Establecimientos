package domain.entidades.servicios;

import java.util.List;

import domain.Persistencia.EntidadPersistente;
import domain.Persistencia.RankingConverter;
import domain.Repositorios.Incidente.RepositorioIncidente;
import domain.entidades.signin.RolUsuario;
import domain.entidades.generadorRankings.GeneradorRanking;
import domain.entidades.Rankings.Ranking;
import lombok.Getter;
import lombok.Setter;
import org.junit.Ignore;

import javax.annotation.Nullable;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="entidadPrestadora")
public class EntidadPrestadora extends EntidadPersistente {

    @Column
    private String nombreEntidad;

    @Transient
    private List<Entidad> entidades;
    // NO SE QUE RELACION IRIA ACA TODO

    @Transient
    private OrganismoDeControl organismoDeControl;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String contacto;


    @Transient
    private Ranking criterio;

    // FK AL ORG DE CONTROL ?? todo



    public void eliminarEntidad(Entidad entidad) {
        entidades.remove(entidad);
    }

    public void agregarEntidad(Entidad entidad) {
        entidades.add(entidad);
    }

    public void obtenerInforme(){
        RepositorioIncidente repo = new RepositorioIncidente();
        GeneradorRanking generadorRanking = GeneradorRanking.getInstance(repo);
        generadorRanking.devolverInformeEntidadPrestadora(this, criterio);
    }

}
