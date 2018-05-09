package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class RotacionDTO {

    private Integer idRotacion;
    private Integer cantidadComprada;
    private Integer cantidadVendida;
    private Integer cantidadEntradaNeta;
    private Integer cantidadSalida;
    private Integer saldoVenta;
    private Integer totalComprado;
    private Integer codDpto;
    private Integer precioSinIva;
    private Integer idInforme;
    private Integer saldoTaller;
    private Integer saldoGarantia;
    private Integer entradasCompra;
    private Integer entradasTaller;
    private String referencia;
    private String perfil;
    private String refCorta;
    private String refAduana;
    private String modelo;
    private String refProv;
    private String descripcion;
    private String nomDpto;
    private String codGrupo;
    private String nomGrupo;
    private String codSubGrupo;
    private String nomSubGrupo;
    private String codColores;
    private String colores;
    private String codMateriales;
    private String materiales;
    private String proveedor;
    private String nombreWeb;
    private BigDecimal rotacionPorcentaje;
    private BigDecimal rotacionPromedioDias;
    private BigDecimal rotacionPromedioDiasAvanz;
    private BigDecimal utilidadPromedio;
    private BigDecimal utilidadMinima;
    private BigDecimal utilidadAvanzada;
    private Date fechaUltimaVenta;

    public RotacionDTO() {
    }

    public RotacionDTO(Integer idRotacion, Integer cantidadComprada, Integer cantidadVendida, Integer cantidadEntradaNeta, Integer cantidadSalida, Integer saldoVenta,
            Integer totalComprado, Integer codDpto, Integer precioSinIva, Integer idInforme, Integer saldoTaller, Integer saldoGarantia, Integer entradasCompra,
            Integer entradasTaller, String referencia, String perfil, String refCorta, String refAduana, String modelo, String refProv, String descripcion,
            String nomDpto, String codGrupo, String nomGrupo, String codSubGrupo, String nomSubGrupo, String codColores, String colores, String codMateriales,
            String materiales, String proveedor, String nombreWeb, BigDecimal rotacionPorcentaje, BigDecimal rotacionPromedioDias, BigDecimal rotacionPromedioDiasAvanz,
            BigDecimal utilidadPromedio, BigDecimal utilidadMinima, BigDecimal utilidadAvanzada, Date fechaUltimaVenta) {
        this.idRotacion = idRotacion;
        this.cantidadComprada = cantidadComprada;
        this.cantidadVendida = cantidadVendida;
        this.cantidadEntradaNeta = cantidadEntradaNeta;
        this.cantidadSalida = cantidadSalida;
        this.saldoVenta = saldoVenta;
        this.totalComprado = totalComprado;
        this.codDpto = codDpto;
        this.precioSinIva = precioSinIva;
        this.idInforme = idInforme;
        this.saldoTaller = saldoTaller;
        this.saldoGarantia = saldoGarantia;
        this.entradasCompra = entradasCompra;
        this.entradasTaller = entradasTaller;
        this.referencia = referencia;
        this.perfil = perfil;
        this.refCorta = refCorta;
        this.refAduana = refAduana;
        this.modelo = modelo;
        this.refProv = refProv;
        this.descripcion = descripcion;
        this.nomDpto = nomDpto;
        this.codGrupo = codGrupo;
        this.nomGrupo = nomGrupo;
        this.codSubGrupo = codSubGrupo;
        this.nomSubGrupo = nomSubGrupo;
        this.codColores = codColores;
        this.colores = colores;
        this.codMateriales = codMateriales;
        this.materiales = materiales;
        this.proveedor = proveedor;
        this.nombreWeb = nombreWeb;
        this.rotacionPorcentaje = rotacionPorcentaje;
        this.rotacionPromedioDias = rotacionPromedioDias;
        this.rotacionPromedioDiasAvanz = rotacionPromedioDiasAvanz;
        this.utilidadPromedio = utilidadPromedio;
        this.utilidadMinima = utilidadMinima;
        this.utilidadAvanzada = utilidadAvanzada;
        this.fechaUltimaVenta = fechaUltimaVenta;
    }

    public Integer getIdRotacion() {
        return idRotacion;
    }

    public void setIdRotacion(Integer idRotacion) {
        this.idRotacion = idRotacion;
    }

    public Integer getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(Integer cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public Integer getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(Integer cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public Integer getCantidadEntradaNeta() {
        return cantidadEntradaNeta;
    }

    public void setCantidadEntradaNeta(Integer cantidadEntradaNeta) {
        this.cantidadEntradaNeta = cantidadEntradaNeta;
    }

    public Integer getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(Integer cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }

    public Integer getSaldoVenta() {
        return saldoVenta;
    }

    public void setSaldoVenta(Integer saldoVenta) {
        this.saldoVenta = saldoVenta;
    }

    public Integer getTotalComprado() {
        return totalComprado;
    }

    public void setTotalComprado(Integer totalComprado) {
        this.totalComprado = totalComprado;
    }

    public Integer getCodDpto() {
        return codDpto;
    }

    public void setCodDpto(Integer codDpto) {
        this.codDpto = codDpto;
    }

    public Integer getPrecioSinIva() {
        return precioSinIva;
    }

    public void setPrecioSinIva(Integer precioSinIva) {
        this.precioSinIva = precioSinIva;
    }

    public Integer getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Integer idInforme) {
        this.idInforme = idInforme;
    }

    public Integer getSaldoTaller() {
        return saldoTaller;
    }

    public void setSaldoTaller(Integer saldoTaller) {
        this.saldoTaller = saldoTaller;
    }

    public Integer getSaldoGarantia() {
        return saldoGarantia;
    }

    public void setSaldoGarantia(Integer saldoGarantia) {
        this.saldoGarantia = saldoGarantia;
    }

    public Integer getEntradasCompra() {
        return entradasCompra;
    }

    public void setEntradasCompra(Integer entradasCompra) {
        this.entradasCompra = entradasCompra;
    }

    public Integer getEntradasTaller() {
        return entradasTaller;
    }

    public void setEntradasTaller(Integer entradasTaller) {
        this.entradasTaller = entradasTaller;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getRefCorta() {
        return refCorta;
    }

    public void setRefCorta(String refCorta) {
        this.refCorta = refCorta;
    }

    public String getRefAduana() {
        return refAduana;
    }

    public void setRefAduana(String refAduana) {
        this.refAduana = refAduana;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getRefProv() {
        return refProv;
    }

    public void setRefProv(String refProv) {
        this.refProv = refProv;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getCodSubGrupo() {
        return codSubGrupo;
    }

    public void setCodSubGrupo(String codSubGrupo) {
        this.codSubGrupo = codSubGrupo;
    }

    public String getNomSubGrupo() {
        return nomSubGrupo;
    }

    public void setNomSubGrupo(String nomSubGrupo) {
        this.nomSubGrupo = nomSubGrupo;
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

    public BigDecimal getRotacionPorcentaje() {
        return rotacionPorcentaje;
    }

    public void setRotacionPorcentaje(BigDecimal rotacionPorcentaje) {
        this.rotacionPorcentaje = rotacionPorcentaje;
    }

    public BigDecimal getRotacionPromedioDias() {
        return rotacionPromedioDias;
    }

    public void setRotacionPromedioDias(BigDecimal rotacionPromedioDias) {
        this.rotacionPromedioDias = rotacionPromedioDias;
    }

    public BigDecimal getRotacionPromedioDiasAvanz() {
        return rotacionPromedioDiasAvanz;
    }

    public void setRotacionPromedioDiasAvanz(BigDecimal rotacionPromedioDiasAvanz) {
        this.rotacionPromedioDiasAvanz = rotacionPromedioDiasAvanz;
    }

    public BigDecimal getUtilidadPromedio() {
        return utilidadPromedio;
    }

    public void setUtilidadPromedio(BigDecimal utilidadPromedio) {
        this.utilidadPromedio = utilidadPromedio;
    }

    public BigDecimal getUtilidadMinima() {
        return utilidadMinima;
    }

    public void setUtilidadMinima(BigDecimal utilidadMinima) {
        this.utilidadMinima = utilidadMinima;
    }

    public BigDecimal getUtilidadAvanzada() {
        return utilidadAvanzada;
    }

    public void setUtilidadAvanzada(BigDecimal utilidadAvanzada) {
        this.utilidadAvanzada = utilidadAvanzada;
    }

    public Date getFechaUltimaVenta() {
        return fechaUltimaVenta;
    }

    public void setFechaUltimaVenta(Date fechaUltimaVenta) {
        this.fechaUltimaVenta = fechaUltimaVenta;
    }
}
