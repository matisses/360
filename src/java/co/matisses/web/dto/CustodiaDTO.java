package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class CustodiaDTO {

    private int contador;
    private Integer idCustodia;
    private String nombreCustodia;
    private String imagen;
    private Character tipoCelular;
    private Boolean requiereContrasena;
    private Boolean tipoCantidad;
    private Boolean requiereActa;

    public CustodiaDTO() {
    }

    public CustodiaDTO(Integer idCustodia) {
        this.idCustodia = idCustodia;
    }

    public CustodiaDTO(int contador, Integer idCustodia, String nombreCustodia, String imagen, Character tipoCelular, Boolean requiereContrasena, Boolean tipoCantidad, Boolean requiereActa) {
        this.contador = contador;
        this.idCustodia = idCustodia;
        this.nombreCustodia = nombreCustodia;
        this.imagen = imagen;
        this.tipoCelular = tipoCelular;
        this.requiereContrasena = requiereContrasena;
        this.tipoCantidad = tipoCantidad;
        this.requiereActa = requiereActa;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public Integer getIdCustodia() {
        return idCustodia;
    }

    public void setIdCustodia(Integer idCustodia) {
        this.idCustodia = idCustodia;
    }

    public String getNombreCustodia() {
        return nombreCustodia;
    }

    public void setNombreCustodia(String nombreCustodia) {
        this.nombreCustodia = nombreCustodia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Character getTipoCelular() {
        return tipoCelular;
    }

    public void setTipoCelular(Character tipoCelular) {
        this.tipoCelular = tipoCelular;
    }

    public Boolean getRequiereContrasena() {
        return requiereContrasena;
    }

    public void setRequiereContrasena(Boolean requiereContrasena) {
        this.requiereContrasena = requiereContrasena;
    }

    public Boolean getTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(Boolean tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    public Boolean getRequiereActa() {
        return requiereActa;
    }

    public void setRequiereActa(Boolean requiereActa) {
        this.requiereActa = requiereActa;
    }
}
