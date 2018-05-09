package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class VentasNetasDTO {

    private Integer idVentasNetas;
    private Integer yearfac;
    private Integer monthfac;
    private Integer dayfac;
    private Integer precioSinIva;
    private String fuente;
    private String factura;
    private String referencia;
    private String refCorta;
    private String descripcion;
    private String refProv;
    private String codDpto;
    private String nomDpto;
    private String codGrupo;
    private String nomGrupo;
    private String codSubgrupo;
    private String nomSubgrupo;
    private String modelo;
    private String codColores;
    private String colores;
    private String codMateriales;
    private String materiales;
    private String proveedor;
    private String nombreWeb;
    private String nit;
    private String comentariosFac;
    private BigDecimal cantidad;
    private BigDecimal costoUnitario;
    private BigDecimal porcentajeUtilidad;
    private Date fecha;

    public VentasNetasDTO() {
    }

    public VentasNetasDTO(Integer idVentasNetas, Integer yearfac, Integer monthfac, Integer dayfac, Integer precioSinIva, String fuente, String factura, String referencia,
            String refCorta, String descripcion, String refProv, String codDpto, String nomDpto, String codGrupo, String nomGrupo, String codSubgrupo, String nomSubgrupo,
            String modelo, String codColores, String colores, String codMateriales, String materiales, String proveedor, String nombreWeb, String nit, String comentariosFac,
            BigDecimal cantidad, BigDecimal costoUnitario, BigDecimal porcentajeUtilidad, Date fecha) {
        this.idVentasNetas = idVentasNetas;
        this.yearfac = yearfac;
        this.monthfac = monthfac;
        this.dayfac = dayfac;
        this.precioSinIva = precioSinIva;
        this.fuente = fuente;
        this.factura = factura;
        this.referencia = referencia;
        this.refCorta = refCorta;
        this.descripcion = descripcion;
        this.refProv = refProv;
        this.codDpto = codDpto;
        this.nomDpto = nomDpto;
        this.codGrupo = codGrupo;
        this.nomGrupo = nomGrupo;
        this.codSubgrupo = codSubgrupo;
        this.nomSubgrupo = nomSubgrupo;
        this.modelo = modelo;
        this.codColores = codColores;
        this.colores = colores;
        this.codMateriales = codMateriales;
        this.materiales = materiales;
        this.proveedor = proveedor;
        this.nombreWeb = nombreWeb;
        this.nit = nit;
        this.comentariosFac = comentariosFac;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.porcentajeUtilidad = porcentajeUtilidad;
        this.fecha = fecha;
    }

    public Integer getIdVentasNetas() {
        return idVentasNetas;
    }

    public void setIdVentasNetas(Integer idVentasNetas) {
        this.idVentasNetas = idVentasNetas;
    }

    public Integer getYearfac() {
        return yearfac;
    }

    public void setYearfac(Integer yearfac) {
        this.yearfac = yearfac;
    }

    public Integer getMonthfac() {
        return monthfac;
    }

    public void setMonthfac(Integer monthfac) {
        this.monthfac = monthfac;
    }

    public Integer getDayfac() {
        return dayfac;
    }

    public void setDayfac(Integer dayfac) {
        this.dayfac = dayfac;
    }

    public Integer getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(Integer precioSinIva) {
        this.precioSinIva = precioSinIva;
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

    public String getCodColores() {
        return codColores;
    }

    public void setCodColores(String codColores) {
        this.codColores = codColores;
    }

    public String getColores() {
        return colores;
    }

    public void setColores(String colores) {
        this.colores = colores;
    }

    public String getCodMateriales() {
        return codMateriales;
    }

    public void setCodMateriales(String codMateriales) {
        this.codMateriales = codMateriales;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombreWeb() {
        return nombreWeb;
    }

    public void setNombreWeb(String nombreWeb) {
        this.nombreWeb = nombreWeb;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getComentariosFac() {
        return comentariosFac;
    }

    public void setComentariosFac(String comentariosFac) {
        this.comentariosFac = comentariosFac;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getPorcentajeUtilidad() {
        return porcentajeUtilidad;
    }

    public void setPorcentajeUtilidad(BigDecimal porcentajeUtilidad) {
        this.porcentajeUtilidad = porcentajeUtilidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
