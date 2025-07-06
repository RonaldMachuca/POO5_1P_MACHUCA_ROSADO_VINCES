<<<<<<< HEAD
package com.example.POO4_1P_MACHUCA_ROSADO_VINCES;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;


public class Cliente extends Usuario {
    //variables de instancia
    private String celular;
    private String direccion;
    //constructor de Cliente para inicializar las variables
    public Cliente(String codigoUnico, String cedula, String nombres, String Apellidos, String celular, String direccion){
        super(codigoUnico, cedula, nombres, Apellidos);
        this.celular=celular;
        this.direccion=direccion;
    }

    public int mostrarMenu(){   //metodo mostrarMenu
        Scanner sc = new Scanner(System.in);
        System.out.println("Menú de Cliente"+"\n"+"1. Comprar Producto"+"\n"+"2. Gestionar Pedido"+"\n"+"3. Salir"); //formato del menu
        System.out.println("Seleccione una opcion: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        sc.close();
        return opcion; // retorna una opcion para luego aplicarlo en la clase Sistema
    }

    public void comprar(){  //metodo comprar
        String continuar = "si"; //variable que permitira comprar productos dependiendo del cliente
        ArrayList<Producto> productos = Archivos.LeeFicheroProducto();  //lista de productos con el metodo de la clase Archivos
        ArrayList<Repartidor> repartidores = Archivos.LeeFicheroRepartidores(); //lista de productos con el metodo de la clase Archivos
        do{         //Se utiliza el do para repetir la linea del codigo
            Scanner sc = new Scanner(System.in);
            System.out.println("Categorias disponibles: "+"\n 1."+CategoriaProducto.DEPORTE+"\n 2."+CategoriaProducto.HOGAR+"\n 3."+CategoriaProducto.ROPA+"\n 4."+CategoriaProducto.TECNOLOGIA);   //muestra las categorias disponibles
            System.out.println("Ingrese la opcion para consultar: ");
            int opcion = sc.nextInt();  //pide al usuario la opcion y se la guarda en una variable
            int i=0;    //variable de control
            Random random = new Random(); //se instancia random
            if(opcion==1){ 
                ArrayList<Producto> productos1 = new ArrayList<>(); //lista nueva inicializada para agrupar los productos que cumplen con lo pedido por el cliente
                for(Producto p : productos){        //for each para recorrer el ArrayList de productos
                    if(p.getCategoriaProducto()==CategoriaProducto.DEPORTE){    //comparar categoria del producto
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
                                Repartidor repartidorAsignado=repartidores.get(random.nextInt(repartidores.size()));    //asignamos de manera aleatoria a un repartidor al pedido
                                LocalDate fecha = LocalDate.now();  //inicializamos la fecha cuando ocurre el pedido
                                Pedido pedido = new Pedido(fecha,p.getCodigoProducto(), cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico());   //creamos el pedido con las variables que recolectamos
                                Archivos.EscribirArchivoPedidos(pedido.toString()); //metodo para escribir un txt. agregando nuestro pedido
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
                            Pedido pedido = new Pedido(fecha,productos1.get(i1).getCodigoProducto(), cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico());  //creamos el pedido con las variables que recolectamos
                            Archivos.EscribirArchivoPedidos(pedido.toString());     //metodo para escribir un txt. agregando nuestro pedido
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
                            Pedido pedido = new Pedido(fecha,productos1.get(i1).getCodigoProducto(), cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico());  //creamos el pedido con las variables que recolectamos
                            Archivos.EscribirArchivoPedidos(pedido.toString()); //metodo para escribir un txt. agregando nuestro pedido
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
                            Pedido pedido = new Pedido(fecha,productos1.get(i1).getCodigoProducto(), cantidad, total, EstadoProducto.EN_PREPARACION, repartidorAsignado.getCodigoUnico());      //creamos el pedido con las variables que recolectamos
                            Archivos.EscribirArchivoPedidos(pedido.toString());     //metodo para escribir un txt. agregando nuestro pedido
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

    public void consultarPedido(){      //metodo para consultar pedido
        ArrayList<Producto> productos = Archivos.LeeFicheroProducto();  //Array usando metodo de Archivos
        ArrayList<Repartidor> repartidores = Archivos.LeeFicheroRepartidores();     //Array usando metodo de Archivos
        ArrayList<Pedido> pedidos =Archivos.LeeFicheroPedidos();    //Array usando metodo de Archivos
        Scanner sc = new Scanner(System.in);
        System.out.println("===== CONSULTA DE ESTADO DE PEDIDO =====");
        System.out.println("Ingrese el codigo del pedido: ");   //le pedimos al cliente que ingrese el codigo de un pedido y lo almacenamos
        String codPed = sc.nextLine();
        for(Pedido p :pedidos){     // for each para recorrer el Array de pedidos
            if(codPed.equals(p.getCodigoPedido())){     //comparamos el codigo ingresado pr el cliente con algun codigo del Array de pedidos
                System.out.println("Fecha del pedido"+p.getFecha());    //formato del pedido
                Producto producto = p.buscarProductoPorCodigo(p.getCodigoProducto(), productos);    //implementacion del metodo para buscar un producto por codigo
                Repartidor repartidor = p.buscarRepartidorPorCodigo(p.getCodigoRepartidor(), repartidores); //implementacion del metodo para buscar un repartidor por codigo
                System.out.println("Producto comprado: "+producto.getNombreProducto()+" (Codigo: "+producto.getCodigoProducto()+")");
                System.out.println("Cantidad: "+p.getCantidad());
                System.out.println("Valor Pagado: $"+p.getValor());
                System.out.println("Estado Actual: "+p.getEstadoProducto());
                System.out.println("Repartidor: "+repartidor.getNombres()+" "+repartidor.getApellidos()+"\n");
                System.out.println("Su pedido esta siendo preparado para su envio");    //validacion

            }
        }
    }
    //getters y setters
    public String getCelular(){
        return celular;
    }
    public void setCelular(String celular){
        this.celular=celular;
    }
    public String getDireccion(){
        return direccion;
    }
=======
package com.example;

public class Cliente {
    private String celular;
    private String direccion;

    public Cliente(String celular, String direccion){
        this.celular = celular;
        this.direccion = direccion;
    }

    public String getCelular(){
        return celular;
    }

    public String getDireccion(){
        return direccion;
    }

    public void setCelular(String celular){
        this.celular=celular;
    }

>>>>>>> 12f99d1092efeaea966cf8335599be8d120a1617
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
}
