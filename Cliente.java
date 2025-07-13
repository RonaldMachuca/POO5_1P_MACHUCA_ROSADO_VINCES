package com.funcionamiento;

import com.archivo.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que representa a un Cliente del sistema.
 * Hereda de {@link Usuario} y permite realizar compras y gestionar pedidos.
 */
public class Cliente extends Usuario {
    /**
     * Número de celular del cliente.
     */
    private String celular;

    /**
     * Dirección del cliente.
     */
    private String direccion;

    

    /**
     * Constructor de la clase Cliente.
     *
     * @param codigoUnico Código único del cliente.
     * @param cedula Cédula del cliente.
     * @param nombres Nombres del cliente.
     * @param Apellidos Apellidos del cliente.
     * @param usuario Nombre de usuario para iniciar sesión.
     * @param contrasena Contraseña del cliente.
     * @param correo Correo electrónico del cliente.
     * @param rol Rol del usuario.
     * @param celular Número de celular del cliente.
     */
    public Cliente(String codigoUnico, String cedula, String nombres, String Apellidos, String usuario, String contrasena, String correo, char rol, String celular){
        super(codigoUnico, cedula, nombres, Apellidos);
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;
        this.celular = celular;   
        this.direccion = null;
    }
    public Cliente(String codigoUnico, String cedula, String nombres, String Apellidos, String usuario, String contrasena, String correo, char rol){
        super(codigoUnico, cedula, nombres, Apellidos);
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;   
        this.direccion = null;
    }

    /**
     * Muestra el menú principal del cliente.
     *
     * @return la opción seleccionada por el cliente.
     */
    public int mostrarMenu(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Menú de Cliente\n1. Comprar Producto\n2. Gestionar Pedido\n3. Salir");
        System.out.println("Seleccione una opcion: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        
        return opcion;
    }

    /**
     * Permite al cliente comprar productos según la categoría seleccionada.
     *
     * @param sistema El sistema que contiene los repartidores disponibles.
     */
    public void comprar(Sistema sistema) {
    Scanner sc = new Scanner(System.in);
    String continuar = "si";
    ArrayList<Producto> productos = Archivo.LeeFicheroProducto();
    List<Repartidor> repartidores = sistema.getRepartidores();
    Random random = new Random();

    do {
        System.out.println("\nCategorias disponibles:");
        System.out.println(" 1. DEPORTES");
        System.out.println(" 2. HOGAR");
        System.out.println(" 3. ROPA");
        System.out.println(" 4. TECNOLOGIA");
        System.out.print("Ingrese la opcion para consultar: ");
        int opcion = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        ArrayList<Producto> productosCategoria = new ArrayList<>();
        CategoriaProducto categoriaElegida = null;

        switch (opcion) {
            case 1:
                categoriaElegida = CategoriaProducto.DEPORTES;
                break;
            case 2:
                categoriaElegida = CategoriaProducto.HOGAR;
                break;
            case 3:
                categoriaElegida = CategoriaProducto.ROPA;
                break;
            case 4:
                categoriaElegida = CategoriaProducto.TECNOLOGIA;
                break;
            default:
                System.out.println("Opción inválida.");
                continue; // volver a mostrar menú
        }

        // Filtrar productos por categoría seleccionada
        int i = 1;
        for (Producto p : productos) {
            if (p.getCategoriaProducto() == categoriaElegida) {
                productosCategoria.add(p);
                System.out.println(i + ". " + p);
                i++;
            }
        }

        if (productosCategoria.isEmpty()) {
            System.out.println("No hay productos disponibles en esta categoría.");
        } else {
            System.out.print("Seleccione el número del producto a comprar: ");
            int seleccion = sc.nextInt();
            sc.nextLine();

            if (seleccion < 1 || seleccion > productosCategoria.size()) {
                System.out.println("Selección inválida.");
            } else {
                Producto productoSeleccionado = productosCategoria.get(seleccion - 1);

                System.out.print("Ingrese la cantidad que desea comprar: ");
                int cantidad = sc.nextInt();
                sc.nextLine();

                if (cantidad <= 0 || cantidad > productoSeleccionado.getStock()) {
                    System.out.println("Cantidad inválida o stock insuficiente.");
                } else {
                    productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);
                    double total = cantidad * productoSeleccionado.getPrecio();
                    System.out.println("Su total a pagar es: " + total);
                    System.out.print("Ingrese su número de tarjeta: ");
                    String numeroTarjeta = sc.nextLine();
                    LocalDate fecha = LocalDate.now();

                    // Elegir repartidor aleatorio
                    Repartidor repartidorAsignado = repartidores.get(random.nextInt(repartidores.size()));
                    
                    

                    Pedido pedido = new Pedido(productoSeleccionado.getCodigoProducto(), fecha, cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico(),this.codigoUnico);
                    
                    Archivo.EscribirArchivoPedidos(pedido.toString());
                    
                    
                    sistema.notificar(this, pedido);
                    sistema.notificar(repartidorAsignado, pedido, this);

                    System.out.println("Pedido registrado correctamente con código: " + pedido.getCodigoPedido());
                }
            }
        }

        System.out.print("¿Desea consultar otra categoría? (si/no): ");
        continuar = sc.nextLine().trim().toLowerCase();

    } while (continuar.equals("si"));
    
}

