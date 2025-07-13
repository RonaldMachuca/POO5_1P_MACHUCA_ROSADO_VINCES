package com.funcionamiento;
/**
 * Representa un producto con su código, categoría, nombre, precio y stock disponible.
 */
public class Producto {
    /** Código único del producto. */
    private String codigoProducto;

    /** Categoría del producto. */
    private CategoriaProducto categoriaProducto;

    /** Nombre del producto. */
    private String nombreProducto;

    /** Precio unitario del producto. */
    private double precio;

    /** Cantidad disponible en stock. */
    private int stock;

    /**
     * Constructor para crear un producto con sus atributos.
     * 
     * @param codigoProducto Código único del producto.
     * @param categoriaProducto Categoría del producto.
     * @param nombreProducto Nombre del producto.
     * @param precio Precio unitario.
     * @param stock Cantidad en stock.
     */
    public Producto(String codigoProducto, CategoriaProducto categoriaProducto, String nombreProducto, double precio, int stock) {
        this.codigoProducto = codigoProducto;
        this.categoriaProducto = categoriaProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y setters

    /**
     * Devuelve el código único del producto.
     * 
     * @return Código único del producto.
     */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /**
     * Devuelve la categoría del producto.
     * 
     * @return Categoría del producto.
     */
    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    /**
     * Devuelve el nombre del producto.
     * 
     * @return Nombre del producto.
     */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /**
     * Devuelve el precio unitario del producto.
     * 
     * @return Precio unitario del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Devuelve la cantidad disponible en stock.
     * 
     * @return Cantidad disponible en stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el código único del producto.
     * 
     * @param codigoProducto Código único del producto.
     */
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /**
     * Establece la categoría del producto.
     * 
     * @param categoriaProducto Categoría del producto.
     */
    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    /**
     * Establece el nombre del producto.
     * 
     * @param nombreProducto Nombre del producto.
     */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /**
     * Establece el precio unitario del producto.
     * 
     * @param precio Precio unitario del producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Establece la cantidad disponible en stock.
     * 
     * @param stock Cantidad disponible en stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Devuelve una representación del producto en formato de línea para archivos.
     * 
     * @return Cadena con atributos separados por '|'.
     */
    @Override
    public String toString() {
        return getCodigoProducto() + "|" + getCategoriaProducto() + "|" + getNombreProducto() + "|" + getPrecio() + "|" + getStock();
    }
}
