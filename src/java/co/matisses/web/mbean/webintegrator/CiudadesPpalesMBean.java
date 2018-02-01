package co.matisses.web.mbean.webintegrator;

import co.matisses.persistence.sap.entity.Municipio;
import co.matisses.persistence.sap.facade.MunicipioFacade;
import co.matisses.persistence.web.entity.MunicipiosCercanos;
import co.matisses.persistence.web.facade.MunicipiosCercanosFacade;
import co.matisses.web.dto.MunicipioDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "ciudadesPpalesMBean")
public class CiudadesPpalesMBean implements Serializable {

    private static final Logger log = Logger.getLogger(CiudadesPpalesMBean.class.getSimpleName());
    private String codCiudadPpal;
    private String codCiudadSec;
    private String codCiudadRestringir;
    private List<MunicipioDTO> municipios;
    private List<MunicipioDTO> municipiosCercanos;
    private List<MunicipioDTO> municipiosRestringidos;

    @EJB
    private MunicipioFacade municipioFacade;
    @EJB
    private MunicipiosCercanosFacade municipiosCercanosFacade;

    public CiudadesPpalesMBean() {
        municipios = new ArrayList<>();
        municipiosCercanos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarMunicipios();
        cargarMunicipiosRestringidos();
    }

    public String getCodCiudadPpal() {
        return codCiudadPpal;
    }

    public void setCodCiudadPpal(String codCiudadPpal) {
        this.codCiudadPpal = codCiudadPpal;
    }

    public String getCodCiudadSec() {
        return codCiudadSec;
    }

    public void setCodCiudadSec(String codCiudadSec) {
        this.codCiudadSec = codCiudadSec;
    }

    public String getCodCiudadRestringir() {
        return codCiudadRestringir;
    }

    public void setCodCiudadRestringir(String codCiudadRestringir) {
        this.codCiudadRestringir = codCiudadRestringir;
    }

    public List<MunicipioDTO> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<MunicipioDTO> municipios) {
        this.municipios = municipios;
    }

    public List<MunicipioDTO> getMunicipiosCercanos() {
        return municipiosCercanos;
    }

    public void setMunicipiosCercanos(List<MunicipioDTO> municipiosCercanos) {
        this.municipiosCercanos = municipiosCercanos;
    }

    public List<MunicipioDTO> getMunicipiosRestringidos() {
        return municipiosRestringidos;
    }

    public void setMunicipiosRestringidos(List<MunicipioDTO> municipiosRestringidos) {
        this.municipiosRestringidos = municipiosRestringidos;
    }

    private void cargarMunicipios() {
        List<Municipio> entidades = municipioFacade.findAll();
        municipios = new ArrayList<>();
        for (Municipio entidad : entidades) {
            municipios.add(new MunicipioDTO(entidad.getCode(), entidad.getName()));
        }
        Collections.sort(municipios);
    }

    private void cargarMunicipiosRestringidos() {
        List<MunicipiosCercanos> entidades = municipiosCercanosFacade.findByPpal("00000");
        municipiosRestringidos = new ArrayList<>();
        for (MunicipiosCercanos entidad : entidades) {
            MunicipioDTO dto = municipios.get(municipios.indexOf(new MunicipioDTO(entidad.getCodMunicipio(), null)));
            municipiosRestringidos.add(dto);
        }
        Collections.sort(municipiosRestringidos);
    }

    public void cargarAreaMetropolitana() {
        log.log(Level.INFO, "Cargando el area metropolitana para la ciudad [{0}]", codCiudadPpal);
        municipiosCercanos = new ArrayList<>();
        List<MunicipiosCercanos> entidades = municipiosCercanosFacade.findByPpal(codCiudadPpal);
        for (MunicipiosCercanos entidad : entidades) {
            MunicipioDTO dto = municipios.get(municipios.indexOf(new MunicipioDTO(entidad.getCodMunicipio(), null)));
            municipiosCercanos.add(dto);
        }
        Collections.sort(municipiosCercanos);
    }

    public void valorCambio() {

    }

    public void agregarMpio() {
        if (codCiudadPpal == null || codCiudadPpal.trim().isEmpty()) {
            return;
        }
        if (codCiudadSec == null || codCiudadSec.trim().isEmpty()) {
            return;
        }
        if (municipiosCercanos.contains(new MunicipioDTO(codCiudadSec, null))) {
            log.log(Level.WARNING, "No se puede agregar el municipio porque ya existe");
            return;
        }
        try {
            MunicipiosCercanos entidad = new MunicipiosCercanos();
            entidad.setCodMunicipio(codCiudadSec);
            entidad.setCodMunicipioPpal(codCiudadPpal);
            municipiosCercanosFacade.create(entidad);
            cargarAreaMetropolitana();
            codCiudadSec = "";
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al almacenar el nuevo municipio. ", e);
        }
    }

    public void eliminarMpio() {
        try {
            String codMpio = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codMpio");
            if (codMpio == null || codMpio.trim().isEmpty()) {
                return;
            }
            municipiosCercanosFacade.remove(new MunicipiosCercanos(codMpio));
            cargarAreaMetropolitana();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible eliminar el municipio. ", e);
        }
    }
}
