package controllers;

import domain.Repositorios.EntidadPrestadora.RepositorioEntidadPrestadora;
import domain.Repositorios.OrganismoDeControl.RepositorioOrganismoDeControl;
import domain.entidades.LectorCSV.DatosCSV;
import domain.entidades.servicios.EntidadPrestadora;
import domain.entidades.servicios.OrganismoDeControl;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.UploadedFile;
import server.utils.ICrudViewsHandler;
import domain.entidades.LectorCSV.ImportadorDeEntidadesPrestadoras;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntidadesYOrganismosController implements ICrudViewsHandler {
    private RepositorioEntidadPrestadora repositorioEntidadPrestadora;
    private RepositorioOrganismoDeControl repositorioOrganismosDeControl;

    public EntidadesYOrganismosController(RepositorioOrganismoDeControl repositorioOrganismos, RepositorioEntidadPrestadora repositorioEntidades) {
        this.repositorioEntidadPrestadora = repositorioEntidades;
        this.repositorioOrganismosDeControl = repositorioOrganismos;
    }

    @Override
    public void index(Context context) {
        Integer id2 = context.sessionAttribute("id");
        if (id2 == null) {
            context.redirect("/inicio");
            return;  // Asegúrate de salir del método después de redirigir
        }

        Map<String, Object> model = new HashMap<>();
        List<EntidadPrestadora> entidadPrestadoras = this.repositorioEntidadPrestadora.buscarTodos();
        model.put("entidadPrestadora", entidadPrestadoras);
        context.render("portalCargaDeDatos.hbs", model);
    }

    @Override
    public void show(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repositorioEntidadPrestadora.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidadPrestadora", entidadPrestadora);
        context.render("portalCargaDeDatos.hbs", model);
    }

    @Override
    public void create(Context context) {
        EntidadPrestadora entidadPrestadora = null;
        Map<String, Object> model = new HashMap<>();
        model.put("entidadPrestadora", entidadPrestadora);
        context.render("entidadPrestadora.hbs", model);
    }

    @Override
    public void save(Context context) {

        System.out.println("ENTRE AL METODO SAVE");
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        logger.info("ENTRE AL METODO SAVE");

        UploadedFile archivo = context.uploadedFile("archivo");

        if (archivo != null) {
            System.setProperty("org.eclipse.jetty.util.HttpCookie.maxCommentLength", "-1");


            try {
                System.out.println("ENTRE AL ARCHIVO");
                logger.info("ENTRE AL ARCHIVO");

                InputStream inputStream = archivo.content();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder contenidoCSV = new StringBuilder();
                String linea;

                while ((linea = reader.readLine()) != null) {
                    contenidoCSV.append(linea).append("\n");
                }

                ImportadorDeEntidadesPrestadoras importador = new ImportadorDeEntidadesPrestadoras();

                // Llama a tu importador para procesar y guardar los datos del archivo CSV
                boolean importacionExitosa = importador.importarEntidadesPrestadoras(contenidoCSV.toString(), this, context);

                if (importacionExitosa) {
                    
                    logger.info("ARCHIVO PROCESADO CON EXITO");
                 //   context.result("Archivo CSV procesado y guardado con éxito.");
                } else {
                    logger.info("ERROR AL PROCESAR EL ARCHIVO");
                    context.result("Error al procesar y guardar el archivo CSV.");
                }
            } catch (IOException e) {
                // Maneja cualquier error de lectura del archivo
                logger.info("ERROR AL PROCESAR EL ARCHIVO 2");
                context.result("Error al procesar el archivo.");
            }
        } else {
            // Manejar el caso en el que no se ha subido un archivo o se ha proporcionado un nombre de campo incorrecto
            context.result("No se ha seleccionado ningún archivo CSV.");
        }

    }

    public void confirmacion(Context context) {
        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        logger.info("ESTOY EN CONFIRMACION");
        Map<String, DatosCSV> diccionario = context.sessionAttribute("miDiccionario");

        Map<String, Object> model = new HashMap<>();

        model.put("miDiccionario", diccionario);


        try {
            context.render("confirmarDatos.hbs", model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    /*public void save(Context context) {
        EntidadPrestadora entidadPrestadora = new EntidadPrestadora();
        this.asignarParametros(entidadPrestadora, context);
        this.repositorioEntidadPrestadora.guardar(entidadPrestadora);
        context.status(HttpStatus.CREATED);
        context.redirect("/entidadPrestadora");
    }
*/
    @Override
    public void edit(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repositorioEntidadPrestadora.buscarPorId(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidadPrestadora", entidadPrestadora);
        context.render("entidadPrestadora/entidadPrestadora.hbs", model);

    }

    @Override
    public void update(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repositorioEntidadPrestadora.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(entidadPrestadora, context);
        this.repositorioEntidadPrestadora.actualizar(entidadPrestadora);
        context.redirect("/entidadPrestadora");
    }

    @Override
    public void delete(Context context) {
        EntidadPrestadora entidadPrestadora = (EntidadPrestadora) this.repositorioEntidadPrestadora.buscarPorId(Long.parseLong(context.pathParam("id")));
        this.repositorioEntidadPrestadora.eliminar(Long.parseLong(context.pathParam("id")));
        context.redirect("/entidadPrestadora");
    }

    private void asignarParametros(EntidadPrestadora entidadPrestadora, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            // entidadPrestadora.setNombre(context.formParam("nombre")); //TODO
        }
    }

    public void procesarDiccionario(Map<String, DatosCSV> diccionario, Context context) {

        Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
        logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
        logger.info("ESTOY PROCESANDO EL DICCIONARIO");
        diccionario.forEach((clave, valor) -> {
                    logger.info("EL TIPO DE DATO ACTUAL ES: "+valor.getTipoDato());
                    if ("EntidadPrestadora".equals(valor.getTipoDato())) {
                        logger.info("ESTOY PROCESANDO UNA ENTIDAD PRESTADORA");
                        EntidadPrestadora entidadPrestadora = new EntidadPrestadora();
                        entidadPrestadora.setNombre(valor.getNombre());
                        entidadPrestadora.setApellido(valor.getApellido());
                        entidadPrestadora.setContacto(valor.getContacto());
                        entidadPrestadora.setNombreEntidad(valor.getNombreOrganismo());

                        repositorioEntidadPrestadora.guardar(entidadPrestadora);
                    }

                    else if ("OrganismoControl".equals(valor.getTipoDato())) {
                        logger.info("ESTOY PROCESANDO UN ORGANISMO DE CONTROL");
                        OrganismoDeControl organismoDeControl = new OrganismoDeControl();
                        organismoDeControl.setNombre(valor.getNombre());


                        repositorioOrganismosDeControl.guardar(organismoDeControl);
                    }

                }



        );

        context.sessionAttribute("miDiccionario", diccionario);
        context.redirect("/confirmarDatosPortalCargaDeDatos");
    }
}

