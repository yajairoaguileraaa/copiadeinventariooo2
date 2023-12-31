package com.example.myapplication;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.UiModeManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.Productos;
import com.example.myapplication.Adapter.ProductosAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main_Inventarioo extends AppCompatActivity {
    private EditText buscador;
    public static List<Productos> productosList = new ArrayList<>();
    public static ProductosAdapter productosAdapter;
    Button crearproducto, boton_eliminar;
    RecyclerView recycleProductos;
    Button salir, btnEditar, btnbuscador;
    Spinner spinner;

    //te veo -_-

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inventario);
        inicializarElementos();

        spinner = findViewById(R.id.spinneeer);

        ArrayList<Estado> estadolista = new ArrayList<>();
        estadolista.add(new Estado(1, "Disponible"));

        estadolista.add(new Estado(2, "No Disponible"));
        ArrayAdapter<Estado> adapter = new ArrayAdapter<>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, estadolista);

        spinner.setAdapter(adapter);

        crearproducto = (Button) findViewById(R.id.boton_crear);
        crearproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearproducto = new Intent(Main_Inventarioo.this, CrearProducto.class);
                startActivity(crearproducto);
            }
        });
        boton_eliminar = (Button) findViewById(R.id.boton_eliminar);
        boton_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ProductoSeleccionado = false;
                for (Productos producto : productosList) {
                    if (producto.isSelected()) {
                        ProductoSeleccionado = true;
                        break;
                    }
                }
                if (!ProductoSeleccionado) {
                    Toast.makeText(Main_Inventarioo.this, "No se ha seleccionado ningún producto", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(Main_Inventarioo.this)
                        .setTitle("Confirmar eliminación")
                        .setMessage("¿Estás seguro de que quieres eliminar los productos seleccionados?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Iterator<Productos> iterator = productosList.iterator();
                                int count = 0;
                                while (iterator.hasNext()) {
                                    Productos producto = iterator.next();
                                    if (producto.isSelected()) {
                                        iterator.remove();
                                        count++;
                                    }
                                }
                                productosAdapter.notifyDataSetChanged();
                                Toast.makeText(Main_Inventarioo.this, "Se han eliminado " + count + " productos", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        Button btnEditarP = findViewById(R.id.btnEditarP);
        btnEditarP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Productos productoSeleccionado = null;
                for (Productos producto : productosList) {
                    if (producto.isSelected()) {
                        if (productoSeleccionado != null) {
                            Toast.makeText(Main_Inventarioo.this, "Solo se puede editar un producto a la vez", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        productoSeleccionado = producto;
                    }
                }
                if (productoSeleccionado == null) {
                    Toast.makeText(Main_Inventarioo.this, "Debe seleccionar un producto para editar", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Main_Inventarioo.this, EditarProducto.class);

                intent.putExtra("PRODUCTO_ID", productoSeleccionado.getId());

                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler1);
        productosAdapter = new ProductosAdapter(productosList, this);
        recyclerView.setAdapter(productosAdapter);

        Button btnbuscarporcodigo = findViewById(R.id.btnbuscador);
        buscador = findViewById(R.id.buscador2);

        btnbuscarporcodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buscadorText = buscador.getText().toString();
                if (!buscadorText.isEmpty()) {
                    long codigo = Long.parseLong(buscadorText);
                    Productos producto = productosAdapter.buscarProductoPorCodigo(codigo);
                    if (producto != null) {
                        producto.setFound(true);
                        int position = productosList.indexOf(producto);
                        productosAdapter.notifyItemChanged(position);
                        recyclerView.smoothScrollToPosition(position);

                    } else {
                        Toast.makeText(Main_Inventarioo.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Main_Inventarioo.this, "El campo de búsqueda está vacío", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btnScan2 = findViewById(R.id.menu_button2);
        btnScan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrador = new IntentIntegrator(Main_Inventarioo.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrador.setPrompt("Lector de codigos");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });

        LinearLayout mainLayout = findViewById(R.id.mainLayoutt);

        int color1 = ContextCompat.getColor(this, R.color.Color2);
        int color2 = ContextCompat.getColor(this, R.color.Color1);
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        if (uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES) {
            mainLayout.setBackgroundColor(color1);
        } else {
            mainLayout.setBackgroundColor(color2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                buscador.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void inicializarElementos() {
        recycleProductos = findViewById(R.id.recycler1);
        recycleProductos.setLayoutManager(new LinearLayoutManager(this));

        productosAdapter = new ProductosAdapter(productosList, this);
        recycleProductos.setAdapter(productosAdapter);
    }

}
