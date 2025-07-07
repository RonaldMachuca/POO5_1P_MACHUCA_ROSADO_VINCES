package com.example;

import java.util.List;
import java.util.Scanner;

public class Repartidor extends Usuario{
    //variables de instancia
    private String empresa;


    //constructor de repartidor para inicializar las variables
    public Repartidor(String codigoUnico, String cedula, String nombres, String apellidos, String usuario, String contrasena, String correo, char rol, String empresa) {
        super(codigoUnico, cedula, nombres, apellidos);
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;
        this.empresa = empresa;
    }

    public Repartidor(String codigoUnico, String cedula, String nombres, String apellidos, String empresa){
    super(codigoUnico, cedula, nombres, apellidos);
    this.empresa = empresa;
}
    
     private String formatoEstado(EstadoProducto estado) {
        switch (estado) {
        case EN_PREPARACION: return "EN PREPARACION";
        case EN_RUTA: return "EN RUTA";
        case ENTREGADO: return "ENTREGADO";
        default: return estado.toString();
    }
}

    public void gestionarEstadoPedido(List<Pedido> pedidos){
        Scanner sc = new Scanner(System.in);
        System.out.println("===== GESTIONAR ESTADO PEDIDO =====");
        System.out.print("\nIngrese el código del pedido que desea gestionar: ");
        String codigoPedido = sc.nextLine();
        sc.nextLine();

        Pedido pedido = null;
        for(Pedido p: pedidos){
            if(p.getCodigoPedido().equals(codigoPedido) && p.getCodigoRepartidor().equals(this.getCodigoUnico())){
                pedido=p;
                break;
            }

        }

        if(pedido==null){
            System.out.println("Error: Pedido no encontrado o no ha sido asignado a este repartidor.");
            return;
        }

        System.out.println("\nPedido encontrado:");
        System.out.println("Fecha del pedido: " + pedido.getFecha());
        System.out.println("Código del producto: " + pedido.getCodigoProducto());
        System.out.println("Estado actual: " + formatoEstado(pedido.getEstadoProducto()));

        System.out.println("\nSeleccione nuevo estado:");
        System.out.println("1. EN RUTA");
        System.out.println("2. ENTREGADO");
        System.out.print("Opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();

        EstadoProducto estadoActual = pedido.getEstadoProducto();
        EstadoProducto nuevoEstado = null;

        if (opcion == 1) {
            nuevoEstado = EstadoProducto.EN_RUTA;
        }else if (opcion == 2) {
            nuevoEstado = EstadoProducto.ENTREGADO;
        }else {
            System.out.println("Error: Opción inválida.");
            return;
        }
        
        
        if (estadoActual.equals(EstadoProducto.EN_PREPARACION) && nuevoEstado.equals(EstadoProducto.ENTREGADO)) {
            System.out.println("\nError: No puede cambiar directamente de EN PREPARACION a ENTREGADO.");
            System.out.println("Debe cambiar primero a EN RUTA.");
            return;
        }

        if (estadoActual.equals(EstadoProducto.EN_RUTA) && nuevoEstado.equals(EstadoProducto.EN_RUTA)) {
            System.out.println("\nError: El pedido ya está en ruta.");
            return;
        }

        if (estadoActual.equals(EstadoProducto.ENTREGADO)) {
            System.out.println("\nError: El pedido ya fue entregado.");
            return;
        }
        
        
        pedido.setEstadoProducto(nuevoEstado);
        System.out.println("\nEstado actualizado correctamente a " + formatoEstado(nuevoEstado));
        
        
        if (nuevoEstado.equals(EstadoProducto.EN_RUTA)) {
            System.out.println("Notificación enviada al cliente: El pedido " + pedido.getCodigoPedido() + " ha sido despachado y está en camino.");
        }else if (nuevoEstado.equals(EstadoProducto.ENTREGADO)) {
            System.out.println("Notificación enviada al cliente: El pedido " + pedido.getCodigoPedido() + " ha sido entregado con éxito.");
        }
    }
    
    
    public void consultarPedido(List<Pedido> pedidos){     
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== PEDIDOS ASIGNADOS =====");
        System.out.println("\nBuscando pedidos asignados no entregados...");


        int contador = 0;
        for(Pedido p: pedidos){
            if(p.getCodigoRepartidor().equals(this.getCodigoUnico()) && !p.getEstadoProducto().equals(EstadoProducto.ENTREGADO)){
                contador++;
                System.out.println("\nPedidos encontrados:");
                System.out.println(contador + ".Código: " + p.getCodigoPedido());
                System.out.println("Fecha del pedido: " + p.getFecha());
                System.out.println("Estado actual: " + formatoEstado(p.getEstadoProducto()));
            }
        }

        if(contador==0){
            System.out.println("No tiene pedidos pendientes.");
        }else{
            System.out.println("------------------------");
            System.out.println("Total pedidos pendientes: " + contador);
        }

    }


    // sobrecarga de toString para crear los repartidores siguiendo el formato .txt
    @Override
    public String toString(){
        return super.getCodigoUnico()+"|"+super.cedula+"|"+super.getNombres()+"|"+super.getApellidos()+"|"+getEmpresa();
    }



    // getters y setters
    public String getEmpresa(){
        return empresa;
    }
    public void setEmpresa(String empresa){
        this.empresa=empresa;
    }
}