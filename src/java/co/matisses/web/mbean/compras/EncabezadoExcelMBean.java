package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.EncabezadoExcel;
import co.matisses.persistence.web.facade.EncabezadoExcelFacade;
import co.matisses.web.dto.EncabezadoExcelDTO;
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
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "encabezadoExcelMBean")
public class EncabezadoExcelMBean implements Serializable {

    private static final Logger log = Logger.getLogger(EncabezadoExcelMBean.class.getSimpleName());
    private Integer idEncabezado;
    private Integer primeraColumna;
    private Integer ultimaColumna;
    private Integer primeraFila;
    private Integer ultimaFila;
    private String valor;
    private boolean alineadoDerecha = false;
    private boolean alineadoCentro = false;
    private boolean alineadoIzquierda = false;
    private List<EncabezadoExcelDTO> datos;
    @EJB
    private EncabezadoExcelFacade encabezadoExcelFacade;

    public EncabezadoExcelMBean() {
        datos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerDatos();
    }

    public boolean isAlineadoIzquierda() {
        return alineadoIzquierda;
    }

    public void setAlineadoIzquierda(boolean alineadoIzquierda) {
        this.alineadoIzquierda = alineadoIzquierda;
    }

    public boolean isAlineadoDerecha() {
        return alineadoDerecha;
    }

    public void setAlineadoDerecha(boolean alineadoDerecha) {
        this.alineadoDerecha = alineadoDerecha;
    }

    public boolean isAlineadoCentro() {
        return alineadoCentro;
    }

    public void setAlineadoCentro(boolean alineadoCentro) {
        this.alineadoCentro = alineadoCentro;
    }

    public List<EncabezadoExcelDTO> getDatos() {
        return datos;
    }

    public void setDatos(List<EncabezadoExcelDTO> datos) {
        this.datos = datos;
    }

    public Integer getPrimeraColumna() {
        return primeraColumna;
    }

    public void setPrimeraColumna(Integer primeraColumna) {
        this.primeraColumna = primeraColumna;
    }

    public Integer getUltimaColumna() {
        return ultimaColumna;
    }

    public void setUltimaColumna(Integer ultimaColumna) {
        this.ultimaColumna = ultimaColumna;
    }

    public Integer getPrimeraFila() {
        return primeraFila;
    }

    public void setPrimeraFila(Integer primeraFila) {
        this.primeraFila = primeraFila;
    }

    public Integer getUltimaFila() {
        return ultimaFila;
    }

    public void setUltimaFila(Integer ultimaFila) {
        this.ultimaFila = ultimaFila;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void validarAlineacionDerecha() {
        if (alineadoDerecha) {
            alineadoDerecha = false;
        } else {
            alineadoDerecha = true;
            alineadoCentro = false;
            alineadoIzquierda = false;
        }
    }

    public void validarAlineacionCentro() {
        if (alineadoCentro) {
            alineadoCentro = false;
        } else {
            alineadoDerecha = false;
            alineadoCentro = true;
            alineadoIzquierda = false;
        }
    }

    public void validarAlineacionIzquierda() {
        if (alineadoIzquierda) {
            alineadoIzquierda = false;
        } else {
            alineadoDerecha = false;
            alineadoCentro = false;
            alineadoIzquierda = true;
        }
    }

    private void obtenerDatos() {
        datos = new ArrayList<>();

        List<EncabezadoExcel> encabezado = encabezadoExcelFacade.obtenerEncabezado("compras");

        if (encabezado != null && !encabezado.isEmpty()) {
            for (EncabezadoExcel e : encabezado) {
                datos.add(new EncabezadoExcelDTO(e.getIdEncabezadoExcel(), e.getColumnaInicial(), e.getColumnaFinal(), e.getFilaInicial(), e.getFilaFinal(),
                        e.getObjecto(), e.getValor(), e.getAlineadoDerecha(), e.getAlineadoIzquierda(), e.getAlineadoCentro()));
            }
        }
    }

    public void guardarConfiguracion() {
        if (primeraColumna == null || primeraColumna == 0) {
            mostrarMensaje("Error", "Se debe ingresar un valor en el campo Primera Columna", true, false, false);
            log.log(Level.SEVERE, "Se debe ingresar un valor en el campo Primera Columna");
            return;
        }
        if (ultimaColumna == null || ultimaColumna == 0) {
            mostrarMensaje("Error", "Se debe ingresar un valor en el campo Ultima Columna", true, false, false);
            log.log(Level.SEVERE, "Se debe ingresar un valor en el campo Ultima Columna");
            return;
        }
        if (primeraFila == null || primeraFila == 0) {
            mostrarMensaje("Error", "Se debe ingresar un valor en el campo Primera Fila", true, false, false);
            log.log(Level.SEVERE, "Se debe ingresar un valor en el campo Primera Fila");
            return;
        }
        if (ultimaFila == null || ultimaFila == 0) {
            mostrarMensaje("Error", "Se debe ingresar un valor en el campo Ultima Fila", true, false, false);
            log.log(Level.SEVERE, "Se debe ingresar un valor en el campo Ultima Fila");
            return;
        }
        if (!alineadoCentro && !alineadoDerecha && !alineadoIzquierda) {
            mostrarMensaje("Error", "Se debe seleccionar un alineado para la configuración", true, false, false);
            log.log(Level.SEVERE, "Se debe seleccionar un alineado para la configuracion");
            return;
        }

        EncabezadoExcel encabezado;
        if (idEncabezado != null && idEncabezado != 0) {
            encabezado = encabezadoExcelFacade.find(idEncabezado);
        } else {
            encabezado = new EncabezadoExcel();
        }

        encabezado.setAlineadoCentro(alineadoCentro);
        encabezado.setAlineadoDerecha(alineadoDerecha);
        encabezado.setAlineadoIzquiera(alineadoIzquierda);
        encabezado.setColumnaFinal(ultimaColumna);
        encabezado.setColumnaInicial(primeraColumna);
        encabezado.setFilaFinal(ultimaFila);
        encabezado.setFilaInicial(primeraFila);
        encabezado.setObjecto("compras");
        encabezado.setValor(valor);

        if (idEncabezado != null && idEncabezado != 0) {
            encabezadoExcelFacade.edit(encabezado);
            log.log(Level.INFO, "Se modifico el encabezado de excel para el modulo de compras. id encabezado: [{0}]", encabezado.getIdEncabezadoExcel());
            mostrarMensaje("Éxito", "Se modifico el encabezado", false, true, false);
            limpiar();
            obtenerDatos();
        } else {
            encabezadoExcelFacade.create(encabezado);
            log.log(Level.INFO, "Se creo el encabezado de excel para el modulo de compras. id encabezado: [{0}]", encabezado.getIdEncabezadoExcel());
            mostrarMensaje("Éxito", "Se creo el encabezado", false, true, false);
            limpiar();
            obtenerDatos();
        }
    }

    public void limpiar() {
        primeraColumna = null;
        ultimaColumna = null;
        primeraFila = null;
        ultimaFila = null;
        valor = null;
        alineadoDerecha = false;
        alineadoCentro = false;
        alineadoIzquierda = false;
        datos = null;
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
