package domain.entidades.LectorCSV;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;


import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ParserCSV {

    public Map<String, DatosCSV> parserCSV(String string_archivo) {// recibe el csv y lo lee.

        Map<String, DatosCSV> diccionarioDatos = new HashMap<>();

        try {
            // Configura el parser
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();

            // Crea un lector CSV desde el contenido del string
            CSVReader csvReader = new CSVReaderBuilder(new StringReader(string_archivo))
                    .withCSVParser(csvParser)
                    .build();


            // Lee cada línea del CSV y construye el diccionario
            List<String[]> filas = csvReader.readAll();

            for (String[] fila : filas) {
                if (fila.length >= 6) {
                    String id = fila[0];
                    String nombre = fila[1];
                    String apellido = fila[2];
                    String contacto = fila[3];
                    String nombreOrganismo = fila[4];
                    String tipoDato = fila[5];

                    DatosCSV datos = new DatosCSV(nombre, apellido, contacto, nombreOrganismo, tipoDato);
                    diccionarioDatos.put(id, datos);
                }

            }
        }
            catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }

        return diccionarioDatos;


    }
}





