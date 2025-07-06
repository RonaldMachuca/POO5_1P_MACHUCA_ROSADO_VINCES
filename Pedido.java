/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.example.POO4_1P_MACHUCA_ROSADO_VINCES;

import java.time.LocalDate;
import java.util.List;

public class Pedido{
    //variables de instancia
    private String codigoPedido;
    private LocalDate fecha; //Fecha local en formato dd-mm-aaaa
    private String codigoproducto;
    private int cantidad;
    private double valor;
    private EstadoProducto estadoProducto;
    private String codigoRepartidor;
    private static int incrementado=000;//variable static para dar codigo a los productos
    
    //constructor que crea su propiocodigo
    public Pedido(LocalDate fecha, String codigoProducto,int cantidad, double valor, EstadoProducto estadoProducto, String codigoRepartidor){
        Pedido.incrementado++;
        this.codigoPedido="PED100"+incrementado;
        this.fecha=fecha;
        this.codigoproducto=codigoProducto;
        this.cantidad=cantidad;
        this.valor=valor;
        this.estadoProducto=estadoProducto;
        this.codigoRepartidor=codigoRepartidor;
        
        
    }
    //constructor que permite un String codigoPedido para al momento de agg pedidos a una lista no cambiar el valor del codigo
    public Pedido(String codigoPedido,LocalDate fecha, String codigoProducto,int cantidad, double valor, EstadoProducto estadoProducto, String codigoRepartidor){
        this(fecha, codigoProducto, cantidad, valor, estadoProducto, codigoRepartidor);
        this.codigoPedido=codigoPedido;
        
    }
    //getters y setters
    public String getCodigoPedido(){
        return codigoPedido;
    }
    public void setCodigoPedido(String codigoPedido ){
        this.codigoPedido=codigoPedido;
    }
    public LocalDate getFecha(){
        return fecha;
    }
    public void setFecha(LocalDate fecha){
        this.fecha=fecha;
    }
    public String getCodigoProducto(){
        return codigoproducto;
    }
    public void setCodigoProducto(String codigoProducto){
        this.codigoproducto=codigoProducto;
    }
    public int getCantidad(){
        return cantidad;
    }
    public void setCantidad(int cantidad){
        this.cantidad=cantidad;
    }
    public double getValor(){
        return valor;
    }
    public void setValor(double valor){
        this.valor=valor;
    }
    public EstadoProducto getEstadoProducto(){
        return estadoProducto;
    }
    public void setEstadoProducto(EstadoProducto estadoProducto){
        this.estadoProducto=estadoProducto;
    }
    public String getCodigoRepartidor(){
        return codigoRepartidor;
    }
    public void setCodigoRepartidor(String codigoRepartidor){
        this.codigoRepartidor=codigoRepartidor;
    }
    
    
    //METODO que nos busca un producto si le damos un codigoProducto
    public Producto buscarProductoPorCodigo(String codigoBuscado, List<Producto> productos) {
        for (Producto producto : productos) {       //for que busca en la lista de productos
            if (producto.getCodigoProducto().equals(codigoBuscado)) {   //if que compara si el codigoProducto es igual al codigo que buscamos
                return producto; // Retorna el producto encontrado
            }
        }
        return null; // Retorna null si no encuentra el producto
    }
    //METODO que nos busca el repartidor asignado al pedido mediante un codigo
    public Repartidor buscarRepartidorPorCodigo(String codigoRepartidor, List<Repartidor> repartidores) {
        for (Repartidor repartidor : repartidores) {        //for que busca en la lista de repartidores
            if (repartidor.getCodigoUnico().equals(codigoRepartidor)) { //if que compara si el codigoUnico del repartidor es igual al codigo que buscamos
                return repartidor; // Retorna el producto encontrado
            }
        }
        return null; // Retorna null si no encuentra el producto
    }
    // sobrecarga de toString para crear los pedidos siguiendo el formato .txt
    @Override
    public String toString(){
        return getCodigoPedido()+"|"+getFecha()+"|"+getCodigoProducto()+"|"+ getCantidad()+"|"+getValor()+"|"+ getEstadoProducto()+"|"+getCodigoRepartidor();
    }
}
