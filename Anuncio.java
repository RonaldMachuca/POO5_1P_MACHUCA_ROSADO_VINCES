package Modelo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Anuncio extends Comunicado{
    private String urgencia;


    //Constructor que inicializa todas las variables
    public Anuncio(int id, String tipo, String area, String titulo, String audiencia, String descripcion, String imagen, String urgencia, String idUsuario){
        super(id, tipo, area, titulo, audiencia, descripcion, imagen, idUsuario);
        this.urgencia = urgencia;
    }


    //Getters
    public String getUrgencia() {
        return urgencia;
    }


    //Setters
    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }


    @Override
    public String toString() {
        return super.toString() + "|" +
                urgencia + "|" +
                getIdUsuario();
    }
}
