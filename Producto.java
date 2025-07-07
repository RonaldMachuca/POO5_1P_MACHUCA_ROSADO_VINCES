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

    /** @return Código único del producto. */
    public String getCodigoProducto() {
        return codigoProducto;
    }

    /** @return Categoría del producto. */
    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    /** @return Nombre del producto. */
    public String getNombreProducto() {
        return nombreProducto;
    }

    /** @return Precio unitario del producto. */
    public double getPrecio() {
        return precio;
    }

    /** @return Cantidad disponible en stock. */
    public int getStock() {
        return stock;
    }

    /** @param codigoProducto Código único del producto. */
    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    /** @param categoriaProducto Categoría del producto. */
    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    /** @param nombreProducto Nombre del producto. */
    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    /** @param precio Precio unitario del producto. */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /** @param stock Cantidad disponible en stock. */
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
