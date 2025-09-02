package com.example.proyecto;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Modelo.Comunicado;
import Modelo.Evento;

public class VerComunicados extends AppCompatActivity {

    private EditText fechaSelec;
    private LinearLayout linearLayoutComunicados;
    private Button btnVolver;
    private String usuarioActual;
    private ArrayList<Evento> eventos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ver_comunicados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fechaSelec = findViewById(R.id.fechaSelec);
        linearLayoutComunicados=findViewById(R.id.linearLayoutComunicados);
        btnVolver=findViewById(R.id.btnVolver);

        usuarioActual = getIntent().getStringExtra("usuario");
        eventos = new ArrayList<>();

        cargarComunicados();


        fechaSelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePick = new DatePickerDialog(VerComunicados.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anioSel, int mesSel, int daySel) {
                        String fechaSeleccionada = anioSel + "-" + (mesSel + 1) + "-" + daySel;
                        fechaSelec.setText(fechaSeleccionada);
                        filtrarPorFecha(fechaSeleccionada);
                    }
                }, year, month, day);
                datePick.show();
            }
        });
    }


    public void cargarComunicados(){

        try (InputStream is = getAssets().open("Comunicados.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String linea = null;

            while((linea = reader.readLine()) != null ) {
                String[] partes = linea.split("\\|");
                String tipo = partes[1];
                String titulo = partes[3];
                String desc = partes[4];
                String nombreImg = partes[5];
                mostrarComunicados(titulo, desc, nombreImg);

                if(tipo.equalsIgnoreCase("Evento")){
                    String fecha = partes[7];
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    //Capturar posible excepción al transformar la fecha de un String a Date.
                    try {
                        Date fechaDate = sdf.parse(fecha);
                        Evento eve = new Evento(titulo, desc, nombreImg,fechaDate);
                        eventos.add(eve);
                    }catch (ParseException e){
                        Toast.makeText(this, "Error inesperado. Estamos solucionándolo lo antes posible!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "No se pudo cargar la imagen", Toast.LENGTH_SHORT).show();
        }
    }


    private void mostrarComunicados(String titulo, String desc, String nombreImg) {

        //Contenedor principal
        LinearLayout ly1 = new LinearLayout(this);
        ly1.setOrientation(LinearLayout.VERTICAL);
        ly1.setPadding(16,16,16,16);

        //Título
        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(titulo);
        tvTitulo.setTextSize(18);
        tvTitulo.setTypeface(null, Typeface.BOLD);
        tvTitulo.setPadding(0,0,0,8);

        // Descripción
        TextView tvDescripcion = new TextView(this);
        tvDescripcion.setText(desc);
        tvDescripcion.setPadding(0,0,0,8);

        // Imagen
        ImageView ivImagen = new ImageView(this);
        Bitmap bitmap = cargarImagenDesdeAssets(nombreImg.trim());
        if (bitmap != null) ivImagen.setImageBitmap(bitmap);
        ivImagen.setAdjustViewBounds(true);
        ivImagen.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivImagen.setPadding(0,0,0,8);

        // Agregar al layout
        ly1.addView(tvTitulo);
        ly1.addView(tvDescripcion);
        ly1.addView(ivImagen);

        // Agregar item al layout principal
        linearLayoutComunicados.addView(ly1);
    }

    private void filtrarPorFecha(String fechaSelec){
        linearLayoutComunicados.removeAllViews();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        boolean hayEventos = false;

        for(Evento e: eventos){

            String fechaEvento = sdf.format(e.getFecha());

            if(fechaEvento.equalsIgnoreCase(fechaSelec)){
                mostrarComunicados(e.getTitulo(), e.getDescripcion(), e.getNombreImagen());
                hayEventos = true;
            }
        }
        if(!hayEventos){
            TextView tvMensaje = new TextView(this);
            tvMensaje.setText("No hay eventos para esta fecha.");
            tvMensaje.setTextSize(16);
            tvMensaje.setTypeface(null, Typeface.ITALIC);
            tvMensaje.setPadding(16,16,16,16);
            linearLayoutComunicados.addView(tvMensaje);
        }
    }

    //Método para cargar una imagen desde assets y mostrarla en la aplicación.
    private Bitmap cargarImagenDesdeAssets(String nombreImagen) {
        try {
            InputStream is = getAssets().open(nombreImagen);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void Volver(View view) {
        Intent intent = new Intent(this, Opciones.class);
        startActivity(intent);
    }
}
