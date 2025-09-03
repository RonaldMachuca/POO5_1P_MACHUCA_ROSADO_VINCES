package com.example.proyecto;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

import Modelo.Anuncio;
import Modelo.Comunicado;
import Modelo.Evento;
import Modelo.datosIncompletosException;

/**
 * Activity para publicar comunicados en la aplicación, carga el layout: activity_publicar.xml
 * <p> Permite crear anuncios y eventos, adjuntar imágenes, seleccionar audiencia, elegir área y urgencia.
 * También valida los campos antes de guardar el comunicado en archivo. </p> */
public class Publicar extends AppCompatActivity {

    /** Texto fijo "lugar", se inicializa para ocultar/mostrar acorde al tipo de comunicado seleccionado. */
    private TextView tvLugar;

    /** Texto fijo "Urgencia", se inicializa para ocultar/mostrar acorde al tipo de comunicado seleccionado. */
    private TextView tvUrgencia;

    /** Texto fijo "Fecha", se inicializa para ocultar/mostrar acorde al tipo de comunicado seleccionado. */
    private TextView tvFecha;

    /** Campo de texto donde el usuario ingresa el título del comunicado. */
    private EditText titulo;

    /** Campo de texto donde el usuario ingresa la descripción del comunicado. */
    private EditText descripcion;

    /** Campo de texto donde el usuario ingresa el lugar del evento. Solo aparece cuando se selecciona Evento. */
    private EditText lugar;

    /** Campo de texto que permite seleccionar una fecha. Se abre un DatePicker (Calendario) al hacer clic y se actualiza con la fecha elegida. */
    private EditText fecha;

    /** Botón que permite al usuario seleccionar y adjuntar una imagen al comunicado desde la galeria del dispositivo. */
    private Button btnAdjuntarImagen;

    /** Botón que valida los campos y publica el comunicado (Anuncio o Evento). Si lo publica correctamente, limpia todos los campos. */
    private Button btnPublicar;

    /** Botón que cancela la publicación y regresa a la pantalla de Opciones. */
    private Button btnCancelar;

    /** Grupo de botones que permite seleccionar el tipo de comunicado (Anuncio o Evento). */
    private RadioGroup grupoTipo;

    /** Botón de opción para seleccionar que el comunicado es un Anuncio. */
    private RadioButton anuncio;

    /** Botón de opción para seleccionar que el comunicado es un Evento. */
    private RadioButton evento;

    /** Casilla que indica que el comunicado está dirigido a los estudiantes. */
    private CheckBox ckEstudiantes;

    /** Casilla que indica que el comunicado está dirigido a los profesores. */
    private CheckBox ckProfesores;

    /** Casilla que indica que el comunicado está dirigido al personal de administración. */
    private CheckBox ckAdministracion;

    /** Spinner que permite seleccionar el área a la que pertenece el comunicado. */
    private Spinner spArea;

    /** Spinner que permite seleccionar la urgencia del comunicado. Visible solo para Anuncios */
    private Spinner spUrgencia;

    /** Fecha seleccionada para un evento. Se actualiza al usar el DatePicker (Calendario). */
    private Date fechaSeleccionada;

    /** Lanzador para seleccionar una imagen desde la galería mediante un Intent. */
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    /** URI de la imagen seleccionada por el usuario. */
    private Uri imageUriSeleccionada;

    /** Contenedor de imagen que muestra la imagen seleccionada por el usuario. */
    private ImageView imagenSeleccionada;

    /** Nombre del archivo de la imagen seleccionada, generado automáticamente. */
    private String nombreImg;


    /** Método que se ejecuta al crear la actividad.
     * <p> Inicializa la interfaz de usuario y configura los listeners para la selección de tipo de comunicado, fecha y manejo de imágenes.
     * También configura los spinners con sus valores y ajusta la visibilidad de los campos según el tipo de comunicado. </p>
     * @param savedInstanceState Estado previamente guardado de la actividad. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_publicar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btnPublicarComunicados), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** Inicializa los componentes de la interfaz y configura los spinners y listeners.
         * Se enlazan los widgets del layout con sus variables correspondientes mediante {@code findViewById}. */
        grupoTipo = findViewById(R.id.rbTipo);
        titulo = findViewById(R.id.label_titulo);
        lugar = findViewById(R.id.label_lugar);
        descripcion = findViewById(R.id.label_descripcion);
        tvFecha = findViewById(R.id.tvFecha);
        fecha = findViewById(R.id.label_fecha);
        anuncio = findViewById(R.id.rbAnuncio);
        evento = findViewById(R.id.rbEvento);
        ckEstudiantes = findViewById(R.id.ckEstudiantes);
        ckProfesores = findViewById(R.id.ckProfesores);
        ckAdministracion = findViewById(R.id.ckAdministrativo);
        tvLugar = findViewById(R.id.txvLugar);
        tvUrgencia = findViewById(R.id.txvUrgencia);
        btnAdjuntarImagen = findViewById(R.id.btnAdjuntarImagen);
        btnPublicar = findViewById(R.id.btnPublicar);
        btnCancelar = findViewById(R.id.btnCancelar);
        imagenSeleccionada = findViewById(R.id.imagenSeleccionada);

