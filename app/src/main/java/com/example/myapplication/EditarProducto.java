package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Adapter.Productos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class EditarProducto extends AppCompatActivity {

    Button btnvolver;
    Button btnScan;

    private EditText editTextNombre;
    private EditText editTextCodigo;
    private EditText editTextCantidad;
    private EditText editTextPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        btnScan = findViewById(R.id.btnScan);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCodigo = findViewById(R.id.editTextCodigo);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        editTextPrecio = findViewById(R.id.editTextPrecio);



        btnvolver = (Button)findViewById(R.id.boton_volver);
        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnvolver = new Intent(EditarProducto.this, Main_Inventarioo.class);
                startActivity(btnvolver);
            }
        });

        int productoId = getIntent().getIntExtra("PRODUCTO_ID", -1);

        Productos producto = null;
        for (Productos p : Main_Inventarioo.productosList) {
            if (p.getId() == productoId) {
                producto = p;
                break;
            }
        }

        if (producto == null) {
            Toast.makeText(this, "No se encontró el producto con el ID proporcionado", Toast.LENGTH_SHORT).show();
            return;
        }

        editTextNombre.setText(producto.getNombre());
        editTextCodigo.setText(Long.toString(producto.getCodigo()));

        editTextCantidad.setText(producto.getCantidad());
        editTextPrecio.setText(producto.getPrecio());

        final Productos productoFinal = producto;

        Button buttonEditarProducto = findViewById(R.id.buttonCrearProducto);
        buttonEditarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productoFinal != null) {
                    new AlertDialog.Builder(EditarProducto.this)
                            .setTitle("Confirmar cambios")
                            .setMessage("¿Guardar cambios?")
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Guardar los cambios
                                    editarProducto(productoFinal);
                                    Intent buttonEditarProducto = new Intent(EditarProducto.this, Main_Inventarioo.class);
                                    startActivity(buttonEditarProducto);
                                }
                            })
                            .setNegativeButton("No", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    Toast.makeText(EditarProducto.this, "No se seleccionó ningún producto para editar", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrador = new IntentIntegrator(EditarProducto.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrador.setPrompt("Lector de codigos");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,Data);
        if (result!= null){
            if (result.getContents() == null){
                Toast.makeText(this,"Lectura cancelada", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                editTextCodigo.setText(result.getContents());
            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, Data);
        }
    }
    private void editarProducto(Productos producto) {
        String nombre = editTextNombre.getText().toString();
        String codigoStr = editTextCodigo.getText().toString();
        String cantidadStr = editTextCantidad.getText().toString();
        String precioStr = editTextPrecio.getText().toString();

        if (!nombre.isEmpty()) {
            producto.setNombre(nombre);
        }

        if (!codigoStr.isEmpty()) {
            long codigo = Long.parseLong(codigoStr);
            producto.setCodigo(codigo);
        }

        if (!cantidadStr.isEmpty()) {
            producto.setCantidad(cantidadStr);
        }

        if (!precioStr.isEmpty()) {
            producto.setPrecio(precioStr);
        }

        Main_Inventarioo.productosAdapter.notifyDataSetChanged();

        Toast.makeText(this, "Producto actualizado con éxito", Toast.LENGTH_SHORT).show();

        // Limpia los campos de texto
        editTextNombre.setText("");
        editTextCodigo.setText("");
        editTextCantidad.setText("");
        editTextPrecio.setText("");
    }
}