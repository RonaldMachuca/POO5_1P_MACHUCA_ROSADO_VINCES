package com.example.POO4_1P_MACHUCA_ROSADO_VINCES;

import java.util.Scanner;

public class Repartidor extends Usuario{
    //variables de instancia
    private String empresa;
    private String email;
    //constructor de repartidor para inicializar las variables
    public Repartidor(String codigoUnico, String cedula, String nombres, String Apellidos, String celular, String empresa, String email){
        super(codigoUnico, cedula, nombres, Apellidos);
        this.empresa=empresa;
        this.email=email;
    }
    public void consultarPedido(){      // consultar pedidos aun incompleto
        Scanner sc = new Scanner(System.in);
        System.out.println("===== PEDIDOS ASIGNADOS =====");
        System.out.println("Ingrese el codigo del pedido: ");
        String codPed = sc.nextLine();
    }
    //getters y setters
    public String getEmpresa(){
        return empresa;
    }
    public void setEmpresa(String empresa){
        this.empresa=empresa;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    // sobrecarga de toString para crear los repartidores siguiendo el formato .txt
    @Override
    public String toString(){
        return super.getCodigoUnico()+"|"+super.cedula+"|"+super.getNombres()+"|"+super.getApellidos()+"|"+getEmpresa()+"|"+getEmail();
    }
}
