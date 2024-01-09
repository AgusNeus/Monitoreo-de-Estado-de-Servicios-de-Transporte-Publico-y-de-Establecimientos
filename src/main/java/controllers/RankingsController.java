package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Repositorios.Incidente.RepositorioIncidente;
import domain.Repositorios.Miembro.RepositorioMiembro;
import domain.entidades.Rankings.MayorCantidadReportes;
import domain.entidades.Rankings.Ranking;
import domain.entidades.comunidad.Miembro;
import domain.entidades.comunidad.Miembro;
import domain.entidades.generadorRankings.GeneradorRanking;
import domain.entidades.servicios.Entidad;
import domain.entidades.servicios.Incidente;
import domain.entidades.servicios.IncidentePuestos;
import domain.entidades.servicios.Servicio;
import domain.entidades.signin.RolUsuario;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.apache.poi.ss.formula.functions.Rank;
import server.utils.ICrudViewsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RankingsController implements ICrudViewsHandler{
    RepositorioIncidente repositorioIncidente;

    public RankingsController(RepositorioIncidente repositorioIncidentes){
        repositorioIncidente = repositorioIncidentes;
    }

    @Override
    public void index(Context context){

    }
    @Override
    public void show(Context context) {
        Integer id2 = context.sessionAttribute("id");
        if (id2 == null) {
            context.redirect("/inicio");
            return;  // Asegúrate de salir del método después de redirigir
        }

        //List<Entidad> ranking1 =  generador.getRankingMayorPromedioCierre();
        //List<Entidad> ranking2 = generador.getRankingMayorCantidadReportes();
       // List<Incidente> ranking3 = generador.getRankingMayorGradoImpacto();

    }

    @Override
    public void create(Context context) {

    }
    @Override
    public void save(Context context) {

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

//    public List buscarPorId(Long id) {
//        if (id == 0) {
//            return GeneradorRanking.getInstance().getRankingMayorCantidadReportes();
//        }
//        else if (id == 1) {
//            return GeneradorRanking.getInstance().getRankingMayorGradoImpacto();
//        }
//        else {
//            return GeneradorRanking.getInstance().getRankingMayorPromedioCierre();
//        }
//
//    }
//

    public void showRanking(Context context) throws JsonProcessingException {
        GeneradorRanking generador = GeneradorRanking.getInstance(repositorioIncidente);

        Integer id2 = context.sessionAttribute("id");
        if (id2 == null) {
            context.redirect("/inicio");
            return;  // Asegúrate de salir del método después de redirigir
        }
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        List<Incidente> rank =  generador.generarRanking2();
        logger.log(Level.INFO, "POR ENTRAR AL FOR DE RANKING");
        for (Incidente incidente : rank) {
            logger.log(Level.INFO, "ENTRE AL FOR");
            logger.log(Level.INFO, "Nombre del Incidente: {0}", incidente.getNombre());
            // Agrega más información si es necesario
        }
       
        Map<String, Object> model = new HashMap<>();
        RolUsuario userRole = context.sessionAttribute("tipo_rol");
        if (userRole == RolUsuario.ADMINISTRADOR_PLATAFORMA) {
            model.put("es_admin", context.sessionAttribute("es_admin"));
        }
        // model.put("ranking3", ranking3);
        context.render("rankings.hbs", model);


    }


}

