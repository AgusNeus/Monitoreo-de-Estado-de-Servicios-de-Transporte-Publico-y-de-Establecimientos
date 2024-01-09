package domain.entidades.LectorCSV;

import domain.entidades.servicios.ConfigReader;

import java.util.HashMap;
import java.util.Map;

public class ImportadorDeOrganismosDeControl {

    //Dado que uno de los objetivos del Sistema es ayudar a mejorar la calidad de los servicios públicos,
// en esta versión se incorporan como usuarios de la plataforma a las empresas o entidades propietarias de los servicios públicos
// y a los organismos de control (en caso de que existiese por el tipo de servicio). Cada empresa podrá designar una persona a la cual le llegará información resumida sobre las problemáticas de los servicios que se ofrecen. De igual manera, los organismos de control podrán designar una persona con el mismo objetivo. La generación de la información que recibirán estará a cargo de un servicio de software específico que será detallado en la próxima entrega.
//La carga de datos de entidades prestadoras y organismos de control debe poder ser realizada en forma masiva a través de la carga de un archivo CSV.

    Map<String, DatosCSV> diccionarioDatosOrganismos = new HashMap<>();
    String ruta_archivo_org_control = ConfigReader.getPropertyValue("pathOrganismosDeControl");

    public void importarOrganismosDeControl() {
        ParserCSV parserCSV = new ParserCSV();
        diccionarioDatosOrganismos = parserCSV.parserCSV(ruta_archivo_org_control);
    }

}
