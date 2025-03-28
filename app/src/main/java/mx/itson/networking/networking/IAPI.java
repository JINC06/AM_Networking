package mx.itson.networking.networking;

import java.util.List;

import mx.itson.networking.modelos.Caffenio;
import retrofit2.Call;
import retrofit2.http.GET;


public interface IAPI {

    @GET("/nspv/v2/venues")
    Call<List<Caffenio>> getAllCaffenios();

}
