package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class SucursalBancoComprasDTO {

    private Integer idSucursalBanco;
    private String direccion;
    private String swift;
    private BancoComprasDTO bancoCompras;
    private CiudadDTO idCiudad;

    public SucursalBancoComprasDTO() {
        bancoCompras = new BancoComprasDTO();
    }

    public SucursalBancoComprasDTO(Integer idSucursalBanco) {
        this.idSucursalBanco = idSucursalBanco;
    }

    public SucursalBancoComprasDTO(Integer idSucursalBanco, String direccion, String swift, BancoComprasDTO bancoCompras, CiudadDTO idCiudad) {
        this.idSucursalBanco = idSucursalBanco;
        this.direccion = direccion;
        this.swift = swift;
        this.bancoCompras = bancoCompras;
        this.idCiudad = idCiudad;
    }

    public Integer getIdSucursalBanco() {
        return idSucursalBanco;
    }

    public void setIdSucursalBanco(Integer idSucursalBanco) {
        this.idSucursalBanco = idSucursalBanco;
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

    public BancoComprasDTO getBancoCompras() {
        return bancoCompras;
    }

    public void setBancoCompras(BancoComprasDTO bancoCompras) {
        this.bancoCompras = bancoCompras;
    }

    public CiudadDTO getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(CiudadDTO idCiudad) {
        this.idCiudad = idCiudad;
    }
}
