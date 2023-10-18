package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;


import com.example.myapplication.Adapter.Productos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;



    public class CrearProducto extends AppCompatActivity {
        Button btnvolver;
        Button btnScan;
        Button buttonTakePicture;
        private EditText editTextNombre;
        private EditText editTextCodigo;
        private EditText editTextCantidad;
        private EditText editTextPrecio;
        private Productos nuevoProducto;
        private String encodedImage;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_crear_producto);



            buttonTakePicture = findViewById(R.id.buttonTakePicture);


            btnScan = findViewById(R.id.btnScan);
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

            buttonTakePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestCameraPermission();
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        takePictureLauncher.launch(takePictureIntent);
                    }
                }
            });

            btnScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrador = new IntentIntegrator(CrearProducto.this);
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

        private static final int REQUEST_CAMERA_PERMISSION = 200;



        private final ActivityResultLauncher<Intent> takePictureLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        int thumbnailSize = 64;
                        Bitmap thumbnailBitmap = ThumbnailUtils.extractThumbnail(imageBitmap, thumbnailSize, thumbnailSize);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        thumbnailBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    }
                }
        );



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

            if (!codigoStr.matches("\\d+")) {
                Toast.makeText(this, "El código solo puede ser numérico", Toast.LENGTH_SHORT).show();
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
            long codigo = Long.parseLong(codigoStr);

            int id = Main_Inventarioo.productosList.size() + 1;

            nuevoProducto = new Productos(id, nombre, codigo, cantidadStr, precioStr, encodedImage);

            boolean seAgrego = Main_Inventarioo.productosList.add(nuevoProducto);

            if (seAgrego) {
                Main_Inventarioo.productosAdapter.notifyDataSetChanged();

                Toast.makeText(this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();

                editTextNombre.setText("");
                editTextCodigo.setText("");
                editTextCantidad.setText("");
                editTextPrecio.setText("");
            } else {
                Toast.makeText(this, "No se pudo crear el producto", Toast.LENGTH_SHORT).show();
            }
        }

        private void requestCameraPermission() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA_PERMISSION);
            }
        }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQUEST_CAMERA_PERMISSION) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permiso de cámara concedidoooo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
