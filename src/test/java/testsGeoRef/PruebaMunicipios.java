package testsGeoRef;

import componentesExternos.geoRef.entidades.*;
import componentesExternos.geoRef.interfaces.ServicioGeoRef;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import com.google.gson.Gson;
public class PruebaMunicipios {

    public static void main(String[] args) throws IOException {
        ServicioGeoRef servicioGeoref = ServicioGeoRef.instancia();
        System.out.println("Seleccione una de las siguientes provincias, ingresando su id:");

        ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();

        listadoDeProvinciasArgentinas.provincias.sort((p1, p2) -> p1.id >= p2.id ? 1 : -1);

        for (Provincia unaProvincia : listadoDeProvinciasArgentinas.provincias) {
            System.out.println(unaProvincia.id + ") " + unaProvincia.nombre);
        }

        Scanner entradaEscaner = new Scanner(System.in);
        int idProvinciaElegida = Integer.parseInt(entradaEscaner.nextLine());

        Optional<Provincia> posibleProvincia = listadoDeProvinciasArgentinas.provinciaDeId(idProvinciaElegida);

        if (posibleProvincia.isPresent()) {
            Provincia provinciaSeleccionada = posibleProvincia.get();
            System.out.println("Municipios de " + provinciaSeleccionada.nombre + ":");

            ListadoDeMunicipios listadoDeMunicipios = servicioGeoref.listadoDeMunicipiosDeProvincia(provinciaSeleccionada.id);

            for (Municipio unMunicipio : listadoDeMunicipios.municipios) {
                System.out.println(unMunicipio.id + ") " + unMunicipio.nombre);
            }
            String provincia = servicioGeoref.obtenerNombreProvincia(42);
            System.out.println(provincia);

            String municipio = servicioGeoref.obtenerNombreMunicipio(420231);
            //String localidad = servicioGeoref.obtenerNombreLocalidad(42);

           System.out.println(municipio);
           // System.out.println(localidad);
            Scanner entradaMunicipio = new Scanner(System.in);
            int idMunicipioElegido = Integer.parseInt(entradaMunicipio.nextLine());

            Optional<Municipio> posibleMunicipio = listadoDeMunicipios.municipioDeId(idMunicipioElegido);

            if (posibleMunicipio.isPresent()) {
                Municipio municipioSeleccionado = posibleMunicipio.get();
                System.out.println("Localidades de " + municipioSeleccionado.nombre + ":");
                Gson gson = new Gson();
                ListadoDeLocalidades listadoDeLocalidades = servicioGeoref.listadoDeLocalidadesDeMunicipio(municipioSeleccionado.id);
                for (Localidad unaLocalidad : listadoDeLocalidades.localidades) {
                    System.out.println(unaLocalidad.id + ") " + unaLocalidad.nombre);
                }
            } else {
                System.out.println("Municipio no encontrado.");
            }
        } else {
            System.out.println("Provincia no encontrada.");
        }
    }


}
