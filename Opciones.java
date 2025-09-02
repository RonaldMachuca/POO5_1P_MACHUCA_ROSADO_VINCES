package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Opciones extends AppCompatActivity {

    private Button btnVerComunicados;
    private Button btnPublicarComunicados;
    private Button btnTableroComunicados;
    private Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_opciones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnVerComunicados = findViewById(R.id.btnVerComunicados);
        btnPublicarComunicados = findViewById(R.id.btnPublicarComunicados);
        btnTableroComunicados = findViewById(R.id.btnTableroComunicados);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnPublicarComunicados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Opciones.this, Publicar.class);
                startActivity(intent);
            }
        });

        btnVerComunicados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Opciones.this, VerComunicados.class);
                startActivity(intent);
            }
        });
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Opciones.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnTableroComunicados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Opciones.this, TableroComunicados.class);
                startActivity(intent);
            }
        });
    }
}