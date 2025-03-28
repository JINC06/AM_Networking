package mx.itson.networking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import mx.itson.networking.adapter.CaffenioAdapter;
import mx.itson.networking.modelos.Caffenio;
import mx.itson.networking.networking.IAPI;
import mx.itson.networking.networking.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CaffenioAdapter.OnCaffenioCallback {

    private RecyclerView rvLista;
    private CaffenioAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLista = findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new LinearLayoutManager(this));

        callServiceGetCaffenios();
    }

    private void callServiceGetCaffenios() {
        IAPI interfaz = RetrofitClientInstance.getRetrofitInstance().create(IAPI.class);
        Call<List<Caffenio>> call = interfaz.getAllCaffenios();
        call.enqueue(new Callback<List<Caffenio>>() {
            @Override
            public void onResponse(Call<List<Caffenio>> call, Response<List<Caffenio>> response) {
                if(response.isSuccessful()){

                    List<Caffenio> listado = response.body();
                    String data = new Gson().toJson(listado);
                    Log.e("Datos Caffenio", data);

                    adaptador = new CaffenioAdapter(listado, MainActivity.this);
                    rvLista.setAdapter(adaptador);

                }else{
                    Toast.makeText(MainActivity.this, "Ocurrió un error en la llamada al servicio", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Caffenio>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ocurrió un error en la llamada al servicio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCaffenioSelected(int position, Caffenio caffenio) {
        Intent intent = new Intent(MainActivity.this, DetalleActivity.class);
        intent.putExtra("CAFFENIO", caffenio);
        startActivity(intent);
    }
}