    /**
     * Permite al cliente consultar el estado de un pedido ingresando su código.
     */
    public void gestionarPedido() {
        ArrayList<Producto> productos = Archivo.LeeFicheroProducto();
        ArrayList<Repartidor> repartidores = Archivo.LeeFicheroRepartidores();
        ArrayList<Pedido> pedidos = Archivo.LeeFicheroPedidos();

        Scanner sc = new Scanner(System.in);
        System.out.println("\n===== CONSULTA DE ESTADO DE PEDIDO =====");
        System.out.print("Ingrese el código del pedido: ");
        String codPed = sc.nextLine();

        boolean encontrado = false;

        for (Pedido p : pedidos) {
            if (codPed.equals(p.getCodigoPedido())) {
                encontrado = true;
                System.out.println("\nFecha del pedido: " + p.getFecha());
                Producto producto = p.buscarProductoPorCodigo(p.getCodigoProducto(), productos);
                if (producto != null) {
                    System.out.println("Producto comprado: " + producto.getNombreProducto() + " (Código: " + producto.getCodigoProducto() + ")");
                } else {
                    System.out.println("Producto comprado: (No encontrado)");
                }
                System.out.println("Cantidad: " + p.getCantidad());
                System.out.println("Valor Pagado: $" + p.getValor());
                System.out.println("Estado Actual: " + Repartidor.formatoEstado(p.getEstadoProducto()));
                Repartidor repartidor = p.buscarRepartidorPorCodigo(p.getCodigoRepartidor(), repartidores);
                if (repartidor != null) {
                    System.out.println("Repartidor: " + repartidor.getNombres() + " " + repartidor.getApellidos());
                } else {
                    System.out.println("Repartidor: (No encontrado)");
                }
                System.out.println("\nSu pedido está siendo preparado para su envío.");
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró un pedido con ese código.");
        }
    }

    

    /**
     * Obtiene el número de celular del cliente.
     *
     * @return Número de celular.
     */
    public String getCelular(){
        return celular;
    }

    /**
     * Establece el número de celular del cliente.
     *
     * @param celular Número de celular.
     */
    public void setCelular(String celular){
        this.celular = celular;
    }

    /**
     * Obtiene la dirección del cliente.
     *
     * @return Dirección.
     */

    public String getDireccion(){
        return direccion;
    }
    
    @Override
    public String toString(){
        return super.getCodigoUnico() + "|" + super.cedula + "|" + super.getNombres() + "|" + super.getApellidos();
    }

    
}