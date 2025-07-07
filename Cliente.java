package com.funcionamiento;

import com.archivo.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Representa a un cliente del sistema, que extiende de la clase Usuario.
 * Un cliente puede realizar acciones como comprar productos y gestionar pedidos.
 */
public class Cliente extends Usuario {
    /** Número de celular del cliente. */
    private String celular;

    /** Dirección de entrega del cliente. */
    private String direccion;
    /**
     * Constructor para crear un nuevo Cliente.
     *
     * @param codigoUnico código único del cliente
     * @param cedula cédula de identidad del cliente
     * @param nombres nombres del cliente
     * @param Apellidos apellidos del cliente
     * @param usuario nombre de usuario del cliente
     * @param contrasena contraseña del cliente
     * @param correo correo electrónico del cliente
     * @param rol rol asignado al cliente (como carácter)
     * @param celular número de celular del cliente
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


    public int mostrarMenu(){   /**
     * Muestra el menú disponible para el cliente.
     *
     * @return la opción seleccionada por el cliente
     */
        Scanner sc = new Scanner(System.in);
        System.out.println("Menú de Cliente"+"\n"+"1. Comprar Producto"+"\n"+"2. Gestionar Pedido"+"\n"+"3. Salir"); //formato del menu
        System.out.println("Seleccione una opcion: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        sc.close();
        return opcion; // retorna una opcion para luego aplicarlo en la clase Sistema
    }

