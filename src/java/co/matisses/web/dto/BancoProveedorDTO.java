package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BancoProveedorDTO {

    private Integer idBanco;
    private Integer idCiudad;
    private String razonSocial;
    private String direccion;
    private String swift;
    private String aba;

    public BancoProveedorDTO() {
    }

    public BancoProveedorDTO(Integer idBanco, Integer idCiudad, String razonSocial, String direccion, String swift, String aba) {
        this.idBanco = idBanco;
        this.idCiudad = idCiudad;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.swift = swift;
        this.aba = aba;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }

    public String getAba() {
        return aba;
    }

    public void setAba(String aba) {
        this.aba = aba;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }
}
