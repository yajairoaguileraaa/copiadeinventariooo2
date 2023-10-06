package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ProductosAdapter;
import com.example.myapplication.Adapter.Productos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main_Inventarioo extends AppCompatActivity {

    public static List<Productos> productosList = new ArrayList<>();
    public static ProductosAdapter productosAdapter;

    Button crearproducto, boton_eliminar;

    RecyclerView recycleProductos;


    Button salir, btnEditar;


    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_inventario);
        inicializarElementos();

        spinner = findViewById(R.id.spinneeer);

        ArrayList<Estado> estadolista = new ArrayList<>();
        estadolista.add(new Estado(1,"Disponible"));

        estadolista.add(new Estado(2,"No Disponible"));
        ArrayAdapter<Estado> adapter = new ArrayAdapter<>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,estadolista);

        spinner.setAdapter(adapter);

        crearproducto  = (Button)findViewById(R.id.boton_crear);
        crearproducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearproducto = new Intent(Main_Inventarioo.this, CrearProducto.class);
                startActivity(crearproducto);
            }
        });

        boton_eliminar = (Button)findViewById(R.id.boton_eliminar);
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





        EditText editTextSearch = findViewById(R.id.editTextSearch);
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String nombre = editTextSearch.getText().toString();
                    List<Productos> productosEncontrados = buscarProductoPorNombre(nombre);
                    if (!productosEncontrados.isEmpty()) {
                        productosAdapter.setProductos(productosEncontrados);
                        productosAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(Main_Inventarioo.this, "No se encontraron productos con ese nombre", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });




    }

    private void inicializarElementos() {
        recycleProductos = findViewById(R.id.recycler1);
        recycleProductos.setLayoutManager(new LinearLayoutManager(this));

        productosAdapter = new ProductosAdapter(productosList,this);
        recycleProductos.setAdapter(productosAdapter);
    }

    public List<Productos> buscarProductoPorNombre(String nombre) {
        List<Productos> productosEncontrados = new ArrayList<>();
        for (Productos producto : productosList) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
