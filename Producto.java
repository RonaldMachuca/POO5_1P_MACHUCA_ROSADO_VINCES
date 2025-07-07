package com.example;

public class Producto{
    //variables de instancia
    private String codigoProducto;
    private CategoriaProducto categoriaProducto;
    private String nombreProducto;
    private double precio;
    private int stock;

    //constructor de Producto
    public Producto(String codigoProducto,CategoriaProducto categoriaProducto, String nombreProducto,double precio,int stock){
        this.codigoProducto=codigoProducto;
        this.categoriaProducto=categoriaProducto;
        this.nombreProducto=nombreProducto;
        this.precio=precio;
        this.stock=stock;
    }
    

    //getters y setter
    public String getCodigoProducto(){
        return codigoProducto;
    }
    public CategoriaProducto getCategoriaProducto(){
        return categoriaProducto;
    }
    public String getNombreProducto(){
        return nombreProducto;
    }
    public double getPrecio(){
        return precio;
    }
    public int getStock(){
        return stock;
    }
    public void setCodigoProducto(String codigoProducto){
        this.codigoProducto=codigoProducto;
    }
    public void setCategoriaProducto(CategoriaProducto categoriaProducto){
        this.categoriaProducto=categoriaProducto;
    }
    public void setNombreProducto(String nombreProducto){
        this.nombreProducto=nombreProducto;
    }
    public void setPrecio(double precio){
        this.precio=precio;
    }
    public void setStock(int stock){
        this.stock=stock;
    }
    //sobreescritura del metodo toString
    @Override
    public String toString(){
        return getCodigoProducto()+"|"+getCategoriaProducto()+"|"+getNombreProducto()+"|"+getPrecio()+"|"+getStock();
    }
}