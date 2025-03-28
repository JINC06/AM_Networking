package mx.itson.networking;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import mx.itson.networking.modelos.Caffenio;

public class DetalleActivity extends AppCompatActivity {

    private ImageView imgTop;
    private TextView tvNombre, tvEstadoCiudad, tvDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Bundle bundle = getIntent().getExtras();
        Caffenio caffenio = (Caffenio) bundle.getSerializable("CAFFENIO");

        setTitle( caffenio.getDriveName() );

        imgTop = findViewById(R.id.imgTop);
        tvNombre = findViewById(R.id.tvNombre);
        tvEstadoCiudad = findViewById(R.id.tvEstadoCiudad);
        tvDireccion = findViewById(R.id.tvDireccion);

        Picasso.get()
                .load(caffenio.getVenueImage())
                .into(imgTop);

        tvNombre.setText( caffenio.getDriveName() );
        tvEstadoCiudad.setText( caffenio.getCity() + ", " + caffenio.getState() );
        tvDireccion.setText( caffenio.getVenueAddress() );

    }
}