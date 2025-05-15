package mx.itson.networking.networking;

import java.util.List;

import mx.itson.networking.modelos.Caffenio;
import mx.itson.networking.modelos.Reporte;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IAPIReporteCiudadano {

    @POST("/apireporte/reporte.php")
    Call<Reporte> postSendReporte(@Body Reporte reporte, @Header("Authorization") String token);

}
