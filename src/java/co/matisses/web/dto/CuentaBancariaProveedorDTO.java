package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class CuentaBancariaProveedorDTO {

    private Integer idCuentaBancaria;
    private String cuentaBancaria;
    private String iban;
    private String aba;
    private String codProveedor;
    private SucursalBancoComprasDTO sucursales;

    public CuentaBancariaProveedorDTO() {
        sucursales = new SucursalBancoComprasDTO();
    }

    public CuentaBancariaProveedorDTO(Integer idCuentaBancaria, String cuentaBancaria, String iban, String aba, String codProveedor, SucursalBancoComprasDTO sucursales) {
        this.idCuentaBancaria = idCuentaBancaria;
        this.cuentaBancaria = cuentaBancaria;
        this.iban = iban;
        this.aba = aba;
        this.codProveedor = codProveedor;
        this.sucursales = sucursales;
    }

    public Integer getIdCuentaBancaria() {
        return idCuentaBancaria;
    }

    public void setIdCuentaBancaria(Integer idCuentaBancaria) {
        this.idCuentaBancaria = idCuentaBancaria;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getAba() {
        return aba;
    }

    public void setAba(String aba) {
        this.aba = aba;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public SucursalBancoComprasDTO getSucursales() {
        return sucursales;
    }

    public void setSucursales(SucursalBancoComprasDTO sucursales) {
        this.sucursales = sucursales;
    }
}
