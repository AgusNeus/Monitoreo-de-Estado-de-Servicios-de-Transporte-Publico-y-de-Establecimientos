package domain.entidades.LectorCSV;

import controllers.EntidadesPrestadorasController;
import controllers.EntidadesYOrganismosController;
import domain.entidades.servicios.ConfigReader;
import io.javalin.http.Context;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ImportadorDeEntidadesPrestadoras {

    Map<String, DatosCSV> diccionarioDatosEntidades = new HashMap<>();

    public boolean importarEntidadesPrestadoras(String archivoEntidadesPrestadoras, EntidadesYOrganismosController controlador, Context context) {
        System.out.println("ENTRE AL IMPORTADOR");
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        logger.info("ENTRE AL IMPORTADOR");
        ParserCSV parserCSV = new ParserCSV();
        diccionarioDatosEntidades = parserCSV.parserCSV(archivoEntidadesPrestadoras);

        controlador.procesarDiccionario(diccionarioDatosEntidades, context);

        return true;
    }

}




