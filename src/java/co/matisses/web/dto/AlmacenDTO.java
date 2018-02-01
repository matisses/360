package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class AlmacenDTO {

    private Integer intrnalKey;
    private String whsCode;
    private String whsName;
    private String nombreReportes;
    private String visualizar;
    private String velocidad;
    private String prioridad;
    private String nombrextablet;
    private String estado;
    private String placaVehiculo;
    private String codigoVentas;
    private String codigoCiudad;
    private String city;
    private String street;
    private String rutaImg;

    public AlmacenDTO() {
    }

    public AlmacenDTO(String whsCode, String whsName, String nombrextablet) {
        this.whsCode = whsCode;
        this.whsName = whsName;
        this.nombrextablet = nombrextablet;
    }

    public AlmacenDTO(Integer intrnalKey, String whsCode, String whsName, String nombreReportes, String visualizar, String velocidad,
            String prioridad, String nombrextablet, String estado, String placaVehiculo, String codigoVentas, String codigoCiudad,
            String city, String street, String rutaImg) {
        this.intrnalKey = intrnalKey;
        this.whsCode = whsCode;
        this.whsName = whsName;
        this.nombreReportes = nombreReportes;
        this.visualizar = visualizar;
        this.velocidad = velocidad;
        this.prioridad = prioridad;
        this.nombrextablet = nombrextablet;
        this.estado = estado;
        this.placaVehiculo = placaVehiculo;
        this.codigoVentas = codigoVentas;
        this.codigoCiudad = codigoCiudad;
        this.city = city;
        this.street = street;
        this.rutaImg = rutaImg;
    }

    public Integer getIntrnalKey() {
        return intrnalKey;
    }

    public void setIntrnalKey(Integer intrnalKey) {
        this.intrnalKey = intrnalKey;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public String getWhsName() {
        return whsName;
    }

    public void setWhsName(String whsName) {
        this.whsName = whsName;
    }

    public String getNombreReportes() {
        return nombreReportes;
    }

    public void setNombreReportes(String nombreReportes) {
        this.nombreReportes = nombreReportes;
    }

    public String getVisualizar() {
        return visualizar;
    }

    public void setVisualizar(String visualizar) {
        this.visualizar = visualizar;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getNombrextablet() {
        return nombrextablet;
    }

    public void setNombrextablet(String nombrextablet) {
        this.nombrextablet = nombrextablet;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getCodigoVentas() {
        return codigoVentas;
    }

    public void setCodigoVentas(String codigoVentas) {
        this.codigoVentas = codigoVentas;
    }

    public String getCodigoCiudad() {
        return codigoCiudad;
    }

    public void setCodigoCiudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRutaImg() {
        return rutaImg;
    }

    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }
}
