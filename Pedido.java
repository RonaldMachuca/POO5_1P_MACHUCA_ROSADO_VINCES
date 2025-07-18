/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.git;
import java.time.LocalDate;
public class Pedido {
    private String codigoPedido;
    private LocalDate fecha;
    private String codigoProducto;
    private int cantidad;
    private double valorPagado;
    private EstadoProducto estado;
    private String codigoRepartidor;
    public static int idIncremental = 1000;
    public Pedido(String codigoPedido, LocalDate fecha,String codigoProducto ,int cantidad, double valorPagado, EstadoProducto estado, String codigoRepartidor) {
        this.codigoPedido = generarCodigoPedido();
        this.fecha = fecha;
        this.codigoProducto= codigoProducto;
        this.cantidad = cantidad;
        this.valorPagado = valorPagado;
        this.estado = estado;
        this.codigoRepartidor= codigoRepartidor;
    }
    public String getCodigoPedido() {
        return codigoPedido;
    }
    public LocalDate getFecha() {
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
    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }
    public void setFecha(LocalDate fecha) {
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
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }
    
    public String getCodigoRepartidor() {
        return codigoRepartidor;
    }

    public void setCodigoRepartidor(String codigoRepartidor) {
        this.codigoRepartidor = codigoRepartidor;
    }
    
    private String generarCodigoPedido(){
        idIncremental++;
        return "PED" + idIncremental;
    }
    @Override
    public String toString() {
        return codigoPedido + "|" +
               fecha + "|" +
               codigoProducto + "|" +
               cantidad + "|" +
               valorPagado + "|" +
               estado + "|" +
               codigoRepartidor;
    }   
}