        /** Se configuran los spinners {@link #spArea} y {@link #spUrgencia} con sus adaptadores para mostrar las opciones definidas en {@code arrays.xml}. */
        spArea = findViewById(R.id.spArea);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spArea, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spArea.setAdapter(adapter);

        spUrgencia = findViewById(R.id.spUrgencia);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.spUrgencia, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUrgencia.setAdapter(adapter2);

         /** Se establece un listener para el {@link RadioGroup rbTipo} que llama a {@link #actualizarVisibilidad()} cada vez que se selecciona un tipo de comunicado. */
        RadioGroup rbTipo = findViewById(R.id.rbTipo);
        rbTipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup grupo, int checkedId) {
                actualizarVisibilidad();
            }
        });
        actualizarVisibilidad();


        /** Método general compartido en clase.
         * Permite al usuario seleccionar una fecha mediante un DatePickerDialog (Abre un calendario).
         * Al seleccionar una fecha, actualiza el texto del {@link EditText fecha} y guarda el valor en {@link #fechaSeleccionada}.*/
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePick = new DatePickerDialog(Publicar.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int anioSel, int mesSel, int daySel) {
                        String SelectedDate = String.format("%02d/%02d/%04d", daySel, mesSel + 1, anioSel);
                        fecha.setText(SelectedDate);

                        Calendar fecha = Calendar.getInstance();
                        fecha.set(anioSel, mesSel, daySel);
                        fechaSeleccionada = fecha.getTime();
                    }
                }, year, month, day);
                datePick.show();
            }
        });


        /** Método general compartido en clase.
         * Configura el {@link ActivityResultLauncher} para seleccionar una imagen desde la galería del dispositivo.
         * <p> Cuando el usuario selecciona una imagen:
         * Se guarda en almacenamiento interno de la app.
         * Se actualiza la {@link #imagenSeleccionada} para mostrarla en la interfaz.
         * Se guarda la URI y el nombre del archivo en {@link #imageUriSeleccionada} y {@link #nombreImg}. </p> */
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // manejar aquí la imagen seleccionada
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri originalUri = result.getData().getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(originalUri);
                            String nombreArchivo = "img_" + System.currentTimeMillis() + ".jpg";
                            File file = new File(getFilesDir(), nombreArchivo);
                            FileOutputStream outputStream = new FileOutputStream(file);
                            byte[] buffer = new byte[1024];
                            int length;

                            while ((length = inputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }
                            inputStream.close();
                            outputStream.close();

                            imageUriSeleccionada = Uri.fromFile(file);
                            imagenSeleccionada.setImageURI(imageUriSeleccionada);
                            nombreImg = nombreArchivo;

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error al copiar imagen", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        /** Listener que abre la galaería del dispositivo.
         * Configura el {@link Button btnAdjuntarImagen} para que, al ser presionado, abra la galería de imágenes del dispositivo.
         * Usa {@link #imagePickerLauncher} para manejar la selección de la imagen y actualizar la interfaz con la imagen elegida. */
        btnAdjuntarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                imagePickerLauncher.launch(intent);
            }
        });
    }


    /** Actualiza la visibilidad de los campos de la interfaz según el tipo de comunicado seleccionado.
     * <p> Si se selecciona "Anuncio", se ocultan los campos de lugar y fecha, y se muestran los de urgencia.
     * Si se selecciona "Evento", se muestran los campos de lugar y fecha, y se ocultan los de urgencia.
     * Si no hay selección, se muestran todos los campos por defecto. </p> */
    private void actualizarVisibilidad(){
        boolean esAnuncio = anuncio.isChecked();
        boolean esEvento = evento.isChecked();

        if(esAnuncio){
            tvLugar.setVisibility(View.GONE);
            lugar.setVisibility(View.GONE);
            tvFecha.setVisibility(View.GONE);
            fecha.setVisibility(View.GONE);
            tvUrgencia.setVisibility(View.VISIBLE);
            spUrgencia.setVisibility(View.VISIBLE);
        } else if(esEvento) {
            tvLugar.setVisibility(View.VISIBLE);
            lugar.setVisibility(View.VISIBLE);
            tvFecha.setVisibility(View.VISIBLE);
            fecha.setVisibility(View.VISIBLE);
            tvUrgencia.setVisibility(View.GONE);
            spUrgencia.setVisibility(View.GONE);
        } else{
            tvLugar.setVisibility(View.VISIBLE);
            lugar.setVisibility(View.VISIBLE);
            tvFecha.setVisibility(View.VISIBLE);
            fecha.setVisibility(View.VISIBLE);
            tvUrgencia.setVisibility(View.VISIBLE);
            spUrgencia.setVisibility(View.VISIBLE);
        }


        /** Listener que publica (o no) lo que el usuario haya ingresado.
         * Configura el {@link Button btnPublicar} para validar los datos ingresados por el usuario y publicar el comunicado.
         * <p> Al presionar el botón:
         * Se obtiene el tipo de comunicado seleccionado (Anuncio o Evento).
         * Se recopilan los datos de los campos de texto, spinners, checkboxes y la imagen seleccionada.
         * Se valida que todos los campos obligatorios estén completos y que la audiencia esté seleccionada.
         * Si es un Evento, se crea un objeto {@link Evento} y se guarda mediante {@link #guardarComunicado(Comunicado)}.
         * Si es un Anuncio, se crea un objeto {@link Anuncio} y se guarda mediante {@link #guardarComunicado(Comunicado)}.
         * Muestra un {@link Toast} confirmando la publicación o alertando sobre errores de validación.
         * Limpia todos los campos del formulario para permitir una nueva publicación. </p>
         * En caso de error inesperado se muestra un mensaje genérico al usuario. */
        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int selectedTipo = grupoTipo.getCheckedRadioButtonId();
                    RadioButton rdSeleccion = findViewById(selectedTipo);
                    String area = spArea.getSelectedItem().toString();
                    int posicionArea = spArea.getSelectedItemPosition();
                    boolean est = ckEstudiantes.isChecked();
                    boolean prof = ckProfesores.isChecked();
                    boolean adm = ckAdministracion.isChecked();
                    String title = titulo.getText().toString().trim();
                    String lug = lugar.getText().toString().trim();
                    String fechaTexto = fecha.getText().toString().trim();
                    String descrp = descripcion.getText().toString().trim();
                    String urg = spUrgencia.getSelectedItem().toString();
                    int posicionUrg = spUrgencia.getSelectedItemPosition();

                    boolean esAnuncio = anuncio.isChecked();
                    boolean esEvento = evento.isChecked();

                    try {
                        boolean audicenciaSelec = est || prof || adm;

                        // Validación personalizada para cuando el usuario llene todo y no seleccione el tipo de comuinicado.
                        if (!esAnuncio && !esEvento) {
                            throw new datosIncompletosException("Debe seleccionar el tipo de comunidado.");
                        }

                        boolean imgSeleccion = nombreImg != null && !nombreImg.isEmpty();

                        // Validación de todos los campos.
                        if (title.isEmpty() || descrp.isEmpty() || posicionArea == 0 || !audicenciaSelec || !imgSeleccion) {
                            throw new datosIncompletosException("Datos incompletos. Por favor, llene los campos correctamente.");
                        }


                        // Se crea un StringBuilder para construir la cadena de audiencia seleccionada acorde a las selecciones del usuario.
                        StringBuilder audienciaBuilder = new StringBuilder();

                        // Si la casilla Estudiantes se marca, se agrega "Estudiantes"
                        if (ckEstudiantes.isChecked()) {
                            audienciaBuilder.append("Estudiantes");
                        }

                        // Si la casilla Profesores se marca, se agrega "Profesores"
                        if (ckProfesores.isChecked()) {
                            if (audienciaBuilder.length() > 0) audienciaBuilder.append(";");
                            audienciaBuilder.append("Profesores");
                        }

                        // Si la casilla Administración se marca, se agrega "Administración"
                        if (ckAdministracion.isChecked()) {
                            if (audienciaBuilder.length() > 0) audienciaBuilder.append(";");
                            audienciaBuilder.append("Administración");
                        }

                        //Crea la cadena de String con el formato:  Estudiantes;Profesores;Administración o acorde a lo seleccionad.
                        String audiencia = audienciaBuilder.toString();

                        // Si seleccionamos Evento como tipo de comunicado, se valida que los campos lugar y fecha no estén vacíos, si lo están, lanza un mensaje en pantalla.
                        if (esEvento) {
                            String tipo = rdSeleccion.getText().toString();
                            if (lug.isEmpty() || fechaTexto.isEmpty()) {
                                throw new datosIncompletosException("Datos incompletos. Por favor, llene los campos correctamente.");
                            }
                            int id = obtenerUltimoId() + 1;
                            Evento eventos = new Evento(id, tipo, area, title, audiencia, descrp, nombreImg, lug, fechaSeleccionada, MainActivity.idUsuarioActual);
                            guardarComunicado(eventos);
                            Toast.makeText(Publicar.this, "Evento publicado con éxito!", Toast.LENGTH_SHORT).show();
                        }

                        // Si seleccionamos Anuncio como tipo de comunicado, se valida que la selección de urgencia no sea el primero de la lista (el primero de la lista es un mensaje por defecto).
                        if (esAnuncio) {
                            String tipo = rdSeleccion.getText().toString();
                            if (posicionUrg == 0) {
                                throw new datosIncompletosException("Datos incompletos. Por favor, llene los campos correctamente.");
                            }
                            int id = obtenerUltimoId() + 1;
                            Anuncio anuncios = new Anuncio(id, tipo, area, title, audiencia, descrp, nombreImg, urg, MainActivity.idUsuarioActual);
                            guardarComunicado(anuncios);
                            Toast.makeText(Publicar.this, "Anuncio publicado con éxito!", Toast.LENGTH_SHORT).show();
                        }

                        //Si se publica sin problema, limpia todos los campos que fueron llenados por usuario.
                        titulo.setText("");
                        descripcion.setText("");
                        grupoTipo.clearCheck();
                        spArea.setSelection(0);
                        fecha.setText("");
                        imagenSeleccionada.setImageResource(R.drawable.image_def);
                        nombreImg = null;
                        imageUriSeleccionada = null;
                        ckEstudiantes.setChecked(false);
                        ckProfesores.setChecked(false);
                        ckAdministracion.setChecked(false);
                        lugar.setText("");
                        spUrgencia.setSelection(0);

                    } catch (datosIncompletosException e) {
                        Toast.makeText(Publicar.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Publicar.this, "Error inesperado. Estamos solucionándolo lo antes posible!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /** Guarda un comunicado en el archivo "Comunicados.txt" dentro del almacenamiento interno de la aplicación.
     * <p>El método abre el archivo en modo append, escribe la representación en texto del objeto
     * {@link Comunicado} y agrega un salto de línea. Si ocurre un error al escribir, se muestra un
     * {@link Toast} notificando al usuario.</p>
     * @param comunicado Objeto {@link Comunicado} que se desea guardar. Puede ser un {@link Anuncio} o {@link Evento}.*/
    private void guardarComunicado(Comunicado comunicado) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(openFileOutput("Comunicados.txt", MODE_APPEND)))) {
            writer.write(comunicado.toString());
            writer.newLine();
        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar el comunicado.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    /**
     * Recupera el ID del último comunicado registrado en el archivo "Comunicados.txt".
     * <p>Este método lee cada línea del archivo, obtiene el primer valor separado por "|"
     * el cual corresponde al ID del último comunicado y lo devuelve.
     * Se utiliza para asignar un identificador único y secuencial a cada nuevo comunicado.</p>
     * @return El ID del último comunicado registrado. Retorna 0 si no hay comunicados o si ocurre un error.*/
    private int obtenerUltimoId() {
        int idContador = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput("Comunicados.txt")))) {
            String linea = null;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\|");
                    if (partes.length > 0) {
                        try {
                            int idLinea = Integer.parseInt(partes[0]);
                            if (idLinea > idContador) {
                                idContador = idLinea;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return idContador;
    }


    /** Maneja la acción de cancelar la publicación de un comunicado.
     * <p>Al ser llamado redirige al usuario a la actividad {@link Opciones} sin guardar ningún dato ingresado.</p>
     * @param view La vista que invoca este método es el botón Cancelar. */
    public void Cancelar(View view){
        Intent intent = new Intent(this, Opciones.class);
        startActivity(intent);
    }

}