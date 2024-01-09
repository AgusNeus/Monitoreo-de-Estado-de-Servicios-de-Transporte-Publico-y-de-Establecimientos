//package domain.services.geoRef.entidades;
//
//import domain.services.geoRef.interfaces.ServicioGeoRef;
//
//import java.io.IOException;
//import java.util.Optional;
//import java.util.Scanner;
//
//public class PruebaDepartamentos {
//
//    public static void main(String[] args) throws IOException {
//      ServicioGeoRef servicioGeoref = ServicioGeoRef.instancia();
//       System.out.println("Seleccione una de las siguientes provincias, ingresando su id:");
//
//        ListadoDeProvincias listadoDeProvinciasArgentinas = servicioGeoref.listadoDeProvincias();
//
//        listadoDeProvinciasArgentinas.provincias.sort((p1, p2) -> p1.id >= p2.id? 1 : -1);
//
//        for(Provincia unaProvincia:listadoDeProvinciasArgentinas.provincias){
//            System.out.println(unaProvincia.id + ") " + unaProvincia.nombre);
//        }
//
//        Scanner entradaEscaner = new Scanner(System.in);
//        int idProvinciaElegida = Integer.parseInt(entradaEscaner.nextLine());
//
//        Optional<Provincia> posibleProvincia = listadoDeProvinciasArgentinas.provinciaDeId(idProvinciaElegida);
//
//        if(posibleProvincia.isPresent()){
//            Provincia provinciaSeleccionada = posibleProvincia.get();
//            ListadoDeDepartamentos departamentosDeLaProvincia = servicioGeoref.listadoDeDepartamentosDeProvincia(provinciaSeleccionada);
//            System.out.println("Los departamentos de la provincia "+ provinciaSeleccionada.nombre +" son:");
//            for(Departamento unDepartamento: departamentosDeLaProvincia.departamentos){
//                System.out.println(unDepartamento.nombre);
//            }
//        }
//        else{
//            System.out.println("No existe la provincia seleccionada");
//        }
//    }
//}