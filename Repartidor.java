package com.funcionamiento;

import java.util.ArrayList;
import java.util.Scanner;

import com.archivo.Archivo;

/**
 * Clase que representa a un repartidor, extiende de Usuario.
 * Permite gestionar y consultar el estado de pedidos asignados.
 */
public class Repartidor extends Usuario {
    /** Empresa a la que pertenece el repartidor. */
    private String empresa;
    /** atributo de Sistema para usar el método notificarCambios*/
    private Sistema sistema;

    /*Constructor para instanciar Sistema
     * @param sistema;*/
    public Repartidor(Sistema sistema){
        this.sistema=sistema;
    }
    
     /**Constructor completo para inicializar un repartidor con todos sus atributos.
     *
     * @param codigoUnico Código único del repartidor.
     * @param cedula Cédula del repartidor.
     * @param nombres Nombres del repartidor.
     * @param apellidos Apellidos del repartidor.
     * @param usuario Usuario para login.
     * @param contrasena Contraseña para login.
     * @param correo Correo electrónico.
     * @param rol Rol del usuario.
     * @param empresa Empresa a la que pertenece.
     */
    public Repartidor(String codigoUnico, String cedula, String nombres, String apellidos, String usuario, String contrasena, String correo, char rol, String empresa) {
        super(codigoUnico, cedula, nombres, apellidos);
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;
        this.empresa = empresa;
    }

    /**
     * Constructor simplificado para crear un repartidor con solo datos básicos.
     *
     * @param codigoUnico Código único del repartidor.
     * @param cedula Cédula del repartidor.
     * @param nombres Nombres del repartidor.
     * @param apellidos Apellidos del repartidor.
     * @param empresa Empresa a la que pertenece.
     */
    public Repartidor(String codigoUnico, String cedula, String nombres, String apellidos, String empresa) {
        super(codigoUnico, cedula, nombres, apellidos);
        this.empresa = empresa;
    }

    /**
     * Formatea el estado del pedido para mostrarlo legible.
     *
     * @param estado Estado del producto.
     * @return String con estado en mayúsculas y sin guiones.
     */

    //método para darle mejor formato al estado de un producto.
    public static String formatoEstado(EstadoProducto estado) {
        switch (estado) {
            case EN_PREPARACION:
                return "EN PREPARACION";
            case EN_RUTA:
                return "EN RUTA";
            case ENTREGADO: 
                return "ENTREGADO";
            default: return estado.toString();
        }
    }

    //método para notificar el cambio de estado del pedido por correo electronico
    public void enviarNotificacion(Sistema sistema, Cliente cliente, Pedido pedido){
        sistema.notificarCambioEstado(cliente, pedido);
    }

    /**
     * Permite gestionar y actualizar el estado de un pedido asignado a este repartidor.
     *
     * @param pedidos Lista de pedidos existentes.
     */
    public void gestionarEstadoPedido(ArrayList<Pedido> pedidos, Sistema sistema) {
        ArrayList<Cliente> clientes = Archivo.LeeFicheroClientes();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== GESTIONAR ESTADO PEDIDO =====");
        System.out.print("\nIngrese el código del pedido que desea gestionar: ");
        String codigoPedido = sc.nextLine();
        //sc.nextLine();

        Pedido pedido = null;
        for (Pedido p : pedidos) {
            if (p.getCodigoPedido().equals(codigoPedido) && p.getCodigoRepartidor().equals(this.getCodigoUnico())) {
                pedido = p;
                break;
            }
        }

        if (pedido == null) {
            System.out.println("Error: Pedido no encontrado o no ha sido asignado a este repartidor.");
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
        } else if (opcion == 2) {
            nuevoEstado = EstadoProducto.ENTREGADO;
        } else {
            System.out.println("Error: Opción inválida.");
            return;
        }

        //validaciones antes de asignar algún estado
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
        Archivo.GuardarPedidos(pedidos);     

        if (nuevoEstado.equals(EstadoProducto.EN_RUTA)) {
            System.out.println("Notificación enviada al cliente: El pedido " + pedido.getCodigoPedido() + " ha sido despachado y está en camino.");
            
            for(Cliente c : clientes){
                
                if(pedido.getCodigoUnicoCliente().equals(c.codigoUnico)){
                    Cliente c1 = new Cliente(c.getCodigoUnico(),c.getCedula(),c.getNombres(),c.getApellidos(),c.getUsuario(),c.getContrasena(),c.getCorreo(),c.getRol());
                    
                    sistema.notificarCambioEstado(c1, pedido);
                }
            }
        } else if (nuevoEstado.equals(EstadoProducto.ENTREGADO)) {
            System.out.println("Notificación enviada al cliente: El pedido " + pedido.getCodigoPedido() + " ha sido entregado con éxito.");
            for(Cliente c : clientes){
                
                if(pedido.getCodigoUnicoCliente().equals(c.codigoUnico)){
                    Cliente c1 = new Cliente(c.getCodigoUnico(),c.getCedula(),c.getNombres(),c.getApellidos(),c.getUsuario(),c.getContrasena(),c.getCorreo(),c.getRol());
                    
                    sistema.notificarCambioEstado(c1, pedido);
                }
            }
        }
    }


    /**
     * Muestra los pedidos asignados a este repartidor que aún no han sido entregados.
     *
     * @param pedidos Lista de pedidos existentes.
     */
    public void consultarPedido(ArrayList<Pedido> pedidos) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== PEDIDOS ASIGNADOS =====");
        System.out.println("\nBuscando pedidos asignados no entregados...");

        int contador = 0;
        for (Pedido p : pedidos) {
            if (p.getCodigoRepartidor().equals(this.getCodigoUnico()) && !p.getEstadoProducto().equals(EstadoProducto.ENTREGADO)) {
                contador++;
                System.out.println("\nPedidos encontrados:");
                System.out.println(contador + ".Código: " + p.getCodigoPedido());
                System.out.println("Fecha del pedido: " + p.getFecha());
                System.out.println("Estado actual: " + formatoEstado(p.getEstadoProducto()));
            }
        }

        if (contador == 0) {
            System.out.println("No tiene pedidos pendientes.");
        } else {
            System.out.println("------------------------");
            System.out.println("Total pedidos pendientes: " + contador);
        }
    }


    /**
     * Devuelve una representación en texto del repartidor en formato para archivos.
     *
     * @return Cadena con datos separados por '|'.
     */
    @Override
    public String toString() {
        return super.getCodigoUnico() + "|" + super.cedula + "|" + super.getNombres() + "|" + super.getApellidos() + "|" + getEmpresa();
    }

    // Getters y setters

    /** @return Empresa a la que pertenece el repartidor. */
    public String getEmpresa() {
        return empresa;
    }

    /** @param empresa Empresa a la que pertenece el repartidor. */
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
