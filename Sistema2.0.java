/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.git;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.time.LocalDate;

public class Sistema {
    private List<Usuario> listaUsuarios= new ArrayList<>();
    private List<Producto> listaProductos= new ArrayList<>();
    private List<Pedido> listaPedidos = new ArrayList<>();
    private final String correoSistema = "guillermovinces006@gmail.com";
    private final String claveSistema = "nqug wfgv ztnc qbmg";
    public Usuario iniciarSesion(String usuario, String contraseña){
        for(Usuario u: listaUsuarios){
            if(u.getUsuario().equals(usuario) && u.getContrasena().equals(contraseña)){
                return u;
            }
        }
        return null;
    }
    public void mostrarMenu(Usuario usuario){
        Scanner sc= new Scanner(System.in);
        if (usuario instanceof Cliente) {
            Cliente c = (Cliente) usuario;
            int opcion;
            do{
               System.out.println("\n--- Menú Cliente ---");
               System.out.println("1. Comprar");
               System.out.println("2. Gestionar pedidos");
               System.out.println("0. Salir");
               System.out.print("Seleccione una opción: ");
               opcion = sc.nextInt(); 
               switch(opcion){
                   case 1:
                       c.comprar();
                       break;
                   case 2:
                       c.gestionarPedido();
                       break;
                   case 0:
                       System.out.println("Saliendo del menú...");
                       break;
                   default:
                       System.out.println("Opción inválida");   
               }
            }while(opcion!=0);
        }else if(usuario instanceof Repartidor){
            Repartidor r= (Repartidor) usuario;
            int opcion;
            do{
                System.out.println("\n--- Menú Repartidor ---");
                System.out.println("1. Consultar pedidos asignados");
                System.out.println("2. Gestionar pedidos");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = sc.nextInt();
                switch(opcion){
                    case 1:
                        r.consultarPedidosAsignados();
                        break;
                    case 2:
                        r.gestionarPedido();
                        break;
                    case 0:
                        System.out.println("Saliendo del menú...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }while(opcion!=0);
        }
    }
   
    public void cargarUsuario(){
        try(BufferedReader br = new BufferedReader(new FileReader("Usuarios.txt", StandardCharsets.UTF_8))){
            String linea;
            while((linea = br.readLine()) != null){
                String[] datos = linea.split("\\|");
                char rol = datos[7].charAt(0);
                if(rol == 'C'){
                    Cliente c = new Cliente(datos[0], datos[1], datos[2], datos[3],datos[4], datos[5], datos[6], rol,datos[8], datos[9]);
                    listaUsuarios.add(c);
                }else if(rol == 'R'){
                    Repartidor r= new Repartidor(datos[0], datos[1], datos[2], datos[3],datos[4], datos[5], datos[6], rol,datos[8], datos[9], datos[10]);
                    listaUsuarios.add(r);
                }  
            }
        }catch(IOException e){
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
    }
    public void cargarProductos(){
        try (BufferedReader br = new BufferedReader(new FileReader("Productos.txt", StandardCharsets.UTF_8))){
            String linea;
            while((linea = br.readLine()) != null){
              String[] datos = linea.split("\\|");
              String codigoProducto = datos[0];
              CategoriaProducto categoria = CategoriaProducto.valueOf(datos[1].toUpperCase());
              String nombre = datos[2];
              double precio = Double.parseDouble(datos[3]);
              int stock = Integer.parseInt(datos[4]);
              Producto producto = new Producto(codigoProducto, categoria, nombre, precio, stock);
              listaProductos.add(producto);
            }
        }catch (IOException e){
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
    }
    public void cargarPedidos(){
        try (BufferedReader br = new BufferedReader(new FileReader("Pedidos.txt"))){
            String linea;
            br.readLine();
            while ((linea = br.readLine()) != null){
                String[] datos = linea.split("\\|");
                String codigoPedido = datos[0];
                LocalDate fecha = LocalDate.parse(datos[1]);
                String codigoProducto = datos[2];
                int cantidad = Integer.parseInt(datos[3]);
                double valorPagado = Double.parseDouble(datos[4]);
                EstadoProducto estado = EstadoProducto.valueOf(datos[5]);
                String codigoRepartidor = datos[6];
                Pedido p = new Pedido(codigoPedido, fecha, codigoProducto, cantidad, valorPagado, estado, codigoRepartidor);
                listaPedidos.add(p);
            }
        }catch (IOException e){
            System.out.println("Error al leer el archivo de pedidos: " + e.getMessage());
        }
    }
    public void notificar(Cliente cliente, Pedido pedido) {
        String destinatario = cliente.getCorreo();
        String asunto = "Pedido realizado";
        String mensaje = "El cliente " + cliente.getNombres() + " " + cliente.getApellidos() +
                " ha realizado un pedido con código " + pedido.getCodigoPedido() +
                " el día " + pedido.getFecha() + ".\n\n" +
                "Producto: " + pedido.getCodigoProducto() + "\n" +
                "Cantidad: " + pedido.getCantidad() + "\n" +
                "Valor pagado: $" + pedido.getValorPagado() + "\n" +
                "Estado inicial: " + pedido.getEstado() + "\n\n" +
                "Gracias por su compra. Recibirá actualizaciones del estado de su pedido por este medio.";

        ServicioCorreo.enviarCorreo(destinatario, asunto, mensaje, correoSistema, claveSistema);
    }
    public void notificar(Repartidor repartidor, Pedido pedido, Cliente cliente) {
        String destinatario = repartidor.getCorreo();
        String asunto = "Nuevo pedido asignado";
        String mensaje = "Estimado/a " + repartidor.getNombres() + " " + repartidor.getApellidos() + ",\n\n" +
                "Se le ha asignado un nuevo pedido con los siguientes detalles:\n\n" +
                "Código del pedido: " + pedido.getCodigoPedido() + "\n" +
                "Fecha del pedido: " + pedido.getFecha() + "\n" +
                "Cliente: " + cliente.getNombres() + " " + cliente.getApellidos() + "\n" +
                "Estado actual: " + pedido.getEstado() + "\n\n" +
                "Por favor, prepare la logística necesaria para la entrega.\n\n" +
                "Gracias por su trabajo.";

        ServicioCorreo.enviarCorreo(destinatario, asunto, mensaje, correoSistema, claveSistema);
    }
     public void notificarCambioEstado(Cliente cliente, Pedido pedido) {
        String destinatario = cliente.getCorreo();
        String asunto = "Actualización del estado de su pedido";
        String mensaje = "Estimado/a " + cliente.getNombres() + " " + cliente.getApellidos() + ",\n\n" +
                "Le informamos que el estado de su pedido con código " + pedido.getCodigoPedido() +
                " ha cambiado a: " + pedido.getEstado() + ".\n\n" +
                "Fecha del pedido: " + pedido.getFecha() + "\n" +
                "Producto: " + pedido.getCodigoProducto() + "\n" +
                "Repartidor asignado: " + pedido.getCodigoRepartidor() + "\n\n" +
                "Gracias por confiar en nosotros.";

        ServicioCorreo.enviarCorreo(destinatario, asunto, mensaje, correoSistema, claveSistema);
    }
}
