package com.example;

public class Cliente {
    private String celular;
    private String direccion;

    public Cliente(String celular, String direccion){
        this.celular = celular;
        this.direccion = direccion;
    }

    public String getCelular(){
        return celular;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setCelular(String celular){
        this.celular=celular;
    }

    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
}
