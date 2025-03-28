package mx.itson.networking.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mx.itson.networking.R;
import mx.itson.networking.modelos.Caffenio;

public class CaffenioAdapter extends RecyclerView.Adapter<CaffenioAdapter.ViewHolder> {

    private List<Caffenio> list;
    private OnCaffenioCallback callback;

    public CaffenioAdapter(List<Caffenio> list) {
        this.list = list;
    }

    public CaffenioAdapter(List<Caffenio> list, OnCaffenioCallback callback) {
        this.list = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venue, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Caffenio caffenio = list.get(position);
        holder.tvNombre.setText( caffenio.getDriveName() );
        holder.tvEstadoCiudad.setText( caffenio.getCity() + ", " + caffenio.getState() );
        holder.tvDireccion.setText( caffenio.getVenueAddress() );

        holder.llContenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null) {
                    callback.onCaffenioSelected(position, caffenio);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvEstadoCiudad, tvDireccion;
        LinearLayout llContenedor;

        public ViewHolder(View view) {
            super(view);
            tvNombre = view.findViewById(R.id.tvNombre);
            tvEstadoCiudad = view.findViewById(R.id.tvEstadoCiudad);
            tvDireccion = view.findViewById(R.id.tvDireccion);
            llContenedor = view.findViewById(R.id.llContenedor);
        }

    }

    public List<Caffenio> getList() {
        return list;
    }

    public void setList(List<Caffenio> list) {
        this.list = list;
    }

    public interface OnCaffenioCallback {
        void onCaffenioSelected(int position, Caffenio caffenio);
    }
}
