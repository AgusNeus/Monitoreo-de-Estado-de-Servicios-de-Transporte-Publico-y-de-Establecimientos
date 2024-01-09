package componentesExternos.geoRef.interfaces;
import componentesExternos.geoRef.entidades.ListadoDeLocalidades;
import componentesExternos.geoRef.entidades.ListadoDeProvincias;
import componentesExternos.geoRef.entidades.ListadoDeMunicipios;

import componentesExternos.geoRef.entidades.Localidad;
import componentesExternos.geoRef.entidades.Municipio;
import componentesExternos.geoRef.entidades.Provincia;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  GeorefService {
//    @GET("provincias")
//    Call<ListadoDeProvincias> provincias();

    @GET("provincias")
    Call<ListadoDeProvincias> provincias(@Query("campos") String campos);

    @GET("departamentos")
    Call<ListadoDeLocalidades> departamentos(@Query("provincia") int idProvincia);

    @GET("departamentos")
    Call<ListadoDeLocalidades> departamentos(@Query("provincia") int idProvincia, @Query("campos") String campos );

    @GET("departamentos")
    Call<ListadoDeLocalidades> departamentos(@Query("provincia") int idProvincia, @Query("max") int max, @Query("campos") String campos );

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("max") int max, @Query("campos") String campos);
    @GET("localidades")
    Call<ListadoDeLocalidades> localidades(@Query("municipio") int idMunicipio, @Query("max") int max);

    @GET("provincias")
        Call<ListadoDeProvincias> obtenerProvincia(@Query("id") long provinciaId);

    @GET("municipios")
        Call<ListadoDeMunicipios> obtenerMunicipio(@Query("id") long municipioId);

    @GET("localidades")
        Call<ListadoDeLocalidades> obtenerLocalidad(@Query("id") long localidadId);



}


