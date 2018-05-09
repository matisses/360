package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class HistoricoClienteDTO {

    private Integer id;
    private Integer cantidadVenta;
    private Integer cantidadDevolucion;
    private Double precioSinIva;
    private Double costo;
    private Double porcentajeUtilidad;
    private String fuente;
    private String factura;
    private String referencia;
    private String refCorta;
    private String descripcion;
    private String refProv;
    private String devolucion;
    private String codDpto;
    private String nomDpto;
    private String codGrupo;
    private String nomGrupo;
    private String codSubgrupo;
    private String nomSubgrupo;
    private String modelo;
    private String proveedorExterior;
    private String nit;
    private String comentario;
    private Date fechaDevolucion;
    private Date fechaFactura;

    public HistoricoClienteDTO() {
    }

    public HistoricoClienteDTO(Integer id, Integer cantidadVenta, Integer cantidadDevolucion, Double precioSinIva, Double costo, Double porcentajeUtilidad, String fuente,
            String factura, String referencia, String refCorta, String descripcion, String refProv, String devolucion, String codDpto, String nomDpto, String codGrupo,
            String nomGrupo, String codSubgrupo, String nomSubgrupo, String modelo, String proveedorExterior, String nit, String comentario, Date fechaDevolucion, Date fechaFactura) {
        this.id = id;
        this.cantidadVenta = cantidadVenta;
        this.cantidadDevolucion = cantidadDevolucion;
        this.precioSinIva = precioSinIva;
        this.costo = costo;
        this.porcentajeUtilidad = porcentajeUtilidad;
        this.fuente = fuente;
        this.factura = factura;
        this.referencia = referencia;
        this.refCorta = refCorta;
        this.descripcion = descripcion;
        this.refProv = refProv;
        this.devolucion = devolucion;
        this.codDpto = codDpto;
        this.nomDpto = nomDpto;
        this.codGrupo = codGrupo;
        this.nomGrupo = nomGrupo;
        this.codSubgrupo = codSubgrupo;
        this.nomSubgrupo = nomSubgrupo;
        this.modelo = modelo;
        this.proveedorExterior = proveedorExterior;
        this.nit = nit;
        this.comentario = comentario;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaFactura = fechaFactura;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(Integer cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public Integer getCantidadDevolucion() {
        return cantidadDevolucion;
    }

    public void setCantidadDevolucion(Integer cantidadDevolucion) {
        this.cantidadDevolucion = cantidadDevolucion;
    }

    public Double getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(Double precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(Double porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getRefCorta() {
        return refCorta;
    }

    public void setRefCorta(String refCorta) {
        this.refCorta = refCorta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRefProv() {
        return refProv;
    }

    public void setRefProv(String refProv) {
        this.refProv = refProv;
    }

    public String getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(String devolucion) {
        this.devolucion = devolucion;
    }

    public String getCodDpto() {
        return codDpto;
    }

    public void setCodDpto(String codDpto) {
        this.codDpto = codDpto;
    }

    public String getNomDpto() {
        return nomDpto;
    }

    public void setNomDpto(String nomDpto) {
        this.nomDpto = nomDpto;
    }

    public String getCodGrupo() {
        return codGrupo;
    }

    public void setCodGrupo(String codGrupo) {
        this.codGrupo = codGrupo;
    }

    public String getNomGrupo() {
        return nomGrupo;
    }

    public void setNomGrupo(String nomGrupo) {
        this.nomGrupo = nomGrupo;
    }

    public String getCodSubgrupo() {
        return codSubgrupo;
    }

    public void setCodSubgrupo(String codSubgrupo) {
        this.codSubgrupo = codSubgrupo;
    }

    public String getNomSubgrupo() {
        return nomSubgrupo;
    }

    public void setNomSubgrupo(String nomSubgrupo) {
        this.nomSubgrupo = nomSubgrupo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getProveedorExterior() {
        return proveedorExterior;
    }

    public void setProveedorExterior(String proveedorExterior) {
        this.proveedorExterior = proveedorExterior;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }
}
