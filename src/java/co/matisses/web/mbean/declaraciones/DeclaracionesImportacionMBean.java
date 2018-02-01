package co.matisses.web.mbean.declaraciones;

import co.matisses.persistence.dwb.entity.DeclaracionesImportacion;
import co.matisses.persistence.dwb.facade.DeclaracionesImportacionFacade;
import co.matisses.web.dto.DeclaracionesImportacionDTO;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.poi.ExcelReaderDeclaracion;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@Named(value = "declaracionesImportacionMBean")
public class DeclaracionesImportacionMBean implements Serializable {

    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private UserSessionInfoMBean infoMBean;
    private static final Logger CONSOLE = Logger.getLogger(DeclaracionesImportacionMBean.class.getSimpleName());
    private Part archivo;
    private List<DeclaracionesImportacionDTO> declaraciones;
    @EJB
    private DeclaracionesImportacionFacade declaracionesImportacionFacade;

    public DeclaracionesImportacionMBean() {
        declaraciones = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

    public List<DeclaracionesImportacionDTO> getDeclaraciones() {
        return declaraciones;
    }

    public void setDeclaraciones(List<DeclaracionesImportacionDTO> declaraciones) {
        this.declaraciones = declaraciones;
    }

    public void cargarArchivo() {
        try {
            if (archivo != null) {
                CONSOLE.log(Level.INFO, "Recibiendo archivo: {0}", archivo.getSubmittedFileName());
                CONSOLE.log(Level.INFO, "Tipo contenido: {0}", archivo.getContentType());
                if (archivo.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") || archivo.getContentType().equals("application/vnd.ms-excel")) {
                    recibirExcel(archivo.getInputStream(), archivo.getSubmittedFileName());
                } else {
                    mostrarMensaje("Error", "El tipo de archivo que está intentando subir no es válido. Formatos validos XLSX, XLS.", true, false, false);
                    CONSOLE.log(Level.SEVERE, "El tipo de archivo que esta intentando subir no es valido. Formatos validos XLSX, XLS");
                    return;
                }
            } else {
                mostrarMensaje("Error", "No ha seleccionado un archivo para subir.", true, false, false);
                CONSOLE.log(Level.SEVERE, "No ha seleccionado un archivo para subir");
                return;
            }
        } catch (Exception e) {
            mostrarMensaje("Error", "Se ha producido un error al subir el archivo, verifique la extensión de este.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Se ha producido un error al subir el archivo, verifique la extension de este", e);
            return;
        }
        archivo = null;
    }

    private void recibirExcel(InputStream is, String nombre) {
        ExcelReaderDeclaracion excel = new ExcelReaderDeclaracion();

        excel.setInputStream(is);
        excel.setNombre(nombre);

        interpretarExcel(excel.interpretarExcel());
    }

    private void interpretarExcel(List<Object[]> datos) {
        declaraciones = new ArrayList<>();
        if (datos != null && !datos.isEmpty()) {
            for (Object[] o : datos) {
                DeclaracionesImportacionDTO dto = new DeclaracionesImportacionDTO();

                try {
                    for (int i = 0; i < o.length; i++) {
                        if (o[i] instanceof java.lang.String) {
                            if (i == 0 && o[i] != null) {
                                dto.setNumimp((String) o[i]);
                            } else if (i == 1 && o[i] != null) {
                                dto.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse((String) o[i]));
                            } else if (i == 2 && o[i] != null) {
                                dto.setReferencia(genericMBean.completarReferencia((String) o[i]));
                            } else if (i == 3 && o[i] != null) {
                                dto.setDo1((String) o[i]);
                            } else if (i == 4 && o[i] != null) {
                                dto.setDim((String) o[i]);
                            } else if (i == 5 && o[i] != null) {
                                dto.setCantidad(Integer.parseInt((String) o[i]));
                            }
                        } else if (o[i] instanceof java.lang.Integer) {
                            if (i == 0 && o[i] != null) {
                                dto.setNumimp(((Integer) o[i]).toString());
                            } else if (i == 1 && o[i] != null) {
                                dto.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(((Integer) o[i]).toString()));
                            } else if (i == 2 && o[i] != null) {
                                dto.setReferencia(genericMBean.completarReferencia(((Integer) o[i]).toString()));
                            } else if (i == 3 && o[i] != null) {
                                dto.setDo1(((Integer) o[i]).toString());
                            } else if (i == 4 && o[i] != null) {
                                dto.setDim(((Integer) o[i]).toString());
                            } else if (i == 5 && o[i] != null) {
                                dto.setCantidad((Integer) o[i]);
                            }
                        } else if (o[i] instanceof Date) {
                            if (i == 1 && o[i] != null) {
                                dto.setFecha((Date) o[i]);
                            }
                        } else if (o[i] instanceof java.lang.Double) {
                            if (i == 0 && o[i] != null) {
                                dto.setNumimp(String.valueOf(((Double) o[i]).intValue()));
                            } else if (i == 1 && o[i] != null) {
                                dto.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(((Double) o[i]).toString()));
                            } else if (i == 2 && o[i] != null) {
                                dto.setReferencia(genericMBean.completarReferencia(((Double) o[i]).toString()));
                            } else if (i == 3 && o[i] != null) {
                                dto.setDo1(((Double) o[i]).toString());
                            } else if (i == 4 && o[i] != null) {
                                dto.setDim(((Double) o[i]).toString());
                            } else if (i == 5 && o[i] != null) {
                                dto.setCantidad(((Double) o[i]).intValue());
                            }
                        } else if (o[i] instanceof BigDecimal) {
                            if (i == 0 && o[i] != null) {
                                dto.setNumimp((((BigDecimal) o[i]).toString()).substring(0, 7));
                            } else if (i == 1 && o[i] != null) {
                                dto.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(((BigDecimal) o[i]).toString()));
                            } else if (i == 2 && o[i] != null) {
                                dto.setReferencia(genericMBean.completarReferencia(((BigDecimal) o[i]).toString()));
                            } else if (i == 3 && o[i] != null) {
                                dto.setDo1(((BigDecimal) o[i]).toString());
                            } else if (i == 4 && o[i] != null) {
                                dto.setDim(((BigDecimal) o[i]).toString());
                            } else if (i == 5 && o[i] != null) {
                                dto.setCantidad(((BigDecimal) o[i]).intValue());
                            }
                        } else if (i == 0 && o[i] != null) {
                            dto.setNumimp((String) o[i]);
                        } else if (i == 1 && o[i] != null) {
                            dto.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse((String) o[i]));
                        } else if (i == 2 && o[i] != null) {
                            dto.setReferencia(genericMBean.completarReferencia((String) o[i]));
                        } else if (i == 3 && o[i] != null) {
                            dto.setDo1((String) o[i]);
                        } else if (i == 4 && o[i] != null) {
                            dto.setDim((String) o[i]);
                        } else if (i == 5 && o[i] != null) {
                            dto.setCantidad(Integer.parseInt((String) o[i]));
                        }
                    }

