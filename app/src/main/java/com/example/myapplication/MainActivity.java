package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button crearcuenta;

    Button cerrar;

    Button irinventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearcuenta  = (Button)findViewById(R.id.button3);
        crearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearcuenta = new Intent(MainActivity.this, CrearCuenta.class);
                startActivity(crearcuenta);
            }
        });


        irinventario = (Button)findViewById(R.id.button5);
        irinventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irinventario = new Intent(MainActivity.this, Main_Inventarioo.class);
                startActivity(irinventario);
            }
        });












        Button cerrar= (Button) findViewById(R.id.button4);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });







    }




}



