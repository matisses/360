package co.matisses.web.mbean.rotacion;

import co.matisses.persistence.dwb.entity.InformeRotacion;
import co.matisses.persistence.dwb.entity.Rotacion;
import co.matisses.persistence.dwb.entity.RotacionImportacion;
import co.matisses.persistence.dwb.entity.VentasNetas;
import co.matisses.persistence.dwb.facade.InformeRotacionFacade;
import co.matisses.persistence.dwb.facade.RotacionFacade;
import co.matisses.persistence.dwb.facade.RotacionImportacionFacade;
import co.matisses.persistence.dwb.facade.VentasNetasFacade;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.web.dto.RotacionDTO;
import co.matisses.web.dto.RotacionImportacionDTO;
import co.matisses.web.dto.VentasNetasDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.poi.ExcelGeneratorRotacion;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "rotacionMBean")
public class RotacionMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    private static final Logger CONSOLE = Logger.getLogger(RotacionMBean.class.getSimpleName());
    private Integer idInforme;
    private String parametroBusqueda;
    private String referencia;
    private List<RotacionDTO> datosFull;
    private List<RotacionDTO> datos;
    private List<RotacionImportacionDTO> detalle;
    private List<VentasNetasDTO> ventas;
    private List<Object[]> saldos;
    private List<Object[]> diasAlmacen;
    private List<Object[]> movimientos;
    @EJB
    private ExcelGeneratorRotacion excelGeneratorRotacion;
    @EJB
    private RotacionFacade rotacionFacade;
    @EJB
    private RotacionImportacionFacade rotacionImportacionFacade;
    @EJB
    private VentasNetasFacade ventasNetasFacade;
    @EJB
    private InformeRotacionFacade informeRotacionFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;

    public RotacionMBean() {
        datosFull = new ArrayList<>();
        datos = new ArrayList<>();
        detalle = new ArrayList<>();
        ventas = new ArrayList<>();
        saldos = new ArrayList<>();
        diasAlmacen = new ArrayList<>();
        movimientos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public Integer getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Integer idInforme) {
        this.idInforme = idInforme;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<RotacionDTO> getDatos() {
        return datos;
    }

    public void setDatos(List<RotacionDTO> datos) {
        this.datos = datos;
    }

    public List<RotacionImportacionDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<RotacionImportacionDTO> detalle) {
        this.detalle = detalle;
    }

    public List<VentasNetasDTO> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentasNetasDTO> ventas) {
        this.ventas = ventas;
    }

    public List<Object[]> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<Object[]> saldos) {
        this.saldos = saldos;
    }

    public List<Object[]> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Object[]> movimientos) {
        this.movimientos = movimientos;
    }

    public Integer getTotalSaldo() {
        if (saldos != null && !saldos.isEmpty()) {
            int total = 0;

            for (Object[] o : saldos) {
                total += (Integer) o[2];
            }

            return total;
        }

        return 0;
    }

    public Integer getDiasAlmacen(String almacen) {
        if (diasAlmacen != null && !diasAlmacen.isEmpty()) {
            for (Object[] d : diasAlmacen) {
                if (((String) d[0]).equals(almacen)) {
                    return (Integer) d[1];
                }
            }
        }

        return 0;
    }

    public void obtenerDatosInforme() {
        datosFull = new ArrayList<>();
        datos = new ArrayList<>();
        Integer estado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estado"));
        idInforme = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idInforme"));

        if (idInforme != null && idInforme != 0 && estado != null && estado == 3) {
            List<Rotacion> rotation = rotacionFacade.obtenerInforme(idInforme);

            if (rotation != null && !rotation.isEmpty()) {
                for (Rotacion r : rotation) {
                    RotacionDTO dto = new RotacionDTO();

                    dto.setCantidadComprada(r.getCantidadComprada());
                    dto.setCantidadEntradaNeta(r.getCantidadEntradaNeta());
                    dto.setCantidadSalida(r.getCantidadSalida());
                    dto.setCantidadVendida(r.getCantidadVendida());
                    dto.setCodColores(r.getCodColores());
                    dto.setCodDpto(r.getCodDpto());
                    dto.setCodGrupo(r.getCodGrupo());
                    dto.setCodMateriales(r.getCodMateriales());
                    dto.setCodSubGrupo(r.getCodSubGrupo());
                    dto.setColores(r.getColores());
                    dto.setDescripcion(r.getDescripcion());
                    dto.setEntradasCompra(r.getEntradasCompra());
                    dto.setEntradasTaller(r.getEntradasTaller());
                    dto.setFechaUltimaVenta(r.getFechaUltimaVenta());
                    dto.setIdInforme(r.getIdInforme());
                    dto.setIdRotacion(r.getIdRotacion());
                    dto.setMateriales(r.getMateriales());
                    dto.setModelo(r.getModelo());
                    dto.setNomDpto(r.getNomDpto());
                    dto.setNomGrupo(r.getNomGrupo());
                    dto.setNomSubGrupo(r.getNomSubGrupo());
                    dto.setNombreWeb(r.getNombreWeb());
                    dto.setPerfil(r.getPerfil());
                    dto.setPrecioSinIva(r.getPrecioSinIva());
                    dto.setProveedor(r.getProveedor());
                    dto.setRefAduana(r.getRefAduana());
                    dto.setRefCorta(r.getRefCorta());
                    dto.setRefProv(r.getRefProv());
                    dto.setReferencia(r.getReferencia());
                    dto.setRotacionPorcentaje(r.getRotacionPorcentaje());
                    dto.setRotacionPromedioDias(r.getRotacionPromedioDias());
                    dto.setRotacionPromedioDiasAvanz(r.getRotacionPromedioDiasAvanz());
                    dto.setSaldoGarantia(r.getSaldoGarantia());
                    dto.setSaldoTaller(r.getSaldoTaller());
                    dto.setSaldoVenta(r.getSaldoVenta());
                    dto.setTotalComprado(r.getTotalComprado());
                    dto.setUtilidadAvanzada(r.getUtilidadAvanzada());
                    dto.setUtilidadMinima(r.getUtilidadMinima());
                    dto.setUtilidadPromedio(r.getUtilidadPromedio());

                    datosFull.add(dto);
                }

                datos = new ArrayList<>(datosFull);
            }
        } else {
            idInforme = null;
        }
    }

    public void mostrarImagenes() {
        referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");
    }

    public void mostrarDetalleRotacion() {
        detalle = new ArrayList<>();
        ventas = new ArrayList<>();
        String item = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");

        List<RotacionImportacion> detail = rotacionImportacionFacade.obtenerRotacionItem(item);

        if (detail != null && !detail.isEmpty()) {
            for (RotacionImportacion r : detail) {
                RotacionImportacionDTO dto = new RotacionImportacionDTO();

                dto.setCantidadBaja(r.getCantidadBaja());
                dto.setCantidadCompra(r.getCantidadCompra());
                dto.setCantidadVenta(r.getCantidadVenta());
                dto.setDiasStock(r.getDiasStock());
                dto.setFechaCierre(r.getFechaCierre());
                dto.setFechaCompra(r.getFechaCompra());
                dto.setFechaUltimaVenta(r.getFechaUltimaVenta());
                dto.setIdCompra(r.getRotacionImportacionPK().getIdCompra());
                dto.setNroSalidas(r.getNroSalidas());
                dto.setReferencia(r.getRotacionImportacionPK().getReferencia());
                dto.setSaldoPendiente(r.getSaldoPendiente());
                dto.setSumaUtilidades(r.getSumaUtilidades());
                dto.setTipo(r.getTipo());
                dto.setUtilidadMinima(r.getUtilidadMinima());
                dto.setUtilidadPromedio(r.getUtilidadPromedio());

                detalle.add(dto);
            }
        }

        List<VentasNetas> sales = ventasNetasFacade.obtenerVentasReferencia(item);

        if (sales != null && !sales.isEmpty()) {
            for (VentasNetas v : sales) {
                VentasNetasDTO dto = new VentasNetasDTO();

                dto.setCantidad(v.getCantidad());
                dto.setCodColores(v.getCodColores());
                dto.setCodDpto(v.getCodDpto());
                dto.setCodGrupo(v.getCodGrupo());
                dto.setCodMateriales(v.getCodMateriales());
                dto.setCodSubgrupo(v.getCodSubgrupo());
                dto.setColores(v.getColores());
                dto.setComentariosFac(v.getComentariosFac());
                dto.setCostoUnitario(v.getCostoUnitario());
                dto.setDayfac(v.getDayFac());
                dto.setDescripcion(v.getDescripcion());
                dto.setFactura(v.getFactura());
                dto.setFecha(v.getFecha());
                dto.setFuente(v.getFuente());
                dto.setIdVentasNetas(v.getIdVentasNetas());
                dto.setMateriales(v.getMateriales());
                dto.setModelo(v.getModelo());
                dto.setMonthfac(v.getMonthFac());
                dto.setNit(v.getNit());
                dto.setNomDpto(v.getNomDpto());
                dto.setNomGrupo(v.getNomGrupo());
                dto.setNomSubgrupo(v.getNomSubgrupo());
                dto.setNombreWeb(v.getNombreWeb());
                dto.setPorcentajeUtilidad(v.getPorcentajeUtilidad());
                dto.setRefCorta(v.getRefCorta());
                dto.setRefProv(v.getRefProv());
                dto.setReferencia(v.getReferencia());
                dto.setYearfac(v.getYearFac());

                ventas.add(dto);
            }
        }
    }

    public void mostrarSaldos() {
        saldos = new ArrayList<>();
        diasAlmacen = new ArrayList<>();
        String item = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");

        List<SaldoItemInventario> sald = saldoItemInventarioFacade.findWithParameters(item, "0", true);

        if (sald != null && !sald.isEmpty()) {
            for (SaldoItemInventario s : sald) {
                saldos.add(new Object[]{s.getSaldoItemInventarioPK().getWhsCode().getWhsCode(), s.getSaldoItemInventarioPK().getWhsCode().getUnombrextablet(), s.getOnHand().intValue()});
            }
        }

        diasAlmacen = saldoItemInventarioFacade.getDiasAlmacen(item);
    }

    public void mostrarMovimientos() {
        movimientos = new ArrayList<>();
        String item = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");
        String almacen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacen");

        movimientos = saldoItemInventarioFacade.getMovimientos(item, almacen);
    }

    public void filtrarInforme() {
        datos = new ArrayList<>();

        if (parametroBusqueda == null || parametroBusqueda.isEmpty()) {
            mostrarMensaje("Error", "Ingrese el valor con el que quiere buscar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese el valor con el que quiere buscar");
            datos = new ArrayList<>(datosFull);
            return;
        }

        for (RotacionDTO r : datosFull) {
            if (r.getReferencia() != null && r.getReferencia().equals(genericMBean.completarReferencia(parametroBusqueda))) {
                datos.add(r);
            } else if (r.getModelo() != null && r.getModelo().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                datos.add(r);
            } else if (r.getNomSubGrupo() != null && r.getNomSubGrupo().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                datos.add(r);
            } else if (r.getRefProv() != null && r.getRefProv().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                datos.add(r);
            } else if (r.getRefAduana() != null && r.getRefAduana().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                datos.add(r);
            } else if (r.getDescripcion() != null && r.getDescripcion().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                datos.add(r);
            } else if (r.getNomGrupo() != null && r.getNomGrupo().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                datos.add(r);
            } else if (r.getNomDpto() != null && r.getNomDpto().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                datos.add(r);
            }
        }
    }

    public StreamedContent getExcelDownload() {
        CONSOLE.log(Level.INFO, "Se inicia el proceso de descarga del informe de rotacion");
        InformeRotacion info = informeRotacionFacade.find(idInforme);

        if (info != null && info.getIdInforme() != null && info.getIdInforme() != 0) {
            String nombreReporte = info.getIdInforme() + "-" + info.getNombre().replace("*", ".");

            excelGeneratorRotacion = new ExcelGeneratorRotacion(applicationMBean, imagenProductoMBean, genericMBean, nombreReporte, agregarEncabezadoReporte(), agregarEncabezadoReportePI(),
                    agregarEncabezadoDetalle(), agregarEncabezadoVentasNetas(), agregarEncabezadoSaldoVenta(), agregarEncabezadoSaldos(), agregarDatosReporte(), agregarDatosReportePI(),
                    agregarDatosDetalle(), agregarDatosVentasNetas(), agregarDatosSaldoVenta(), agregarDatosSaldos());

            try {
                excelGeneratorRotacion.generar();

                InputStream stream = new ByteArrayInputStream(fileToBytes(new File(applicationMBean.obtenerValorPropiedad("url.local.reporte") + nombreReporte + ".xlsx"), nombreReporte));
                return new DefaultStreamedContent(stream, "application/vnd.ms-excel", nombreReporte + ".xlsx");
            } catch (Exception e) {
            }
        }

        return null;
    }

    private byte[] fileToBytes(File file, String nombreArchivo) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[(int) file.length()];

        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            CONSOLE.log(Level.SEVERE, "", ex);
        }

        byte[] bytes = bos.toByteArray();
        File someFile = new File(nombreArchivo + ".xlsx");

        try (FileOutputStream fos = new FileOutputStream(someFile)) {
            fos.write(bytes);
            fos.flush();
            fos.close();
        }

        return buf;
    }

    private List<String> agregarEncabezadoReportePI() {
        List<String> encabezado = new ArrayList<String>();

        encabezado.add("N°");
        encabezado.add("Imagen");
        encabezado.add("Ref. Corta");
        encabezado.add("Modelo");
        encabezado.add("Ref. Proveedor");
        encabezado.add("Materiales");
        encabezado.add("Cod. Colores");
        encabezado.add("Dimensions");
        encabezado.add("Packing Size");
        encabezado.add("Qty New Order");
        encabezado.add("Salida");
        encabezado.add("Entrada Neta");
        encabezado.add("Entrada Compra");
        encabezado.add("Entrada Taller");
        encabezado.add("Categoria");
        encabezado.add("Grupo");
        encabezado.add("Subgrupo");
        encabezado.add("Descripción");
        encabezado.add("Materiales");
        encabezado.add("Ultima Venta");
        encabezado.add("Comprado");
        encabezado.add("Vendido");
        encabezado.add("Rotación");
        encabezado.add("Total Comprado");
        encabezado.add("Días Rotación Promedio");
        encabezado.add("Días Rot. Prom. Avanz.");
        encabezado.add("Precio Sin Iva");
        encabezado.add("Saldo Venta");
        encabezado.add("Saldo Taller");
        encabezado.add("Saldo Garantia");
        encabezado.add("Utilidad Promedio");
        encabezado.add("Utilidad Mínima");
        encabezado.add("Utilidad Avanzada");
        encabezado.add("IMP. 1");
        encabezado.add("IMP. 2");
        encabezado.add("IMP. 3");
        encabezado.add("Transit");
        encabezado.add("New Order");

        return encabezado;
    }

    private List<List<Object>> agregarDatosReportePI() {
        List<List<Object>> valores = new ArrayList<List<Object>>();

        for (RotacionDTO rotation : datosFull) {
            List<Object> fila = new ArrayList<Object>();

            fila.add("");
            fila.add("");
            fila.add(rotation.getRefCorta());
            fila.add(rotation.getModelo());
            fila.add(rotation.getRefProv());
            fila.add("");
            fila.add("");
            fila.add("");
            fila.add(rotation.getCodColores());
            fila.add("");
            fila.add(rotation.getCantidadSalida());
            fila.add(rotation.getCantidadEntradaNeta());
            fila.add(rotation.getEntradasCompra());
            fila.add(rotation.getEntradasTaller());
            fila.add(rotation.getNomDpto());
            fila.add(rotation.getNomGrupo());
            fila.add(rotation.getNomSubGrupo());
            fila.add(rotation.getDescripcion());
            fila.add("");
            fila.add(rotation.getFechaUltimaVenta());
            fila.add(rotation.getCantidadComprada());
            fila.add(rotation.getCantidadVendida());
            fila.add(rotation.getRotacionPorcentaje());
            fila.add(rotation.getTotalComprado());
            fila.add(rotation.getRotacionPromedioDias());
            fila.add(rotation.getRotacionPromedioDiasAvanz());
            fila.add(rotation.getPrecioSinIva());
            fila.add(rotation.getSaldoVenta());
            fila.add(rotation.getSaldoTaller());
            fila.add(rotation.getSaldoGarantia());
            fila.add(rotation.getUtilidadPromedio());
            fila.add(rotation.getUtilidadMinima());
            fila.add(rotation.getUtilidadAvanzada());
            fila.add("");
            fila.add("");
            fila.add("");
            fila.add("");
            fila.add("");

            valores.add(fila);
        }

        return valores;
    }

    private List<String> agregarEncabezadoReporte() {
        List<String> encabezado = new ArrayList<String>();

        encabezado.add("Imagen");
        encabezado.add("Producto");
        encabezado.add("Proveedor");
        encabezado.add("Ref. Corta");
        encabezado.add("Ref. Proveedor");
        encabezado.add("Salidas");
        encabezado.add("Entrada Neta");
        encabezado.add("Entrada Compra");
        encabezado.add("Entrada Taller");
        encabezado.add("Cod. Categoria");
        encabezado.add("Categoria");
        encabezado.add("Cod. Grupo");
        encabezado.add("Grupo");
        encabezado.add("Cod. Subgrupo");
        encabezado.add("Subgrupo");
        encabezado.add("Descripción");
        encabezado.add("Cod. Colores");
        encabezado.add("Colores");
        encabezado.add("Cod. Materiales");
        encabezado.add("Materiales");
        encabezado.add("Modelo");
        encabezado.add("Nombre Web");
        encabezado.add("Ultima Venta");
        encabezado.add("Comprado");
        encabezado.add("Vendido");
        encabezado.add("Rotación");
        encabezado.add("Perfil");
        encabezado.add("Total Comprado");
        encabezado.add("Días Rotación Promedio");
        encabezado.add("Días Rot. Prom. Avanz.");
        encabezado.add("Precio Sin Iva");
        encabezado.add("Saldo Venta");
        encabezado.add("Saldo Taller");
        encabezado.add("Saldo Garantia");
        encabezado.add("Utilidad Promedio");
        encabezado.add("Utilidad Mínima");
        encabezado.add("Utilidad Avanzada");

        return encabezado;
    }

    private List<List<Object>> agregarDatosReporte() {
        List<List<Object>> valoresRotacion = new ArrayList<List<Object>>();

        for (RotacionDTO p : datosFull) {
            List<Object> fila = new ArrayList<Object>();

            fila.add("");
            fila.add(p.getReferencia());
            fila.add(p.getProveedor());
            fila.add(p.getRefCorta());
            fila.add(p.getRefProv());
            fila.add(p.getCantidadSalida());
            fila.add(p.getCantidadEntradaNeta());
            fila.add(p.getEntradasCompra());
            fila.add(p.getEntradasTaller());
            fila.add(p.getCodDpto());
            fila.add(p.getNomDpto());
            fila.add(p.getCodGrupo());
            fila.add(p.getNomGrupo());
            fila.add(p.getCodSubGrupo());
            fila.add(p.getNomSubGrupo());
            fila.add(p.getDescripcion());
            fila.add(p.getCodColores());
            fila.add(p.getColores());
            fila.add(p.getCodMateriales());
            fila.add(p.getMateriales());
            fila.add(p.getModelo());
            fila.add(p.getNombreWeb());
            fila.add(p.getFechaUltimaVenta());
            fila.add(p.getCantidadComprada());
            fila.add(p.getCantidadVendida());
            fila.add(p.getRotacionPorcentaje());
            fila.add(p.getPerfil());
            fila.add(p.getTotalComprado());
            fila.add(p.getRotacionPromedioDias());
            fila.add(p.getRotacionPromedioDiasAvanz());
            fila.add(p.getPrecioSinIva());
            fila.add(p.getSaldoVenta());
            fila.add(p.getSaldoTaller());
            fila.add(p.getSaldoGarantia());
            fila.add(p.getUtilidadPromedio());
            fila.add(p.getUtilidadMinima());
            fila.add(p.getUtilidadAvanzada());

            valoresRotacion.add(fila);
        }

        return valoresRotacion;
    }

    private List<String> agregarEncabezadoDetalle() {
        List<String> encabezado = new ArrayList<String>();

        encabezado.add("Referencia");
        encabezado.add("Fecha Compra");
        encabezado.add("Fecha Cierre");
        encabezado.add("Cantidad Comprada");
        encabezado.add("Cantidad Vendida");
        encabezado.add("Cantidad baja");
        encabezado.add("Saldo Pendiente");
        encabezado.add("Días Stock");
        encabezado.add("Fecha Última Venta");
        encabezado.add("Utilidad Promedio");
        encabezado.add("Utilidad Minima");
        encabezado.add("Tipo");

        return encabezado;
    }

    private List<List<Object>> agregarDatosDetalle() {
        List<List<Object>> valores = new ArrayList<List<Object>>();

        for (RotacionDTO r : datosFull) {
            List<RotacionImportacion> detRot = rotacionImportacionFacade.obtenerRotacionItem(r.getReferencia());

            for (RotacionImportacion ri : detRot) {
                List<Object> fila = new ArrayList<Object>();

                fila.add(r.getReferencia());
                fila.add(ri.getFechaCompra());
                fila.add(ri.getFechaCierre());
                fila.add(ri.getCantidadCompra());
                fila.add(ri.getCantidadVenta());
                fila.add(ri.getCantidadBaja());
                fila.add(ri.getSaldoPendiente());
                fila.add(ri.getDiasStock());
                fila.add(ri.getFechaUltimaVenta());
                fila.add(ri.getUtilidadPromedio());
                fila.add(ri.getUtilidadMinima());
                fila.add(ri.getTipo());

                valores.add(fila);
            }
        }

        return valores;
    }

    private List<String> agregarEncabezadoVentasNetas() {
        List<String> encabezado = new ArrayList<String>();

        encabezado.add("Fuente");
        encabezado.add("Factura");
        encabezado.add("Fecha");
        encabezado.add("Referencia");
        encabezado.add("Descripción");
        encabezado.add("Cantidad");
        encabezado.add("Precio (U) sin Iva");
        encabezado.add("Costo (U)");
        encabezado.add("% Utilidad");

        return encabezado;
    }

    private List<List<Object>> agregarDatosVentasNetas() {
        List<List<Object>> valores = new ArrayList<List<Object>>();

        for (RotacionDTO r : datosFull) {
            List<VentasNetas> vn = ventasNetasFacade.obtenerVentasReferencia(r.getReferencia());

            for (VentasNetas vN : vn) {
                List<Object> fila = new ArrayList<Object>();

                fila.add(vN.getFuente());
                fila.add(vN.getFactura());
                fila.add(vN.getFecha());
                fila.add(vN.getReferencia());
                fila.add(vN.getDescripcion());
                fila.add(vN.getCantidad());
                fila.add(vN.getPrecioSinIva());
                fila.add(vN.getCostoUnitario());
                fila.add(vN.getPorcentajeUtilidad());

                valores.add(fila);
            }
        }

        return valores;
    }

    private List<String> agregarEncabezadoSaldoVenta() {
        List<String> encabezado = new ArrayList<String>();

        encabezado.add("Referencia");
        encabezado.add("Almacén");
        encabezado.add("Nombre");
        encabezado.add("Cantidad");
        encabezado.add("Días Almacén");
        encabezado.add("Total");

        return encabezado;
    }

    private List<List<Object>> agregarDatosSaldoVenta() {
        List<List<Object>> valores = new ArrayList<List<Object>>();

        for (RotacionDTO tmp : datosFull) {
            List<SaldoItemInventario> sald = saldoItemInventarioFacade.findWithParameters(tmp.getReferencia(), "0", true);

            for (SaldoItemInventario p : sald) {
                List<Object> fila = new ArrayList<Object>();

                fila.add(tmp.getReferencia());
                fila.add(p.getSaldoItemInventarioPK().getWhsCode().getWhsCode());
                fila.add(p.getSaldoItemInventarioPK().getWhsCode().getWhsName());
                fila.add(p.getOnHandAsInt());
                fila.add(diasAlmacen(p.getSaldoItemInventarioPK().getWhsCode().getWhsCode(), tmp.getReferencia()));

                Integer saldo = 0;
                for (SaldoItemInventario s : sald) {
                    saldo += s.getOnHand().intValue();
                }

                fila.add(saldo);

                valores.add(fila);
            }
        }

        return valores;
    }

    private List<String> agregarEncabezadoSaldos() {
        List<String> encabezado = new ArrayList<String>();

        encabezado.add("Número Traslado");
        encabezado.add("Fecha");
        encabezado.add("Comentario");
        encabezado.add("Origen");
        encabezado.add("Destino");
        encabezado.add("Cantidad");

        return encabezado;
    }

    private List<List<Object>> agregarDatosSaldos() {
        List<List<Object>> valores = new ArrayList<List<Object>>();
//        
//        for(RotacionDTO r : datosFull){
//            List<Object[]> movimientos = saldoItemInventarioFacade.getMovimientos(r.getReferencia(), parametroBusqueda)
//        }
        return valores;
    }

    public Integer diasAlmacen(String almacen, String referencia) {
        List<Object[]> dias = saldoItemInventarioFacade.getDiasAlmacen(referencia);

        if (dias != null && !dias.isEmpty()) {
            for (Object[] o : dias) {
                if (((String) o[0]).equals(almacen)) {
                    return (Integer) o[1];
                }
            }
        }

        return -1;
    }

    public void limpiar() {
        idInforme = null;
        parametroBusqueda = null;
        referencia = null;
        datosFull = new ArrayList<>();
        datos = new ArrayList<>();
        detalle = new ArrayList<>();
        ventas = new ArrayList<>();
        saldos = new ArrayList<>();
        diasAlmacen = new ArrayList<>();
        movimientos = new ArrayList<>();
    }

    private void mostrarMensaje(String resumen, String mensaje, boolean error, boolean informacion, boolean advertencia) {
        FacesMessage msg = null;
        if (error) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, mensaje);
        } else if (advertencia) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resumen, mensaje);
        } else if (informacion) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, mensaje);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
