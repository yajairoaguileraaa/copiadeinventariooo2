package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Adapter.Productos;


public class CrearProducto extends AppCompatActivity {

    Button btnvolver;

    private EditText editTextNombre;
    private EditText editTextCodigo;
    private EditText editTextCantidad;
    private EditText editTextPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCodigo = findViewById(R.id.editTextCodigo);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        editTextPrecio = findViewById(R.id.editTextPrecio);

        Button buttonCrearProducto = findViewById(R.id.buttonCrearProducto);
        buttonCrearProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearProducto();
            }

        });

        btnvolver = (Button)findViewById(R.id.boton_volver);
        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnvolver = new Intent(CrearProducto.this, Main_Inventarioo.class);
                startActivity(btnvolver);
            }
        });




    }





    private void crearProducto() {
        String nombre = editTextNombre.getText().toString();
        String codigoStr = editTextCodigo.getText().toString();
        String cantidadStr = editTextCantidad.getText().toString();
        String precioStr = editTextPrecio.getText().toString();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "El nombre del producto es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        if (codigoStr.isEmpty()) {
            Toast.makeText(this, "El código del producto es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cantidadStr.isEmpty()) {
            Toast.makeText(this, "La cantidad del producto es obligatoria", Toast.LENGTH_SHORT).show();
            return;
        }

        if (precioStr.isEmpty()) {
            Toast.makeText(this, "El precio del producto es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidad = Integer.parseInt(cantidadStr);
        double precio = Double.parseDouble(precioStr);
        int codigo = Integer.parseInt(codigoStr);

        // Crear un nuevo producto
        int id = Main_Inventarioo.productosList.size() + 1;
        Productos nuevoProducto = new Productos(id, nombre, codigoStr, cantidadStr, precioStr, "booj1");

        // Añadir el nuevo producto a la lista en MainInventario
        boolean seAgrego = Main_Inventarioo.productosList.add(nuevoProducto);

        if (seAgrego) {
            Main_Inventarioo.productosAdapter.notifyDataSetChanged();

            Toast.makeText(this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();

            // Limpia los campos de texto
            editTextNombre.setText("");
            editTextCodigo.setText("");
            editTextCantidad.setText("");
            editTextPrecio.setText("");
        } else {
            Toast.makeText(this, "No se pudo crear el producto", Toast.LENGTH_SHORT).show();
        }

    }
}
