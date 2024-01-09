package domain.entidades.servicios;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static String CONFIG_FILE = "paths.config";

    public static String getPropertyValue(String propertyName) {
        Properties prop = new Properties();
        FileInputStream input = null;

        try {
            input = new FileInputStream(CONFIG_FILE);
            prop.load(input);

            // Obtener el valor de la propiedad solicitada y devolverlo
            String value = prop.getProperty(propertyName);
            return value;

        // MANEJO DE ERRORES
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "error"; // Si ocurre un error, devolver "error"
    }
}
