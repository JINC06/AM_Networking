package mx.itson.networking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import mx.itson.networking.adapter.CaffenioAdapter;
import mx.itson.networking.modelos.Caffenio;
import mx.itson.networking.modelos.Reporte;
import mx.itson.networking.networking.IAPI;
import mx.itson.networking.networking.IAPIReporteCiudadano;
import mx.itson.networking.networking.RetrofitClientInstance;
import mx.itson.networking.networking.RetrofitClientInstanceReporteCiudadano;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CaffenioAdapter.OnCaffenioCallback {

    private RecyclerView rvLista;
    private CaffenioAdapter adaptador;
    private Button btnSendReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvLista = findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        btnSendReport = findViewById(R.id.btnSendReport);

        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReporte();
            }
        });
        callServiceGetCaffenios();
    }

    private void sendReporte() {
        btnSendReport.setEnabled(false);
        //Crear el reporte con información
        Reporte nuevoReporte = new Reporte();
        String token = "Bearer a0f4dcad-5903-482f-8982-88ec8bc6156e";

        nuevoReporte.setNombreInteresado("Luis Alvarez");
        nuevoReporte.setDireccion("Niza Galiza #99");
        nuevoReporte.setColonia("NIZA");
        nuevoReporte.setCelular("6221234567");
        nuevoReporte.setCorreo("jinc06@hotmail.com");
        nuevoReporte.setTipo("ALUMBRADO PÚBLICO");
        nuevoReporte.setDescripcion("Esta oscuro no se ve bien por las noches la lampara está rota");
        nuevoReporte.setImagen(readFromRaw(this, R.raw.image));

        IAPIReporteCiudadano interfaz = RetrofitClientInstanceReporteCiudadano.getRetrofitInstance().create(IAPIReporteCiudadano.class);
        Call<Reporte> call = interfaz.postSendReporte(nuevoReporte, token);
        call.enqueue(new Callback<Reporte>() {
            @Override
            public void onResponse(Call<Reporte> call, Response<Reporte> response) {
                if(response.isSuccessful()) {
                    Reporte responseReporte = response.body();
                    Gson gson = new Gson();
                    Log.i("Response", gson.toJson(responseReporte));
                    Toast.makeText(MainActivity.this, "Reporte enviado con éxito", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        String responseError = response.errorBody().string();
                        Log.e("Error", responseError);
                        Toast.makeText(MainActivity.this, responseError, Toast.LENGTH_LONG).show();
                    }catch (Exception ex) {}
                }
                btnSendReport.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Reporte> call, Throwable t) {
                Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_LONG).show();
                btnSendReport.setEnabled(true);
            }
        });
    }

    public String readFromRaw(Context context, int rawResourceId) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getResources().openRawResource(rawResourceId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
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