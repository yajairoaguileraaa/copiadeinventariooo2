package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
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
        Productos producto = productosList.get(position);
        holder.txtNombre.setText(producto.getNombre());
        holder.txtCodigo.setText(String.valueOf(producto.getCodigo()));
        holder.txtCantidad.setText(producto.getCantidad());
        holder.txtPrecio.setText(producto.getPrecio());

        Glide.with(context)
                .load(producto.getFoto())
                .centerCrop()
                .into(holder.imgFoto);

        holder.checkBox.setChecked(producto.isSelected());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    productosList.get(position).setSelected(holder.checkBox.isChecked());
                }
            }
        });

        if (producto.isFound()) {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
        }




    }

    @Override
    public int getItemCount() {
        return productosList.size();
    }

    public Productos buscarProductoPorCodigo(long codigo) {
        for (Productos producto : productosList) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
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
