package componentesExternos.geoRef.interfaces;

import componentesExternos.geoRef.entidades.ListadoDeLocalidades;
import componentesExternos.geoRef.entidades.ListadoDeMunicipios;
import componentesExternos.geoRef.entidades.ListadoDeProvincias;
import componentesExternos.geoRef.entidades.Localidad;
import componentesExternos.geoRef.entidades.Municipio;
import componentesExternos.geoRef.entidades.Provincia;
import controllers.EntidadesYOrganismosController;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioGeoRef{
  private static ServicioGeoRef instancia = null;
  private int maximaCantidadRegistrosDefault = 200;
  private String urlApi = "https://apis.datos.gob.ar/georef/api/";
  private Retrofit retrofit;

  private ServicioGeoRef() {
    this.retrofit = new Retrofit.Builder()
            .baseUrl(urlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
  }

  public static ServicioGeoRef instancia(){
    if(instancia== null){
      instancia = new ServicioGeoRef();
    }
    return instancia;
  }

  // ---------- PROVINCIAS -----------------

  public ListadoDeProvincias listadoDeProvincias() throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeProvincias> requestProvinciasArgentinas = georefService.provincias("id, nombre");
    Response<ListadoDeProvincias> responseProvinciasArgentinas = requestProvinciasArgentinas.execute();
    return responseProvinciasArgentinas.body();


  }

  // ---------- MUNICIPIOS -----------------


  public ListadoDeMunicipios listadoDeMunicipiosDeProvincia(int provincia_id) throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeMunicipios> requestListadoDeMunicipios = georefService.municipios(provincia_id, maximaCantidadRegistrosDefault,"id, nombre");
    Response<ListadoDeMunicipios> responseListadoDeMunicipios = requestListadoDeMunicipios.execute();
    return responseListadoDeMunicipios.body();
  }



  public ListadoDeLocalidades listadoDeLocalidadesDeMunicipio(int municipio_id) throws IOException { //todo ver porque en teoria las localidades tienen el mismo nombre que los municipios
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeLocalidades> requestListadoDeLocalidades = georefService.localidades(municipio_id, maximaCantidadRegistrosDefault);
    Response<ListadoDeLocalidades> responseListadoDeLocalidades = requestListadoDeLocalidades.execute();
    return responseListadoDeLocalidades.body();
  }
  public String obtenerNombreProvincia(long provinciaId) throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeProvincias> requestProvincia = georefService.obtenerProvincia(provinciaId);
    Response<ListadoDeProvincias> responseProvincia = requestProvincia.execute();
    return responseProvincia.body().provincias.get(0).getNombre();


  }

  public String obtenerNombreMunicipio(long municipioId) throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeMunicipios> requestMunicipio = georefService.obtenerMunicipio(municipioId);
    Response<ListadoDeMunicipios> responseMunicipio = requestMunicipio.execute();
    return responseMunicipio.body().municipios.get(0).getNombre();
  }

  public String obtenerNombreLocalidad(long localidadId) throws IOException {
    GeorefService georefService = this.retrofit.create(GeorefService.class);
    Call<ListadoDeLocalidades> requestLocalidad = georefService.obtenerLocalidad(localidadId);
    Response<ListadoDeLocalidades> responseLocalidad = requestLocalidad.execute();
    Logger logger = Logger.getLogger(EntidadesYOrganismosController.class.getName());
    logger.setLevel(Level.ALL); // Configura el nivel de registro a ALL o INFO
    logger.info("EL RESPONSE LOCALIDAD ES "+ responseLocalidad.body().localidades.get(0).getNombre());

    return responseLocalidad.body().localidades.get(0).getNombre();

  }

}



