package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {

    private List<Productos> productosList;

    private Context context;

    public ProductosAdapter(List<Productos> productosList, Context context) {
        this.productosList = productosList;
        this.context = context;
    }

    public void setProductos(List<Productos> productosList) {
        this.productosList = productosList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjetas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNombre.setText(productosList.get(position).getNombre());
        holder.txtCodigo.setText(String.valueOf(productosList.get(position).getCodigo()));
        holder.txtCantidad.setText(productosList.get(position).getCantidad());
        holder.txtPrecio.setText(productosList.get(position).getPrecio());

        Glide.with(context)
                .load(productosList.get(position).getFoto())
                .centerCrop()
                .into(holder.imgFoto);

        holder.checkBox.setChecked(productosList.get(position).isSelected());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition(); // Obtén la posición actual
                if (position != RecyclerView.NO_POSITION) { // Comprueba si la posición es válida
                    productosList.get(position).setSelected(holder.checkBox.isChecked());
                }
            }
        });
    }




    @Override
    public int getItemCount() {
        return productosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFoto;
        private TextView txtNombre;
        private TextView txtCodigo;
        private TextView txtCantidad;
        private TextView txtPrecio;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFoto = itemView.findViewById(R.id.imgFoto);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtCantidad = itemView.findViewById(R.id.txtCantidad);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}