package testExcel;

import domain.entidades.generadorRankings.FormatoExcel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import domain.entidades.servicios.Entidad;


public class FormatoExcelTest {

    @Test
    public void exportarInforme_DatosValidos_ExportacionExitosa() {
        // Arrange
        List<Entidad> listaRanking = new ArrayList<>();
        Entidad entidad1 = new Entidad("Entidad 1");
        Entidad entidad2 = new Entidad("Entidad 2");
        listaRanking.add(entidad1);
        listaRanking.add(entidad2);

        FormatoExcel formatoExcel = Mockito.spy(FormatoExcel.class);

        // Act
        formatoExcel.exportarInforme(listaRanking);

        // Assert
        // Agrega aquí las aserciones necesarias para verificar el resultado esperado,
        // como verificar si se creó el archivo correctamente o si los datos se exportaron correctamente.
        // Por ejemplo:
        // Assertions.assertTrue(verificarExistenciaArchivo());
        // Assertions.assertTrue(verificarContenidoArchivo());
    }

    // Agrega aquí métodos auxiliares para verificar el resultado esperado
    // en las aserciones del método de prueba.
}