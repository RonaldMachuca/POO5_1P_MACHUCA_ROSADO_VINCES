package com.example.POO4_1P_MACHUCA_ROSADO_VINCES;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;


public class Archivos{
    public static ArrayList<Producto> LeeFicheroProducto(){     //metodo para leer el archivo.txt
        ArrayList<Producto> productos = new ArrayList<>();      //inicializar el Array de productos
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try{
            //
            //
            archivo = new File ("Productos.txt");
            fr=new FileReader(archivo,StandardCharsets.UTF_8);
            br = new BufferedReader(fr);

            //lectura del fichero
            String linea;
            //Para saltar la primera linea
            br.readLine();
            while ((linea = br.readLine()) != null) {
                
                
                String[] datos = linea.split("\\|");    //separamos los datos
                if (datos.length == 5) {
                    String codigo = datos[0];
                    String categoria = datos[1];
                    String nombre = datos[2];
                    double precio = Double.parseDouble(datos[3]);
                    int stock = Integer.parseInt(datos[4]);
                    if(categoria.equals("Ropa")){   //clasificamos y asignamos una categoria del producto
                        CategoriaProducto categoriaProducto = CategoriaProducto.ROPA;
                        productos.add(new Producto(codigo, categoriaProducto, nombre, precio, stock));  //agregamos el objeto al Array
                        
                    }else if(categoria.equals("Tecnología")){   //clasificamos y asignamos una categoria del producto
                        CategoriaProducto categoriaProducto = CategoriaProducto.TECNOLOGIA;
                        productos.add(new Producto(codigo, categoriaProducto, nombre, precio, stock));  //agregamos el objeto al Array
                        
                    }else if(categoria.equals("Hogar")){    //clasificamos y asignamos una categoria del producto
                        CategoriaProducto categoriaProducto = CategoriaProducto.HOGAR;
                        productos.add(new Producto(codigo, categoriaProducto, nombre, precio, stock));  //agregamos el objeto al Array

                    }else if(categoria.equals("Deportes")){     //clasificamos y asignamos una categoria del producto
                        CategoriaProducto categoriaProducto = CategoriaProducto.DEPORTE;
                        productos.add(new Producto(codigo, categoriaProducto, nombre, precio, stock));  //agregamos el objeto al Array
                        
                    }
                    
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        
        
        return productos;

    }

    public static void EscribirArchivoPedidos(String linea){    //metodo para escribir un archivo.txt segun lo que le mandemos
        FileWriter fichero = null;
        BufferedWriter bw = null;

        try{
            File archivo = new File("Pedidos.txt");
            boolean archivoExiste = archivo.exists();
            boolean archivoVacio =! archivoExiste && archivo.length() == 0; 
            fichero = new FileWriter(archivo,true);
            bw = new BufferedWriter(fichero);
            if(archivoVacio){
                bw.write("CodigoPedido|Fecha|CodigoProducto|Cantidad|ValorPagado|Estado|CodigoRepartidor"+"\n");    //para escribir la primera linea para mostrar como se divide la informacion
            }
            
            bw.write(linea+"\n");   //para escribir lo que le mandemos en el parametro
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    //fichero.close();
                    bw.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static ArrayList<Pedido> LeeFicheroPedidos(){        //metodo para leer un archivo .txt y agregar a una Array
        ArrayList<Pedido> pedidos = new ArrayList<>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try{
            //
            //
            archivo = new File ("Pedidos.txt");     //archivo que vamos a leer
            fr=new FileReader(archivo,StandardCharsets.UTF_8);
            br = new BufferedReader(fr);

            //lectura del fichero
            String linea;
            //Para saltar la primera linea
            br.readLine();
            while ((linea = br.readLine()) != null) {
                
                
                String[] datos = linea.split("\\|");
                if (datos.length == 7) {
                    String codigoPedido = datos[0];
                    LocalDate fecha = LocalDate.parse(datos[1]);
                    String codigoProducto = datos[2];
                    int cantidad = Integer.parseInt(datos[3]);
                    double valor = Double.parseDouble(datos[4]);
                    String estado = datos[5];
                    String codigoRepartidor = datos[6];
                    if(estado.equals("EN_PREPARACION")){    //clasificamos y asignamos una categoria del producto
                        EstadoProducto estadoProducto = EstadoProducto.EN_PREPARACION;
                        pedidos.add(new Pedido(codigoPedido, fecha, codigoProducto, cantidad, valor, estadoProducto, codigoRepartidor));    //agregamos el objeto al Array
                        
                    }else if(estado.equals("EN_RUTA")){     //clasificamos y asignamos una categoria del producto
                        EstadoProducto estadoProducto = EstadoProducto.EN_RUTA;
                        pedidos.add(new Pedido(codigoPedido, fecha, codigoProducto, cantidad, valor, estadoProducto, codigoRepartidor));    //agregamos el objeto al Array

                        
                    }else if(estado.equals("ENTREGADO")){       //clasificamos y asignamos una categoria del producto
                        EstadoProducto estadoProducto = EstadoProducto.ENTREGADO;
                        pedidos.add(new Pedido(codigoPedido, fecha, codigoProducto, cantidad, valor, estadoProducto, codigoRepartidor));    //agregamos el objeto al Array
                    }
                    
                    
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return pedidos;

    }

    public static ArrayList<Repartidor> LeeFicheroRepartidores(){
        ArrayList<Repartidor> repartidores = new ArrayList<>();
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try{
            //
            //
            archivo = new File ("Repartidores.txt");    //archivo que vamos a leer
            fr=new FileReader(archivo,StandardCharsets.UTF_8);
            br = new BufferedReader(fr);

            //lectura del fichero
            String linea;
            //Para saltar la primera linea
            br.readLine();
            while ((linea = br.readLine()) != null) {
                
                
                String[] datos = linea.split("\\|");
                if (datos.length == 6) {
                    String codigoUnico = datos[0];
                    String cedula = datos[1];
                    String nombres = datos[2];
                    String apellidos = datos[3];
                    String empresa = datos[4];
                    String email = datos[5];
                    repartidores.add(new Repartidor(codigoUnico, cedula, nombres, apellidos, apellidos, empresa, email));   //agregamos el objeto al Array
                    
                    
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return repartidores;

    }


}


