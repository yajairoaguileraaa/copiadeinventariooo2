package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Adapter.Database;


public class MainActivity extends AppCompatActivity {

    Button crearcuenta;
    EditText edUsername, edPassword;

    Button cerrar;

    Button irinventario;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edUsername = findViewById(R.id.editTextText);
        edPassword = findViewById(R.id.editTextTextPassword);
        irinventario = findViewById(R.id.button2);

        crearcuenta  = (Button)findViewById(R.id.button3);
        crearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearcuenta = new Intent(MainActivity.this, CrearCuenta.class);
                startActivity(crearcuenta);
            }
        });
        irinventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                Database db = new Database(getApplicationContext(),"copiadeinventariooo2",null,1);
                if (usuario.length()==0 || password.length()==0){

                    Toast.makeText(getApplicationContext(),"Contraseña o usuario incorrecto",Toast.LENGTH_SHORT).show();
                }else {
                    if (db.login(usuario,password)==1){
                        Toast.makeText(getApplicationContext(),"Login exitoso",Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("usuario",usuario);
                        editor.apply();
                        startActivity(new Intent(MainActivity.this,Main_Inventarioo.class));
                    }else {
                        Toast.makeText(getApplicationContext(),"Contraseña o usuario incorrecto",Toast.LENGTH_SHORT).show();

                    }
                }
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



