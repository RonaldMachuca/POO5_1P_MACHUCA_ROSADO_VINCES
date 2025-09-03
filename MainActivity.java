package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import Modelo.CredecialesInvalidasException;

/** Activity principal de la aplicación que carga el layout: activity_main.xml
 * <p> Se encarga de manejar la pantalla de inicio de sesión, donde el usuario ingresa sus credenciales (usuario y contraseña).
 * Verifica los datos contra un archivo de texto {@code Usuarios.txt} almacenado en la carpeta de assets. </p>
 * <p> Si las credenciales son correctas, se guarda el identificador del usuario actual en {@link #idUsuarioActual} y
 * se redirige a la pantalla de opciones ({@link Opciones}). En caso contrario, se muestra un mensaje de error. </p> */

public class MainActivity extends AppCompatActivity {

    /** Identificador del usuario que inició sesión correctamente. Este valor se establece al validar las credenciales. */
    public static String idUsuarioActual;

    /** Campo de texto para que el usuario ingrese su nombre de usuario. */
    private EditText editTextUsuario;

    /** Campo de texto para que el usuario ingrese su contraseña. */
    private EditText editTextContra;

    /** Botón para iniciar sesión. Al presionarlo, se validan las credenciales.*/
    private Button btnIngresar;


    /** Método del ciclo de vida de la Activity que inicializa la interfaz y los componentes.
     * Configura los listeners necesarios para manejar el inicio de sesión.
     * @param savedInstanceState Estado guardado de la Activity.*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvSinComunicado), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /** Inicialización de campos y botón */
        editTextUsuario=findViewById(R.id.editTextUsuario);
        editTextContra=findViewById(R.id.editTextContra);
        btnIngresar=findViewById(R.id.btnIngresar);


        /** Listener para el botón Ingresar */
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean credencialesCorrectas=false;
                try (InputStream is = getAssets().open("Usuarios.txt");
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

                    String linea;
                    reader.readLine();      //Saltamos la cabecera

                    //Validamos credenciales
                    while ((linea = reader.readLine()) != null) {
                        String[] datosParticionado=linea.split(",");
                        if((editTextUsuario.getText().toString().trim()).equals(datosParticionado[1])&& (editTextContra.getText().toString().trim()).equals(datosParticionado[2])){
                            credencialesCorrectas=true;
                            MainActivity.idUsuarioActual = datosParticionado[0];
                        }
                    }
                    //Validar si las credenciales son correctas para pasar al nuevo activity.
                    if(credencialesCorrectas){
                        Intent intent=new Intent(MainActivity.this, Opciones.class);
                        startActivity(intent);
                    }else {
                        throw new CredecialesInvalidasException("Usuario o contraseña inválidos.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CredecialesInvalidasException e1) {
                    Toast.makeText(MainActivity.this, e1.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}