/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.git;
import java.util.Date;
/**
 *
 * @author user
 */
public class Pedido {
    private String codigoPedido;
    private Date fecha;
    private int cantidad;
    private double valorPagado;
    private EstadoProducto estado;
    public static int idIncremental = 0;
    public Pedido(String codigoPedido, Date fecha, int cantidad, double valorPagado, EstadoProducto estado) {
        this.codigoPedido = codigoPedido;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.valorPagado = valorPagado;
        this.estado = estado;
        idIncremental++;
    }
    public String getCodigoPedido() {
        return codigoPedido;
    }
    public Date getFecha() {
        return fecha;
    }
    public int getCantidad() {
        return cantidad;
    }
    public double getValorPagado() {
        return valorPagado;
    }
    public EstadoProducto getEstado() {
        return estado;
    }
    public static int getIdIncremental() {
        return idIncremental;
    }
    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }
    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    } 
}
