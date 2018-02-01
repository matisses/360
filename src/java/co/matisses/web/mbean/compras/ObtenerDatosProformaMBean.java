package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.ColumnaProforma;
import co.matisses.persistence.web.entity.ConfiguracionProforma;
import co.matisses.persistence.web.entity.DatosProveedor;
import co.matisses.persistence.web.entity.DetalleProforma;
import co.matisses.persistence.web.entity.ProformaInvoice;
import co.matisses.persistence.web.facade.ColumnaProformaFacade;
import co.matisses.persistence.web.facade.ConfiguracionProformaFacade;
import co.matisses.persistence.web.facade.DatosProveedorFacade;
import co.matisses.persistence.web.facade.DetalleProformaFacade;
import co.matisses.persistence.web.facade.ProformaInvoiceFacade;
import co.matisses.web.dto.ColumnaProformaDTO;
import co.matisses.web.dto.DatosProveedorDTO;
import co.matisses.web.dto.TipoDatosDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.poi.ExcelReaderProforma;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import javax.servlet.http.Part;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "obtenerDatosProformaMBean")
public class ObtenerDatosProformaMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(ObtenerDatosProformaMBean.class.getSimpleName());
    private int registros = 0;
    private Integer columnaAsociada;
    private Integer posicionSeleccionada;
    private String rutaTmp;
    private String rutaImagen;
    private boolean dlgNuevaCol = false;
    private boolean botonesOrdenarDeshabilitados = true;
    private boolean crearColumna = false;
    private boolean elegirColumna = true;
    private boolean columanExiste = false;
    private boolean obligatoria = false;
    private Part archivo;
    private DatosProveedorDTO proveedor;
    private ColumnasProformaMBean columnasProformaMBean;
    private List<Object[]> objects;
    private List<ColumnaProformaDTO> columnasSeleccionadas;
    private List<ColumnaProformaDTO> todasColumnas;
    @EJB
    private DatosProveedorFacade proveedorFacade;
    @EJB
    private ColumnaProformaFacade columnaProformaFacade;
    @EJB
    private ConfiguracionProformaFacade configuracionProformaFacade;
    @EJB
    private ProformaInvoiceFacade proformaInvoiceFacade;
    @EJB
    private DetalleProformaFacade detalleProformaFacade;

    public ObtenerDatosProformaMBean() {
        columnasSeleccionadas = new ArrayList<>();
        todasColumnas = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        rutaTmp = applicationMBean.obtenerValorPropiedad("url.local.wildfly.proveedores");
        rutaImagen = applicationMBean.obtenerValorPropiedad("baru.fotos.proveedores");
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public ColumnasProformaMBean getColumnasProformaMBean() {
        return columnasProformaMBean;
    }

    public void setColumnasProformaMBean(ColumnasProformaMBean columnasProformaMBean) {
        this.columnasProformaMBean = columnasProformaMBean;
    }

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

    public DatosProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(DatosProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    public List<ColumnaProformaDTO> getColumnasSeleccionadas() {
        return columnasSeleccionadas;
    }

    public void setColumnasSeleccionadas(List<ColumnaProformaDTO> columnasSeleccionadas) {
        this.columnasSeleccionadas = columnasSeleccionadas;
    }

    public String getRutaTmp() {
        return rutaTmp;
    }

    public void setRutaTmp(String rutaTmp) {
        this.rutaTmp = rutaTmp;
    }

    public List<Object[]> getObjects() {
        return objects;
    }

    public void setObjects(List<Object[]> objects) {
        this.objects = objects;
    }

    public List<ColumnaProformaDTO> getTodasColumnas() {
        return todasColumnas;
    }

    public void setTodasColumnas(List<ColumnaProformaDTO> todasColumnas) {
        this.todasColumnas = todasColumnas;
    }

    public boolean isDlgNuevaCol() {
        return dlgNuevaCol;
    }

    public void setDlgNuevaCol(boolean dlgNuevaCol) {
        this.dlgNuevaCol = dlgNuevaCol;
    }

    public Integer getColumnaAsociada() {
        return columnaAsociada;
    }

    public void setColumnaAsociada(Integer columnaAsociada) {
        this.columnaAsociada = columnaAsociada;
    }

    public Integer getPosicionSeleccionada() {
        return posicionSeleccionada;
    }

    public void setPosicionSeleccionada(Integer posicionSeleccionada) {
        this.posicionSeleccionada = posicionSeleccionada;
    }

    public boolean isBotonesOrdenarDeshabilitados() {
        return botonesOrdenarDeshabilitados;
    }

    public void setBotonesOrdenarDeshabilitados(boolean botonesOrdenarDeshabilitados) {
        this.botonesOrdenarDeshabilitados = botonesOrdenarDeshabilitados;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public boolean isElegirColumna() {
        return elegirColumna;
    }

    public void setElegirColumna(boolean elegirColumna) {
        this.elegirColumna = elegirColumna;
    }

    public boolean isCrearColumna() {
        return crearColumna;
    }

    public void setCrearColumna(boolean crearColumna) {
        this.crearColumna = crearColumna;
    }

    public boolean isColumanExiste() {
        return columanExiste;
    }

    public void setColumanExiste(boolean columanExiste) {
        this.columanExiste = columanExiste;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    private void cargarColumnas() {
        todasColumnas = new ArrayList<>();
        for (ColumnaProforma entidad : columnaProformaFacade.findAll()) {
            todasColumnas.add(0, new ColumnaProformaDTO(0, entidad.getIdColumna(),
                    entidad.getDecimalesVisibles(), entidad.getIdOperacionColumna() == null ? null : entidad.getIdOperacionColumna().getIdOperacionColumnaProforma(),
                    entidad.getNombre1(), entidad.getNombre1Ingles(), entidad.getNombre2(), entidad.getNombre2Ingles(), entidad.getLongitudOcupadaTabla(), entidad.getTipoCantidad(),
                    entidad.getPermitirCrearItem(), entidad.getRequiereOperacion(), entidad.getTipoItem(), entidad.getTipoIdentificador(), entidad.getTipoImagen(),
                    entidad.getObligatoria(), entidad.getIncluirProforma(), entidad.getModificable(), entidad.getModificableSiNuevo(), entidad.getTipoValorTotal(),
                    entidad.getTipoCBM(), entidad.getTipoValorUnitario(), entidad.getTipoDescuento(),
                    entidad.getIdTipoDato() != null
                            ? new TipoDatosDTO(entidad.getIdTipoDato().getIdTipoDato(), entidad.getIdTipoDato().getTipoDato()) : null,
                    null));
            todasColumnas.get(0).setTipoValorUnitario(entidad.getTipoValorUnitario());
        }

        Collections.sort(todasColumnas);
        for (ColumnaProformaDTO dto : columnasSeleccionadas) {
            for (int i = 0; i < todasColumnas.size(); i++) {
                ColumnaProformaDTO c = todasColumnas.get(i);
                if (c.getIdColumna().equals(dto.getIdColumna()) && dto.isColumnaEnUso()) {
                    todasColumnas.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    public void proveedorSeleccionado() {
        if (proveedor == null) {
            return;
        }
        //cargar columnas configuradas
        columnasSeleccionadas = new ArrayList<>();
        List<ConfiguracionProforma> entidades = configuracionProformaFacade.buscarPorCodigoProveedor(proveedor.getCodProveedor(), null);
        for (ConfiguracionProforma entidad : entidades) {
            columnasSeleccionadas.add(new ColumnaProformaDTO(0, entidad.getIdColumna().getIdColumna(),
                    entidad.getIdColumna().getDecimalesVisibles(),
                    entidad.getIdColumna().getIdOperacionColumna() == null ? null : entidad.getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma(),
                    entidad.getIdColumna().getNombre1(), entidad.getIdColumna().getNombre1Ingles(), entidad.getIdColumna().getNombre2(),
                    entidad.getIdColumna().getNombre2Ingles(), entidad.getIdColumna().getLongitudOcupadaTabla(), entidad.getIdColumna().getTipoCantidad(),
                    entidad.getIdColumna().getPermitirCrearItem(), entidad.getIdColumna().getRequiereOperacion(), entidad.getIdColumna().getTipoItem(),
                    entidad.getIdColumna().getTipoIdentificador(), entidad.getIdColumna().getTipoImagen(),
                    entidad.getIdColumna().getObligatoria(), entidad.getIdColumna().getIncluirProforma(), entidad.getIdColumna().getModificable(),
                    entidad.getIdColumna().getModificableSiNuevo(), entidad.getIdColumna().getTipoValorTotal(),
                    entidad.getIdColumna().getTipoCBM(), entidad.getIdColumna().getTipoValorUnitario(), entidad.getIdColumna().getTipoDescuento(),
                    entidad.getIdColumna().getIdTipoDato() != null
                            ? new TipoDatosDTO(entidad.getIdColumna().getIdTipoDato().getIdTipoDato(), entidad.getIdColumna().getIdTipoDato().getTipoDato()) : null,
                    null));
        }
    }

    public String getFileName(Part part) {
        if (part != null) {
            for (String cd : part.getHeader("content-disposition").split(";")) {
                if (cd.trim().startsWith("filename")) {
                    String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                    return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
                }
            }
        }
        return "";
    }

    public void handleFileUpload() throws IOException {
        try {
            limpiar();
            if (archivo != null) {
                log.log(Level.INFO, "Recibiendo archivo: {0}", new Object[]{getFileName(archivo)});
                log.log(Level.INFO, "Tipo contenido: {0}", new Object[]{archivo.getContentType()});
                if (archivo.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                        || archivo.getContentType().equals("application/vnd.ms-excel")) {
                    recibirDatosExcel(archivo.getInputStream(), getFileName(archivo));
                    archivo = null;
                } else {
                    mostrarMensaje("Error", "El tipo de archivo que está intentando subir no es válido. Formatos validos XLSX, XLS.", true, false, false);
                    log.log(Level.SEVERE, "El tipo de archivo que esta intentando subir no es valido. Formatos validos XLSX, XLS");
                    return;
                }
            } else {
                mostrarMensaje("Error", "No ha seleccionado un archivo para subir.", true, false, false);
                log.log(Level.SEVERE, "No ha seleccionado un archivo para subir");
                return;
            }
        } catch (Exception e) {
            mostrarMensaje("Error", "Se ha producido un error al subir el archivo, verifique la extensión de este.", true, false, false);
            log.log(Level.SEVERE, "Se ha producido un error al subir el archivo, verifique la extención de este. Error [{0}]", e.getMessage());
            return;
        }
    }

    private void limpiar() {
        rutaImagen = applicationMBean.obtenerValorPropiedad("baru.fotos.proveedores");
        proveedor = null;
        objects = new ArrayList<>();
        columnasSeleccionadas = new ArrayList<>();
        todasColumnas = new ArrayList<>();
    }

    private void recibirDatosExcel(InputStream is, String nombre) {
        ExcelReaderProforma excel = new ExcelReaderProforma();
        excel.setInputStream(is);
        excel.setNombre(nombre);
        objects = excel.interpretarExcelRecibido();
        interpretarDatosRecibidos();

        //Obtener columnas obligatorias
        List<ColumnaProforma> columns = columnaProformaFacade.obtenerColumnasObligatorias();

        if (columns != null && !columns.isEmpty()) {
            for (ColumnaProforma c : columns) {
                ColumnaProformaDTO dto = new ColumnaProformaDTO();

                dto.setIdColumna(c.getIdColumna());
                dto.setNombre1Ingles(c.getNombre1Ingles());
                dto.setNombre2Ingles(c.getNombre2Ingles());
                dto.setPosicion(columnasSeleccionadas.size() + 1);
                dto.setColumnaExiste(true);
                dto.setColumnaEnUso(false);
                dto.setObligatoria(true);
                dto.setTipoCBM(c.getTipoCBM());
                dto.setTipoCantidad(c.getTipoCantidad());
                dto.setTipoIdentificador(c.getTipoIdentificador());
                dto.setTipoImagen(c.getTipoImagen());
                dto.setTipoItem(c.getTipoItem());
                dto.setTipoValorTotal(c.getTipoValorTotal());
                dto.setTipoValorUnitario(c.getTipoValorUnitario());

                columnasSeleccionadas.add(dto);
            }
        }
    }

    private void interpretarDatosRecibidos() {
        if (objects != null && !objects.isEmpty()) {
            for (int i = 0; i < objects.size(); i++) {
                Object[] object = objects.get(i);
                try {
                    if (i == 1 || i >= 8) {
                        String columna = "";
                        for (int a = 0; a < object.length; a++) {
                            if (i == 1 && a == 2) {
                                String razonSocialProveedor = (String) object[a];
                                if (razonSocialProveedor != null && !razonSocialProveedor.isEmpty()) {
                                    DatosProveedor pro = proveedorFacade.proveedor(razonSocialProveedor);
                                    if (pro != null) {
                                        asignarDatosProveedor(pro);
                                    }
                                    break;
                                }
                            } else if (i == 8) {
                                boolean existe;
                                if (object[a] != null) {
                                    columna = (object[a] instanceof java.lang.String) ? (String) object[a] : (object[a] instanceof java.lang.Integer) ? ((Integer) object[a]).toString()
                                            : (object[a] instanceof java.lang.Double) ? ((Double) object[a]).toString() : (String) object[a];

                                    Object[] subcolumnas = objects.get(9);
                                    if (subcolumnas.length - 1 < a) {
                                        existe = configuracionProformaFacade.columnaProgramada(columna, "", proveedor.getCodProveedor(), null);
                                    } else {
                                        existe = configuracionProformaFacade.columnaProgramada(columna,
                                                subcolumnas[a] == null ? "" : (subcolumnas[a] instanceof java.lang.String) ? (String) subcolumnas[a] : (subcolumnas[a] instanceof java.lang.Integer)
                                                                        ? ((Integer) subcolumnas[a]).toString()
                                                                        : (subcolumnas[a] instanceof java.lang.Double) ? ((Double) subcolumnas[a]).toString() : (String) subcolumnas[a],
                                                proveedor.getCodProveedor(), null);
                                    }
                                    if (existe) {
                                        for (ColumnaProformaDTO dto : columnasSeleccionadas) {
                                            if (dto.getNombre1Ingles().equals((String) columna)
                                                    && dto.getNombre2Ingles().equals(a > subcolumnas.length - 1 ? ""
                                                            : subcolumnas[a] == null ? "" : (subcolumnas[a] instanceof java.lang.String) ? (String) subcolumnas[a] : (subcolumnas[a] instanceof java.lang.Integer)
                                                                                    ? ((Integer) subcolumnas[a]).toString()
                                                                                    : (subcolumnas[a] instanceof java.lang.Double) ? ((Double) subcolumnas[a]).toString() : (String) subcolumnas[a])) {
                                                dto.setPosicion(a + 1);
                                                dto.setColumnaEnUso(true);
                                                dto.setColumnaExiste(true);
                                                break;
                                            }
                                        }
                                    } else {
                                        columnasSeleccionadas.add(asignarDatosColumanaProforma(a, object, subcolumnas));
                                    }
                                } else {
                                    columna = (object[a] instanceof java.lang.String) ? (String) object[a] : (object[a] instanceof java.lang.Integer) ? ((Integer) object[a]).toString()
                                            : (object[a] instanceof java.lang.Double) ? ((Double) object[a]).toString() : (String) object[a];

                                    Object[] object2 = objects.get(9);
                                    if (object2.length - 1 < a) {
                                        existe = configuracionProformaFacade.columnaProgramada(columna, "", proveedor.getCodProveedor(), null);
                                    } else {
                                        existe = configuracionProformaFacade.columnaProgramada(columna,
                                                object2[a] == null ? "" : (object2[a] instanceof java.lang.String) ? (String) object2[a] : (object2[a] instanceof java.lang.Integer)
                                                                        ? ((Integer) object2[a]).toString()
                                                                        : (object2[a] instanceof java.lang.Double) ? ((Double) object2[a]).toString() : (String) object2[a],
                                                proveedor.getCodProveedor(), null);
                                    }
                                    if (existe) {
                                        for (ColumnaProformaDTO dto : columnasSeleccionadas) {
                                            if (dto.getNombre1Ingles().equals((String) columna)
                                                    && dto.getNombre2Ingles().equals(a > object2.length - 1 ? ""
                                                            : object2[a] == null ? "" : (object2[a] instanceof java.lang.String) ? (String) object2[a] : (object2[a] instanceof java.lang.Integer)
                                                                                    ? ((Integer) object2[a]).toString()
                                                                                    : (object2[a] instanceof java.lang.Double) ? ((Double) object2[a]).toString() : (String) object2[a])) {
                                                dto.setPosicion(a + 1);
                                                dto.setColumnaEnUso(true);
                                                dto.setColumnaExiste(true);
                                                break;
                                            }
                                        }
                                    } else {
                                        columnasSeleccionadas.add(asignarDatosColumanaProforma(a, object, object2));
                                    }
                                }
                            } else if (i > 9) {
                                for (ColumnaProformaDTO dto : columnasSeleccionadas) {
                                    if (dto.getPosicion() - 1 == a) {
                                        if (dto.getIdTipoDato() != null && dto.getIdTipoDato().equals(1)) {
                                            dto.getValores().add(String.valueOf((Byte) object[a]));
                                        } else if (dto.getIdTipoDato() != null && dto.getIdTipoDato().equals(2)) {
                                            dto.getValores().add(String.valueOf((Short) object[a]));
                                        } else if (dto.getIdTipoDato() != null && dto.getIdTipoDato().equals(3)) {
                                            dto.getValores().add(String.valueOf((Integer) object[a]));
                                        } else if (dto.getIdTipoDato() != null && dto.getIdTipoDato().equals(4)) {
                                            dto.getValores().add(String.valueOf((Long) object[a]));
                                        } else if (dto.getIdTipoDato() != null && dto.getIdTipoDato().equals(5)) {
                                            dto.getValores().add(String.valueOf((Float) object[a]));
                                        } else if (dto.getIdTipoDato() != null && dto.getIdTipoDato().equals(6)) {
                                            dto.getValores().add(String.valueOf((Double) object[a]));
                                        } else if (dto.getIdTipoDato() != null && dto.getIdTipoDato().equals(7)) {
                                            dto.getValores().add(String.valueOf((Character) object[a]));
                                        } else if (dto.getIdTipoDato() != null && dto.getIdTipoDato().equals(8)) {
                                            dto.getValores().add(String.valueOf((Boolean) object[a]));
                                        } else {
                                            dto.getValores().add((String) String.valueOf(object[a]));
                                        }
                                        break;
                                    }
                                }
                            } else if (a > 2) {
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                    return;
                }
            }
        }
        Collections.sort(columnasSeleccionadas, new Comparator<ColumnaProformaDTO>() {
            @Override
            public int compare(ColumnaProformaDTO o1, ColumnaProformaDTO o2) {
                return o1.getPosicion() - o2.getPosicion();
            }
        });
    }

    private ColumnaProformaDTO asignarDatosColumanaProforma(Integer a, Object[] object, Object[] object2) {
        ColumnaProformaDTO dto = new ColumnaProformaDTO();
        dto.setNombre1Ingles((object[a] instanceof java.lang.String) ? (String) object[a] : (object[a] instanceof java.lang.Integer)
                ? ((Integer) object[a]).toString()
                : (object[a] instanceof java.lang.Double) ? ((Double) object[a]).toString() : (String) object[a]);
        dto.setNombre2Ingles(a > object2.length - 1 ? "" : (String) object2[a] == null ? ""
                : (object2[a] instanceof java.lang.String) ? (String) object2[a] : (object2[a] instanceof java.lang.Integer)
                                ? ((Integer) object2[a]).toString()
                                : (object2[a] instanceof java.lang.Double) ? ((Double) object2[a]).toString() : (String) object2[a]);
        dto.setPosicion(a + 1);
        dto.setColumnaExiste(false);
        dto.setColumnaEnUso(true);
        return dto;
    }

    private void asignarDatosProveedor(DatosProveedor pro) {
        proveedor = new DatosProveedorDTO();
        proveedor.setCodProveedor(pro.getCodProveedor());
        proveedor.setCorreo(pro.getCorreo());
        proveedor.setDireccion(pro.getDireccion());
        proveedor.setEstado(pro.getEstado().getIdEstado());
        proveedor.setLogo(pro.getLogo());
        proveedor.setPersonaContacto(pro.getPersonaContacto());
        proveedor.setRazonSocial(pro.getRazonSocial());
        proveedor.setTelefono(pro.getTelefono());
        obtenerRutaImagen(proveedor.getLogo());
        cargarColumnas();
        proveedorSeleccionado();
    }

    private void obtenerRutaImagen(String img) {
        File f = new File(rutaTmp + File.separator + img);
        if (f.exists()) {
            try {
                rutaImagen += img;
            } catch (Exception e) {
                log.log(Level.SEVERE, "No se encontro la imagen", e);
                rutaImagen = applicationMBean.obtenerValorPropiedad("url.web.noimage");
            }
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen");
            rutaImagen = applicationMBean.obtenerValorPropiedad("url.web.noimage");
        }
    }

    public void agregarColumna() {
        for (ColumnaProformaDTO col : columnasSeleccionadas) {
            if (col.getPosicion() == posicionSeleccionada) {
                dlgNuevaCol = true;
                break;
            }
        }
    }

    public void seleccionarCrearColumna() {
        if (crearColumna) {
            crearColumna = false;
            elegirColumna = true;
        } else {
            crearColumna = true;
            elegirColumna = false;
        }
    }

    public void seleccionarElegirColumna() {
        if (elegirColumna) {
            crearColumna = true;
            elegirColumna = false;
        } else {
            crearColumna = false;
            elegirColumna = true;
        }
    }

    public void administrarColumna() {
        Integer idColumna = null;
        if (crearColumna) {
            idColumna = columnasProformaMBean.guardarDatosColumna();
            if (idColumna == null) {
                mostrarMensaje("Error", "No se pudo crear la columna, verifique los datos suministrados.", true, false, false);
                log.log(Level.SEVERE, "No se pudo crear la columna, verifique los datos suministrados");
                return;
            }
            cargarColumnas();
        } else {
            idColumna = columnaAsociada;
        }

        boolean existe = false;
        for (int a = 0; a < columnasSeleccionadas.size(); a++) {
            ColumnaProformaDTO dto = columnasSeleccionadas.get(a);
            if (dto.getIdColumna() != null && dto.getIdColumna().equals(idColumna) && !dto.isColumnaEnUso()) {
                existe = true;
                for (int j = 0; j < columnasSeleccionadas.size(); j++) {
                    if (columnasSeleccionadas.get(j).getIdColumna() != null
                            && columnasSeleccionadas.get(j).getIdColumna().equals(dto.getIdColumna()) && posicionSeleccionada != columnasSeleccionadas.get(j).getPosicion()) {
                        columnasSeleccionadas.remove(j);
                        j--;
                    }
                }
                dto.setPosicion(posicionSeleccionada);
                dto.setColumnaEnUso(true);
                dto.setColumnaExiste(true);
                if (dto.getValores() == null) {
                    dto.setValores(new ArrayList<String>());
                }
                for (int i = 0; i < columnasSeleccionadas.size(); i++) {
                    ColumnaProformaDTO c = columnasSeleccionadas.get(i);
                    if (c.getPosicion() == posicionSeleccionada) {
                        dto.setValores(c.getValores());
                        columnasSeleccionadas.remove(i);
                        i--;
                        break;
                    }
                }
                columnasSeleccionadas.add(dto);
                dlgNuevaCol = false;
                break;
            } else if (dto.getIdColumna() != null && dto.getIdColumna().equals(idColumna) && dto.isColumnaEnUso()) {
                mostrarMensaje("Error", "La columna ya fue asignada.", true, false, false);
                log.log(Level.SEVERE, "La columna ya fue asignada");
                posicionSeleccionada = null;
                columanExiste = false;
                obligatoria = false;
                return;
            } else {
                existe = false;
            }
        }
        if (!existe) {
            for (ColumnaProformaDTO dto : todasColumnas) {
                if (dto.getIdColumna().equals(idColumna)) {
                    dto.setPosicion(posicionSeleccionada);
                    dto.setIdTipoDato(dto.getIdTipoDato());
                    dto.setColumnaEnUso(true);
                    dto.setColumnaExiste(true);
                    if (dto.getValores() == null) {
                        dto.setValores(new ArrayList<String>());
                    }
                    for (int i = 0; i < columnasSeleccionadas.size(); i++) {
                        ColumnaProformaDTO c = columnasSeleccionadas.get(i);
                        if (c.getPosicion() == posicionSeleccionada) {
                            dto.setValores(c.getValores());
                            columnasSeleccionadas.remove(i);
                            i--;
                            break;
                        }
                    }
                    columnasSeleccionadas.add(dto);
                    dlgNuevaCol = false;
                }
            }
        }

        Collections.sort(columnasSeleccionadas, new Comparator<ColumnaProformaDTO>() {
            @Override
            public int compare(ColumnaProformaDTO o1, ColumnaProformaDTO o2) {
                return o1.getPosicion() - o2.getPosicion();
            }
        });
        cancelar();
    }

    public void seleccionarColumna() {
        Integer ultimaSeleccionada = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("posicion"));
        if (posicionSeleccionada != null && posicionSeleccionada != 0 && ultimaSeleccionada.equals(posicionSeleccionada) && !dlgNuevaCol) {
            posicionSeleccionada = null;
            botonesOrdenarDeshabilitados = true;
            columanExiste = false;
            obligatoria = false;
        } else {
            posicionSeleccionada = ultimaSeleccionada;
            log.log(Level.INFO, "Se selecciono la posicion #{0} de las columnas", posicionSeleccionada);
            botonesOrdenarDeshabilitados = false;
            columanExiste = Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("existe"));
            obligatoria = Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("obligatoria"));
        }
    }

    public void moverSeleccionIzquierda() {
        if (posicionSeleccionada == null) {
            return;
        }
        int pos = 0;
        for (int i = 0; i < columnasSeleccionadas.size(); i++) {
            if (columnasSeleccionadas.get(i).getPosicion() == posicionSeleccionada) {
                pos = i;
                break;
            }
        }

        columnasSeleccionadas.get(pos).setPosicion(columnasSeleccionadas.get(pos).getPosicion() - 1);
        columnasSeleccionadas.get(pos - 1).setPosicion(columnasSeleccionadas.get(pos).getPosicion() + 1);
        posicionSeleccionada = posicionSeleccionada - 1;

        Collections.sort(columnasSeleccionadas, new Comparator<ColumnaProformaDTO>() {
            @Override
            public int compare(ColumnaProformaDTO o1, ColumnaProformaDTO o2) {
                return o1.getPosicion() - o2.getPosicion();
            }
        });
    }

    public void moverSeleccionDerecha() {
        if (posicionSeleccionada == null) {
            return;
        }

        int pos = 0;
        for (int i = 0; i < columnasSeleccionadas.size(); i++) {
            if (columnasSeleccionadas.get(i).getPosicion() == posicionSeleccionada) {
                pos = i;
                break;
            }
        }
        if (pos == columnasSeleccionadas.size() - 1) {
            return;
        }
        columnasSeleccionadas.get(pos).setPosicion(columnasSeleccionadas.get(pos).getPosicion() + 1);
        columnasSeleccionadas.get(pos + 1).setPosicion(columnasSeleccionadas.get(pos).getPosicion() - 1);
        posicionSeleccionada = posicionSeleccionada + 1;

        Collections.sort(columnasSeleccionadas, new Comparator<ColumnaProformaDTO>() {
            @Override
            public int compare(ColumnaProformaDTO o1, ColumnaProformaDTO o2) {
                return o1.getPosicion() - o2.getPosicion();
            }
        });
    }

    public void eliminarColumna() {
        for (int i = 0; i < columnasSeleccionadas.size(); i++) {
            ColumnaProformaDTO dto = columnasSeleccionadas.get(i);
            if (dto.getPosicion() == posicionSeleccionada) {
                columnasSeleccionadas.remove(i);
                break;
            }
        }
    }

    public void cancelar() {
        columnaAsociada = null;
        posicionSeleccionada = null;
        dlgNuevaCol = false;
        columnasProformaMBean = new ColumnasProformaMBean();
    }

    public void registrarProforma() {
        ProformaInvoice invoice = null;
        List<ColumnaProformaDTO> tmpColumnas = new ArrayList<>(columnasSeleccionadas);

        for (int i = 0; i < columnasSeleccionadas.size(); i++) {
            ColumnaProformaDTO col = columnasSeleccionadas.get(i);

            if (col.isColumnaExiste()) {
                if (!col.isColumnaEnUso() && !col.isObligatoria()) {
                    columnasSeleccionadas.remove(i);
                    i--;
                }
            } else {
                columnasSeleccionadas.remove(i);
                i--;
            }
        }

        if (columnasSeleccionadas != null && !columnasSeleccionadas.isEmpty()) {
            //Se valida que la proforma contenga los siguientes tipos de columnas, que son necesarios para el manejo correcto de la proforma
            boolean tipoItemExiste = false;
            boolean tipoIdentificadorExiste = false;
            boolean tipoImagenExiste = false;
            boolean tipoCantidadExiste = false;
            boolean tipoValorTotalExiste = false;
            boolean tipoCBMExiste = false;
            boolean tipoValorUnitario = false;

            for (ColumnaProformaDTO c : columnasSeleccionadas) {
                if (c.isTipoItem()) {
                    tipoItemExiste = true;
                }
                if (c.isTipoIdentificador()) {
                    tipoIdentificadorExiste = true;
                }
                if (c.isTipoImagen()) {
                    tipoImagenExiste = true;
                }
                if (c.isTipoCantidad()) {
                    tipoCantidadExiste = true;
                }
                if (c.isTipoValorTotal()) {
                    tipoValorTotalExiste = true;
                }
                if (c.isTipoCBM()) {
                    tipoCBMExiste = true;
                }
                if (c.isTipoValorUnitario()) {
                    tipoValorUnitario = true;
                }
            }

            if (!tipoItemExiste) {
                columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                mostrarMensaje("Error", "Para poder crear la PI, debe incluir una columna tipo item.", true, false, false);
                log.log(Level.SEVERE, "Para poder crear la PI, debe incluir una columna tipo item");
                return;
            } else if (!tipoIdentificadorExiste) {
                columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                mostrarMensaje("Error", "Para poder crear la PI, debe incluir una columna tipo identificador.", true, false, false);
                log.log(Level.SEVERE, "Para poder crear la PI, debe incluir una columna tipo identificador");
                return;
            } else if (!tipoImagenExiste) {
                columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                mostrarMensaje("Error", "Para poder crear la PI, debe incluir una columna tipo imagen.", true, false, false);
                log.log(Level.SEVERE, "Para poder crear la PI, debe incluir una columna tipo imagen");
                return;
            } else if (!tipoCantidadExiste) {
                columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                mostrarMensaje("Error", "Para poder crear la PI, debe incluir una columna tipo cantidad.", true, false, false);
                log.log(Level.SEVERE, "Para poder crear la PI, debe incluir una columna tipo cantidad");
                return;
            } else if (!tipoValorTotalExiste) {
                columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                mostrarMensaje("Error", "Para poder crear la PI, debe incluir una columna tipo valor total.", true, false, false);
                log.log(Level.SEVERE, "Para poder crear la PI, debe incluir una columna tipo valor total");
                return;
            } else if (!tipoCBMExiste) {
                columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                mostrarMensaje("Error", "Para poder crear la PI, debe incluir una columna tipo CBM.", true, false, false);
                log.log(Level.SEVERE, "Para poder crear la PI, debe incluir una columna tipo CBM");
                return;
            } else if (!tipoValorUnitario) {
                columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                mostrarMensaje("Error", "Para poder crear la PI, debe incluir una columna tipo valor unitario.", true, false, false);
                log.log(Level.SEVERE, "Para poder crear la PI, debe incluir una columna tipo valor unitario");
                return;
            }

            //proceso de asignacion de datos a la base de datos
            columnasSeleccionadas = organizarColumnasSinPosicion(columnasSeleccionadas);
            for (int i = 0; i < columnasSeleccionadas.size(); i++) {
                ColumnaProformaDTO col = columnasSeleccionadas.get(i);
                int linea = 0;

                if (invoice == null || invoice.getIdProforma() == null || invoice.getIdProforma() == 0) {
                    invoice = crearProforma();

                    if (invoice == null || invoice.getIdProforma() == null || invoice.getIdProforma() == 0) {
                        columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                        mostrarMensaje("Error", "No fue posible crear la proforma. Comuníquese con el departamento de sistemas.", true, false, false);
                        log.log(Level.SEVERE, "No fue posible crear la proforma. Comuniquese con el departamento de sistemas");
                        return;
                    }
                }

                ConfiguracionProforma c = new ConfiguracionProforma();

                c.setCodigoProveedor(proveedor.getCodProveedor());
                c.setIdColumna(new ColumnaProforma(col.getIdColumna()));
                c.setIdProforma(invoice);
                c.setOrden(col.getPosicion());

                try {
                    configuracionProformaFacade.create(c);
                    log.log(Level.INFO, "Se creo registro de configuracion para el proveedor [{0}], de la proforma [{1}], id configuracion [{2}]",
                            new Object[]{c.getCodigoProveedor(), c.getIdProforma().getIdProforma(), c.getIdConfiguracion()});

                    if (col.isTipoIdentificador()) {
                        int contador = 1;
                        if (evaluarSiDatos(col)) {
                            for (int j = 0; j < col.getValores().size(); j++) {
                                if (agregarDetalleProforma(c.getIdConfiguracion(), linea, String.valueOf(contador))) {
                                    linea++;
                                    contador++;
                                } else {
                                    columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                                    mostrarMensaje("Error", "No se pudo crear la proforma.", true, false, false);
                                    log.log(Level.SEVERE, "No se pudo crear la proforma");
                                    return;
                                }
                            }
                        }
                    } else if (col.getValores() != null && !col.getValores().isEmpty()) {
                        if (evaluarSiDatos(col)) {
                            for (String s : col.getValores()) {
                                if (s == null || s.equals("null")) {
                                    s = "";
                                }

                                if (agregarDetalleProforma(c.getIdConfiguracion(), linea, s)) {
                                    linea++;
                                } else {
                                    columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                                    mostrarMensaje("Error", "No se pudo crear la proforma", true, false, false);
                                    log.log(Level.SEVERE, "No se pudo crear la proforma");
                                    return;
                                }
                            }
                        }
                    } else {
                        for (int k = 0; k < registros; k++) {
                            agregarDetalleProforma(c.getIdConfiguracion(), linea, "");
                            linea++;
                        }
                    }

                    if (linea < registros - 1) {
                        int registrosFaltantes = (registros - linea) - 1;

                        for (int j = 0; j < registrosFaltantes; j++) {
                            if (agregarDetalleProforma(c.getIdConfiguracion(), linea + j, "")) {
                                linea++;
                            } else {
                                columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                                mostrarMensaje("Error", "No se pudo crear la proforma", true, false, false);
                                log.log(Level.SEVERE, "No se pudo crear la proforma");
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    columnasSeleccionadas = new ArrayList<>(tmpColumnas);
                    mostrarMensaje("Error", "No se pudo crear la proforma. Comuniquese con el departamento de sistemas", true, false, false);
                    log.log(Level.SEVERE, "No se pudo crear la proforma. Comuniquese con el departamento de sistemas. Error [{0}]", e.getMessage());
                    return;
                }
            }
            crearDirectorioImagenes(invoice);
            cancelarProceso();
            mostrarMensaje("Éxito", "Proforma creada correctamente: " + invoice.getCodProveedor() + " - " + invoice.getAnio() + " - " + invoice.getConsecutivo(), false, true, false);
        } else {
            columnasSeleccionadas = new ArrayList<>(tmpColumnas);
            log.log(Level.SEVERE, "No se puede continuar, debido a que no se encontraron columnas para aplicar a la informacion");
            mostrarMensaje("Error", "No se puede continuar, debido a que no se encontraron columnas para aplicar a la información.", true, false, false);
            return;
        }
    }

    private boolean agregarDetalleProforma(Integer idConfiguracion, Integer lineNum, String s) {
        DetalleProforma detalle = new DetalleProforma();
        detalle.setValor(s);
        detalle.setIdConfiguracion(new ConfiguracionProforma(idConfiguracion));
        detalle.setLineNum(lineNum);

        try {
            detalleProformaFacade.create(detalle);
//            log.log(Level.INFO, "Se agrego detalle");
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    private ProformaInvoice crearProforma() {
        ProformaInvoice invoice = new ProformaInvoice();

        invoice.setCodProveedor(proveedor.getCodProveedor());
        invoice.setAnio(new SimpleDateFormat("yyyy").format(new Date()));
        invoice.setConsecutivo(proformaInvoiceFacade.siguienteConsecutivo(proveedor.getCodProveedor(), new SimpleDateFormat("yyyy").format(new Date())) + 1);
        invoice.setFecha(new Date());
        invoice.setUsuario("ygil");
        invoice.setCbmTotal(0.0);
        invoice.setValorTotal(0.0);
        invoice.setValorTotalDescuento(0.0);
        invoice.setPrimeraCarga(true);
        invoice.setEstado("PP");

        try {
            proformaInvoiceFacade.create(invoice);
            log.log(Level.INFO, "Se crea proforma {0} - {1} - {2}", new Object[]{invoice.getCodProveedor(), invoice.getAnio(), invoice.getConsecutivo()});
            return invoice;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    private void crearDirectorioImagenes(ProformaInvoice invoice) {
        try {
            File directorioProforma = new File(applicationMBean.obtenerValorPropiedad("url.local.imagesProductosProforma")
                    + File.separator + invoice.getCodProveedor() + "-" + invoice.getAnio() + "-" + invoice.getConsecutivo());

            if (directorioProforma.mkdir()) {
                log.log(Level.INFO, "Direcorio creado correctamente en la siguiente ruta: {0}", directorioProforma.getPath());
            } else {
                log.log(Level.SEVERE, "El directorio no se pudo crear, debera crearse manualmente");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "El directorio no se pudo crear, debera crearse manualmente. Error [{0}]", e.getMessage());
        }
    }

    private boolean evaluarSiDatos(ColumnaProformaDTO col) {
        if (col.getValores() != null && !col.getValores().isEmpty()) {
            registros = col.getValores().size();
            return true;
        } else {
            return false;
        }
    }

    private List<ColumnaProformaDTO> organizarColumnasSinPosicion(List<ColumnaProformaDTO> dto) {
        List<ColumnaProformaDTO> dtoSinPosicion = new ArrayList<>();
        List<ColumnaProformaDTO> dtoOrdenado = new ArrayList<>();
        for (ColumnaProformaDTO d : dto) {
            if (d.getPosicion() == 0) {
                dtoSinPosicion.add(d);
            } else {
                dtoOrdenado.add(d);
            }
        }
        Collections.sort(dtoOrdenado, new Comparator<ColumnaProformaDTO>() {
            @Override
            public int compare(ColumnaProformaDTO o1, ColumnaProformaDTO o2) {
                return o1.getPosicion() - o2.getPosicion();
            }
        });
        if (!dtoOrdenado.isEmpty()) {
            int posicion = 1;
            for (ColumnaProformaDTO d : dtoOrdenado) {
                d.setPosicion(posicion);
                posicion++;
            }
        }
        if (!dtoSinPosicion.isEmpty()) {
            int ultimaPosicion = dtoOrdenado.size();
            for (ColumnaProformaDTO d : dtoSinPosicion) {
                ultimaPosicion++;
                d.setPosicion(ultimaPosicion);
                dtoOrdenado.add(d);
            }
        }
        return dtoOrdenado;
    }

    public void cancelarProceso() {
        columnaAsociada = null;
        posicionSeleccionada = null;
        rutaImagen = "";
        dlgNuevaCol = false;
        botonesOrdenarDeshabilitados = true;
        archivo = null;
        proveedor = null;
        objects = new ArrayList<>();
        columnasSeleccionadas = new ArrayList<>();
        todasColumnas = new ArrayList<>();
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
