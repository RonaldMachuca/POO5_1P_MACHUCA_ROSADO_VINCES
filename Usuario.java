package Modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Usuario {
    private String idUsuario;
    private String usuario;
    private String password;

    //Constructor que inicializa todas las variables
    public Usuario(String idUsuario, String usuario, String password) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.password = password;
    }

    //Métodos Getters
    public String getIdUsuario() {
        return idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }


    //Métodos Setters
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return idUsuario + ',' +
                usuario + ',' +
                password;
    }
}