    /**
     * Permite al cliente comprar productos dentro del sistema.
     *
     * @param sistema la instancia del sistema para acceder a productos y repartidores
     */
    public void comprar(Sistema sistema){
        String continuar = "si"; //variable que permitira comprar productos dependiendo del cliente
        ArrayList<Producto> productos = Archivo.LeeFicheroProducto();  //lista de productos con el metodo de la clase Archivos
        List<Repartidor> repartidores = sistema.getRepartidores(); //lista de productos con el metodo de la clase Archivos
        do{         //Se utiliza el do para repetir la linea del codigo
            Scanner sc = new Scanner(System.in);
            System.out.println("Categorias disponibles: "+"\n 1."+CategoriaProducto.DEPORTES+"\n 2."+CategoriaProducto.HOGAR+"\n 3."+CategoriaProducto.ROPA+"\n 4."+CategoriaProducto.TECNOLOGIA);   //muestra las categorias disponibles
            System.out.println("Ingrese la opcion para consultar: ");
            int opcion = sc.nextInt();  //pide al usuario la opcion y se la guarda en una variable
            int i=0;    //variable de control
            Random random = new Random(); //se instancia random
            if(opcion==1){ 
                ArrayList<Producto> productos1 = new ArrayList<>(); //lista nueva inicializada para agrupar los productos que cumplen con lo pedido por el cliente
                for(Producto p : productos){        //for each para recorrer el ArrayList de productos
                    if(p.getCategoriaProducto()==CategoriaProducto.DEPORTES){    //comparar categoria del producto
                        productos1.add(p);      //Se agrega a la lista nueva para mostrar solo los productos que cumplen
                        System.out.println((i+1)+"."+ p);   //imprime las opciones con formato para el cliente pueda elegir
                        i++;
                    }
                }
                System.out.println("Elija la opcion del producto que va a comprar:");
                int opcion1=sc.nextInt();   //pedimos al cliente que elija y guardamos su eleccion
                sc.nextLine();
                System.out.println("Ingrese la cantidad que desea comprar");
                int cantidad = sc.nextInt();    //le pedimos que ingrese una cantidad y la almacenamos
                for(int i1=0;i1<productos1.size();i1++){    //for para recorrer el Array con los productos que cumplen
                    if(opcion1==(i1+1)){    //comprueba que se escoga la opcion correcta
                        for(Producto p : productos1){   //for each recorriendo el Array con los productos que cumplen
                        
                            if(cantidad<=p.getStock()){     //valida que el producto tenga suficiente stock
                                double total = (cantidad*p.getPrecio());    //formula para el total
                                System.out.println("El total a pagar es: "+ total);     // imprime el total a pagar
                                System.out.println("Ingrese su numero de Tarjeta");     
                                int numTar = sc.nextInt();      //le pedimos que ingrese un numero de tarjeta y la almacenamos
                                sc.nextLine();
                                System.out.println("Su tarjeta de num: "+numTar+" Fue aceptada");   //mensaje para validar la tarjeta
                                if (repartidores.isEmpty()) {
                                     System.out.println("Error: No hay repartidores registrados. No se puede completar el pedido.");
                                     return;
                                    }
                                    Repartidor repartidorAsignado = repartidores.get(random.nextInt(repartidores.size()));
                                LocalDate fecha = LocalDate.now();  //inicializamos la fecha cuando ocurre el pedido
                                Pedido pedido = new Pedido(p.getCodigoProducto(), fecha, cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico());   //creamos el pedido con las variables que recolectamos
                                Archivo.EscribirArchivoPedidos(pedido.toString()); //metodo para escribir un txt. agregando nuestro pedido
                                System.out.println("La compra fue exitosa");    //validamos que se agrego el pedido correctamente
                                System.out.println("Desea comprar otra cosa?");     //preguntamos al cliente si quiere agregar otro pedido
                                String continuar1 = sc.nextLine();
                                continuar = continuar1; // para salir o mantenerse en el do
                            }else{
                                System.out.println("No hay suficiente stock");  //Por si no hay suficiente stock
                                System.out.println("Desea comprar otra cosa?"); //preguntamos al cliente si quiere agregar otro pedido
                                String continuar1 = sc.nextLine();
                                continuar = continuar1;     // para salir o mantenerse en el do
                            }
                        }
                    }
                }
            }else if(opcion==2){
                ArrayList<Producto> productos1 = new ArrayList<>();     //lista nueva inicializada para agrupar los productos que cumplen con lo pedido por el cliente
                for(Producto p : productos){     //for each para recorrer el ArrayList de productos
                    if(p.getCategoriaProducto()==CategoriaProducto.HOGAR){  //comparar categoria del producto
                        productos1.add(p);      //Se agrega a la lista nueva para mostrar solo los productos que cumplen
                        System.out.println((i+1)+"."+ p);   //imprime las opciones con formato para el cliente pueda elegir
                        i++;
                    }
                }
                System.out.println("Elija la opcion del producto que va a comprar:");
                int opcion1=sc.nextInt();       //pedimos al cliente que elija y guardamos su eleccion
                sc.nextLine();
                System.out.println("Ingrese la cantidad que desea comprar");
                int cantidad = sc.nextInt();    //le pedimos que ingrese una cantidad y la almacenamos
                double total;
                for(int i1=0;i1<productos1.size();i1++){    //for para recorrer el Array con los productos que cumplen
                    if(opcion1==(i1+1)){        //comprueba que se escoga la opcion correcta
                        if(cantidad<=productos1.get(i1).getStock()){        //valida que el producto tenga suficiente stock
                            total=cantidad*(productos1.get(i1).getPrecio());    //formula para el total
                            System.out.println("El total a pagar es: "+ total);     // imprime el total a pagar
                            System.out.println("Ingrese su numero de Tarjeta");
                            int numTar = sc.nextInt();      //le pedimos que ingrese un numero de tarjeta y la almacenamos
                            sc.nextLine();
                            System.out.println("Su tarjeta de num: "+numTar+" Fue aceptada");   //mensaje para validar la tarjeta
                            Repartidor repartidorAsignado=repartidores.get(random.nextInt(repartidores.size()));    //asignamos de manera aleatoria a un repartidor al pedido
                            LocalDate fecha = LocalDate.now();      //inicializamos la fecha cuando ocurre el pedido
                            Pedido pedido = new Pedido(productos1.get(i1).getCodigoProducto(), fecha, cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico());  //creamos el pedido con las variables que recolectamos
                            Archivo.EscribirArchivoPedidos(pedido.toString());     //metodo para escribir un txt. agregando nuestro pedido
                            System.out.println("La compra fue exitosa");        //validamos que se agrego el pedido correctamente
                            System.out.println("¿Desea comprar otra cosa?: ");     //preguntamos al cliente si quiere agregar otro pedido
                            String continuar1 = sc.nextLine();
                            continuar = continuar1;     // para salir o mantenerse en el do
                        }else{
                            System.out.println("No hay suficiente stock");      //Por si no hay suficiente stock
                            System.out.println("¿Desea comprar otra cosa?: ");     //preguntamos al cliente si quiere agregar otro pedido
                            String continuar1 = sc.nextLine();
                            continuar = continuar1;     // para salir o mantenerse en el do
                        }
                    }
                }
                
            }else if(opcion==3){        
                ArrayList<Producto> productos1 = new ArrayList<>();   //lista nueva inicializada para agrupar los productos que cumplen con lo pedido por el cliente  
                for(Producto p : productos){            //for each para recorrer el ArrayList de productos
                    if(p.getCategoriaProducto()==CategoriaProducto.ROPA){   //comparar categoria del producto
                        productos1.add(p);      //Se agrega a la lista nueva para mostrar solo los productos que cumplen
                        System.out.println((i+1)+"."+p);    //imprime las opciones con formato para el cliente pueda elegir
                        i++;
                    }
                }
                System.out.println("Elija la opcion del producto que va a comprar:");
                int opcion1=sc.nextInt();       //pedimos al cliente que elija y guardamos su eleccion
                sc.nextLine();
                System.out.println("Ingrese la cantidad que desea comprar");
                int cantidad = sc.nextInt();        //le pedimos que ingrese una cantidad y la almacenamos
                double total;
                for(int i1=0;i1<productos1.size();i1++){    //for para recorrer el Array con los productos que cumplen
                    if(opcion1==(i1+1)){        //comprueba que se escoga la opcion correcta
                        if(cantidad<=productos1.get(i1).getStock()){    //valida que el producto tenga suficiente stock
                            total=cantidad*(productos1.get(i1).getPrecio());    //formula para el total
                            System.out.println("El total a pagar es: "+ total);     // imprime el total a pagar
                            System.out.println("Ingrese su numero de Tarjeta");
                            int numTar = sc.nextInt();      //le pedimos que ingrese un numero de tarjeta y la almacenamos
                            sc.nextLine();
                            System.out.println("Su tarjeta de num: "+numTar+" Fue aceptada");   //mensaje para validar la tarjeta
                            Repartidor repartidorAsignado=repartidores.get(random.nextInt(repartidores.size()));    //asignamos de manera aleatoria a un repartidor al pedido
                            LocalDate fecha = LocalDate.now();      //inicializamos la fecha cuando ocurre el pedido
                            Pedido pedido = new Pedido(productos1.get(i1).getCodigoProducto(), fecha, cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico());  //creamos el pedido con las variables que recolectamos
                            Archivo.EscribirArchivoPedidos(pedido.toString()); //metodo para escribir un txt. agregando nuestro pedido
                            System.out.println("La compra fue exitosa");        //validamos que se agrego el pedido correctamente
                            System.out.println("Desea comprar otra cosa?");     //preguntamos al cliente si quiere agregar otro pedido
                            String continuar1 = sc.nextLine();
                            continuar = continuar1;     // para salir o mantenerse en el do
                        }else{
                            System.out.println("No hay suficiente stock");      //Por si no hay suficiente stock
                            System.out.println("Desea comprar otra cosa?");     //preguntamos al cliente si quiere agregar otro pedido
                            String continuar1 = sc.nextLine();
                            continuar = continuar1;     // para salir o mantenerse en el do
                        }
                    }
                }
            }else if(opcion==4){ 
                ArrayList<Producto> productos1 = new ArrayList<>();   //lista nueva inicializada para agrupar los productos que cumplen con lo pedido por el cliente 
                for(Producto p : productos){            //for each para recorrer el ArrayList de productos
                    if(p.getCategoriaProducto()==CategoriaProducto.TECNOLOGIA){ //comparar categoria del producto
                        productos1.add(p);      //Se agrega a la lista nueva para mostrar solo los productos que cumplen
                        System.out.println((i+1)+"." +p);   //imprime las opciones con formato para el cliente pueda elegir
                        i++;
                    }
                }
                System.out.println("Elija la opcion del producto que va a comprar:");
                int opcion1=sc.nextInt();       //pedimos al cliente que elija y guardamos su eleccion
                sc.nextLine();
                System.out.println("Ingrese la cantidad que desea comprar");
                int cantidad = sc.nextInt();        //le pedimos que ingrese una cantidad y la almacenamos
                double total;
                for(int i1=0;i1<productos1.size();i1++){    //for para recorrer el Array con los productos que cumplen
                    if(opcion1==(i1+1)){        //comprueba que se escoga la opcion correcta
                        if(cantidad<=productos1.get(i1).getStock()){    //valida que el producto tenga suficiente stock
                            total=cantidad*(productos1.get(i1).getPrecio());    //formula para el total
                            System.out.println("El total a pagar es: "+ total); // imprime el total a pagar
                            System.out.println("Ingrese su numero de Tarjeta");
                            int numTar = sc.nextInt();      //le pedimos que ingrese un numero de tarjeta y la almacenamos
                            sc.nextLine();
                            System.out.println("Su tarjeta de num: "+numTar+" Fue aceptada");   //mensaje para validar la tarjeta
                            Repartidor repartidorAsignado=repartidores.get(random.nextInt(repartidores.size()));    //asignamos de manera aleatoria a un repartidor al pedido
                            LocalDate fecha = LocalDate.now();      //inicializamos la fecha cuando ocurre el pedido
                            Pedido pedido = new Pedido(productos1.get(i1).getCodigoProducto(),fecha, cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico());      //creamos el pedido con las variables que recolectamos
                            Archivo.EscribirArchivoPedidos(pedido.toString());     //metodo para escribir un txt. agregando nuestro pedido
                            System.out.println("La compra fue exitosa");        //validamos que se agrego el pedido correctamente
                            System.out.println("Desea comprar otra cosa?");     //preguntamos al cliente si quiere agregar otro pedido
                            String continuar1 = sc.nextLine();
                            continuar = continuar1;     // para salir o mantenerse en el do
                        }else{
                            System.out.println("No hay suficiente stock");      //Por si no hay suficiente stock
                            System.out.println("Desea comprar otra cosa?");     //preguntamos al cliente si quiere agregar otro pedido
                            String continuar1 = sc.nextLine();
                            continuar = continuar1;     // para salir o mantenerse en el do
                        }
                    }
                }
            }
        }while(continuar.equals("si")); //while que repite el codigo si la opcion es "si"
        

    }

   /**
     * Permite al cliente consultar el estado de un pedido existente mediante su código.
     */
    public void gestionarPedido() {
    ArrayList<Producto> productos = Archivo.LeeFicheroProducto();  //Array usando metodo de Archivos
    ArrayList<Repartidor> repartidores = Archivo.LeeFicheroRepartidores();     //Array usando metodo de Archivos
    ArrayList<Pedido> pedidos = Archivo.LeeFicheroPedidos();    //Array usando metodo de Archivos

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
            System.out.println("Estado Actual: " + p.getEstadoProducto());

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
    //getters y setters
    /**
     * Obtiene el número de celular del cliente.
     * @return el celular del cliente
     */
    public String getCelular(){
        return celular;
    }
    /**
     * Establece el número de celular del cliente.
     * @param celular el nuevo número de celular
     */
    public void setCelular(String celular){
        this.celular=celular;
    }
    /**
     * Obtiene la dirección del cliente.
     * @return la dirección del cliente
     */
    public String getDireccion(){
        return direccion;
    }
}