                    if (dto != null && dto.getNumimp() != null && !dto.getNumimp().isEmpty() && dto.getReferencia() != null && !dto.getReferencia().isEmpty()) {
                        declaraciones.add(dto);
                    }
                } catch (ParseException | NumberFormatException e) {
                    mostrarMensaje("Error", "No se pudieron obtener los datos.", true, false, false);
                    CONSOLE.log(Level.SEVERE, "No se pudieron obtener los datos. ", e);
                    return;
                }
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos para cargar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos para cargar");
            return;
        }
    }

    public void guardar() {
        if (declaraciones != null && !declaraciones.isEmpty()) {
            CONSOLE.log(Level.INFO, "Inicia proceso de insercion de datos de declaraciones de importacion");

            int contador = 0;
            for (DeclaracionesImportacionDTO d : declaraciones) {
                DeclaracionesImportacion declaracion = new DeclaracionesImportacion();

                declaracion.setCantidad(d.getCantidad());
                declaracion.setDim(d.getDim());
                declaracion.setDo1(d.getDo1());
                declaracion.setFecha(d.getFecha());
                declaracion.setNroFila(contador);
                declaracion.setNumimp(d.getNumimp());
                declaracion.setReferencia(d.getReferencia());
                declaracion.setSaldo(d.getCantidad());
                declaracion.setUsuario(infoMBean.getUsuario());
                declaracion.setFechaInsercion(new Date());

                try {
                    declaracionesImportacionFacade.create(declaracion);
                } catch (Exception e) {
                }

                contador++;
            }

            limpiar();
        }
    }

    public void limpiar() {
        archivo = null;
        declaraciones = new ArrayList<>();
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
