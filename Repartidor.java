public class Repartidor{
    private String empresa;
    private String email;
    private String codigoRepartidor;


    public Repartidor(String empresa, String email, String codigoRepartidor){
        this.empresa=empresa;
        this.email=email;
        this.codigoRepartidor=codigoRepartidor;
    }


    public String getEmpresa(){
        return empresa;
    }

    public String getEmail(){
        return email;
    }

    public String getCodigoRepartidor(){
        return codigoRepartidor;
    }


    public void setEmpresa(String empresa){
        this.empresa=empresa;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void setCodigoRepartidor(String codigoRepartidor) {
        this.codigoRepartidor = codigoRepartidor;
    }
}