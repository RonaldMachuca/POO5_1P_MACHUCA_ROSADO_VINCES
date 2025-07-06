package com.example.POO4_1P_MACHUCA_ROSADO_VINCES;


public abstract class Usuario {
    //variables de instancia
    protected String codigoUnico;
    protected String cedula;
    protected String nombres;
    protected String apellidos;
    protected String usuario;
    protected String contrasena;
    protected String correo;
    protected char rol;
    //constructor para Usuario
    public Usuario(String codigoUnico, String cedula, String nombres, String apellidos){
        this.codigoUnico=codigoUnico;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
    //segundo constructor para usuario que implementa el rol
    public Usuario(String codigoUnico, String cedula, String nombres, String apellidos, String usuario, String contrasena, String correo, char rol) {
        this(codigoUnico, cedula, nombres, apellidos);
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;
    }
    //metodo abstracto que heredaran las subclases 
    public abstract void consultarPedido();
    

    // Getters y setters
    public String getCodigoUnico(){
        return codigoUnico;
    }
    public void setCodigoUnico(String codigoUnico){
        this.codigoUnico=codigoUnico;
    }
    public String getCedula(){
        return cedula;
    }
    public void setCedula(String cedula){
        this.cedula=cedula;
    }
    public String getNombres(){
        return nombres;
    }
    public void setNombres(String nombres){
        this.nombres=nombres;
    }
    public String getApellidos(){
        return apellidos;
    }
    public void setApellidos(String apellidos){
        this.apellidos=apellidos;
    }
    public String getUsuario(){
        return usuario;
    }
    public void setUsuario(String usuario){
        this.usuario=usuario;
    }
    public String getContrasena(){
        return contrasena;
    }
    public void setContrasena(String contrasena){
        this.contrasena=contrasena;
    }
    public String getCorreo(){
        return correo;
    }
    public void setCorreo(String correo){
        this.correo=correo;
    }
    public char getRol(){
        return rol;
    }
    public void setRol(char rol){
        this.rol=rol;
    }
}

