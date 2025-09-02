package Modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Evento extends Comunicado implements Comparable<Evento>, Serializable {
    private String lugar;
    private Date fecha;
    private String fechaString;
    private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


    //Constructor que inicializa todas las variables
    public Evento(int id, String tipo, String area, String titulo, String audiencia, String descripcion, String imagen, String lugar, Date fecha, String idUsuario) {
        super(id, tipo, area, titulo, audiencia, descripcion, imagen, idUsuario);
        this.lugar = lugar;
        this.fecha = fecha;
    }


    //Constructor para cargar filtrar comunicados por fecha (solo eventos)
    public Evento(String titulo, String descripcion, String imagen, Date fecha) {
        super(titulo, descripcion, imagen);
        this.fecha = fecha;
    }


    //Constructor para mostrar Tablero de Comunicados
    public Evento(String titulo, String fechaString){
        super(titulo);
        this.fechaString = fechaString;
    }


    //Métodos Getters
    public String getLugar() {
        return lugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getFechaString(){
        return  fechaString;
    }


    //Métodos Setters
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    @Override
    public String toString() {
        String fechaFormateada = formatoFecha.format(fecha);
        return super.toString() + "|" +
                lugar + "|" +
                fechaFormateada + "|" +
                getIdUsuario();
    }

    @Override
    public int compareTo(Evento otro) {
        return this.getTitulo().compareToIgnoreCase(otro.getTitulo());
    }
}
