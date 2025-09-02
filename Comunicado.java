package Modelo;

public abstract class Comunicado {
    private int id;
    private String tipo;
    private String area;
    private String titulo;
    private String audiencia;
    private String descripcion;
    private String imagen;
    private String idUsuario;

    //Constructor general
    public Comunicado(int id, String tipo, String area, String titulo, String audiencia, String descripcion, String imagen, String idUsuario) {
        this.id = id;
        this.tipo = tipo;
        this.area = area;
        this.titulo = titulo;
        this.audiencia = audiencia;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.idUsuario = idUsuario;
    }

    //Constructor para cargar los comunicados del archivo
    public Comunicado(String titulo, String descripcion, String imagen){
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }


    //Constructor para tablero de comunicados
    public Comunicado(String titulo){
        this.titulo = titulo;
    }


    //Métodos Getters
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getArea() {
        return area;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getNombreImagen() {
        return imagen;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getAudiencia() {
        return audiencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }


    //Métodos Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAudiencia(String audiencia) {
        this.audiencia = audiencia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    @Override
    public String toString() {
        return id + "|" +
                tipo + "|" +
                area + "|" +
                titulo + "|" +
                audiencia + "|" +
                descripcion + "|" +
                imagen;
    }
}
