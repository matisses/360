package co.matisses.web.mbean.session;

import co.matisses.persistence.web.entity.ColumnaProforma;
import co.matisses.persistence.web.entity.ConfiguracionProforma;
import co.matisses.persistence.web.facade.ConfiguracionProformaFacade;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@SessionScoped
@Named(value = "proformaSessionMBean")
public class ProformaSessionMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ProformaSessionMBean.class.getSimpleName());
    private Integer linea = -1;
    private Integer tagEnable = 1;
    private Integer tagDatosProformaEnable = 1;
    private Integer idProforma = 0;
    private Integer idContenedorProforma;
    @EJB
    private ConfiguracionProformaFacade configuracionProformaFacade;

    public ProformaSessionMBean() {
    }

    @PostConstruct
    protected void initialize() {
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public Integer getTagEnable() {
        return tagEnable;
    }

    public void setTagEnable(Integer tagEnable) {
        this.tagEnable = tagEnable;
    }

    public Integer getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(Integer idProforma) {
        this.idProforma = idProforma;
    }

    public Integer getTagDatosProformaEnable() {
        return tagDatosProformaEnable;
    }

    public void setTagDatosProformaEnable(Integer tagDatosProformaEnable) {
        this.tagDatosProformaEnable = tagDatosProformaEnable;
    }

    public Integer getIdContenedorProforma() {
        return idContenedorProforma;
    }

    public void setIdContenedorProforma(Integer idContenedorProforma) {
        this.idContenedorProforma = idContenedorProforma;
    }

    public ColumnaProforma obtenerColumna(Integer idConfiguracion) {
        if (idConfiguracion != null && idConfiguracion != 0) {
            ConfiguracionProforma conf = configuracionProformaFacade.find(idConfiguracion);
            if (conf != null && conf.getIdConfiguracion() != null && conf.getIdConfiguracion() != 0) {
                if (conf.getIdColumna() != null && conf.getIdColumna().getIdColumna() != null && conf.getIdColumna().getIdColumna() != 0) {
                    return conf.getIdColumna();
                }
            }
        }
        return null;
    }

    public String mostrarDatosColumna(Integer idConfiguracion, String origen) {
        if (idConfiguracion != null && idConfiguracion != 0) {
            return tratarColumna(obtenerColumna(idConfiguracion), origen);
        }
        return "";
    }

    public String tratarColumna(ColumnaProforma col, String origen) {
        String r = "";
        r += col.getNombre1();
        switch (origen) {
            case "vista":
                if (col.getNombre2() != null && !col.getNombre2().isEmpty()) {
                    if ((r + " - " + col.getNombre2()).length() > 15) {
                        r = col.getNombre2();
                    } else {
                        r += " - " + col.getNombre2();
                    }
                }
                break;
            case "bean":
                if (col.getNombre2() != null && !col.getNombre2().isEmpty()) {
                    r += " - " + col.getNombre2();
                }
                break;
        }
        return r;
    }
}
