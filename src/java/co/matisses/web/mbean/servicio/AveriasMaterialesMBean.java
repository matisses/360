package co.matisses.web.mbean.servicio;

import co.matisses.persistence.sap.entity.BaruAverias;
import co.matisses.persistence.sap.entity.BaruAveriasMaterial;
import co.matisses.persistence.sap.entity.BaruMateriales;
import co.matisses.persistence.sap.facade.BaruAveriasFacade;
import co.matisses.persistence.sap.facade.BaruAveriasMaterialFacade;
import co.matisses.persistence.sap.facade.BaruMaterialesFacade;
import co.matisses.web.dto.BaruAveriasDTO;
import co.matisses.web.dto.BaruAveriasMaterialDTO;
import co.matisses.web.dto.BaruMaterialesDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@Named(value = "averiasMaterialesMBean")
public class AveriasMaterialesMBean implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(AveriasMaterialesMBean.class.getSimpleName());
    private String material;
    private String nombreAveria;
    private List<BaruMaterialesDTO> materiales;
    private List<BaruAveriasDTO> averias;
    private List<BaruAveriasDTO> averiasDisponibles;
    private List<BaruAveriasMaterialDTO> averiasMaterial;
    @EJB
    private BaruMaterialesFacade baruMaterialesFacade;
    @EJB
    private BaruAveriasMaterialFacade baruAveriasMaterialFacade;
    @EJB
    private BaruAveriasFacade baruAveriasFacade;

    public AveriasMaterialesMBean() {
        materiales = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerMateriales();
        obtenerAverias();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getNombreAveria() {
        return nombreAveria;
    }

    public void setNombreAveria(String nombreAveria) {
        this.nombreAveria = nombreAveria;
    }

    public List<BaruMaterialesDTO> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<BaruMaterialesDTO> materiales) {
        this.materiales = materiales;
    }

    public List<BaruAveriasDTO> getAveriasDisponibles() {
        return averiasDisponibles;
    }

    public void setAveriasDisponibles(List<BaruAveriasDTO> averiasDisponibles) {
        this.averiasDisponibles = averiasDisponibles;
    }

    public List<BaruAveriasMaterialDTO> getAveriasMaterial() {
        return averiasMaterial;
    }

    public void setAveriasMaterial(List<BaruAveriasMaterialDTO> averiasMaterial) {
        this.averiasMaterial = averiasMaterial;
    }

    public String getNombreAveria(String code) {
        if (code != null && !code.isEmpty()) {
            for (BaruAveriasDTO a : averias) {
                if (a.getCode().equals(code)) {
                    return a.getName();
                }
            }
        }
        return "";
    }

    private void obtenerMateriales() {
        materiales = new ArrayList<>();

        List<BaruMateriales> materials = baruMaterialesFacade.findAll();

        if (materials != null && !materials.isEmpty()) {
            for (BaruMateriales m : materials) {
                List<BaruAveriasMaterial> faults = baruAveriasMaterialFacade.obtenerAveriasMaterial(m.getCode());

                materiales.add(new BaruMaterialesDTO(faults != null ? faults.size() : 0, m.getCode(), m.getName(), m.getuCuidados()));
            }

            Collections.sort(materiales, new Comparator<BaruMaterialesDTO>() {
                @Override
                public int compare(BaruMaterialesDTO o1, BaruMaterialesDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void obtenerAverias() {
        averias = new ArrayList<>();
        averiasDisponibles = new ArrayList<>();
        averiasMaterial = new ArrayList<>();

        List<BaruAverias> faults = baruAveriasFacade.findAll();

        if (faults != null && !faults.isEmpty()) {
            for (BaruAverias a : faults) {
                averias.add(new BaruAveriasDTO(a.getCode(), a.getName()));
            }

            averiasDisponibles = new ArrayList<>(averias);
        }
    }

    public void seleccionarMaterial() {
        averiasDisponibles = new ArrayList<>();
        averiasMaterial = new ArrayList<>();
        String mat = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("material");

        if (material == null || material.isEmpty()) {
            material = mat;
        } else if (material.equals(mat)) {
            material = null;
        }

        List<BaruAveriasMaterial> faults = baruAveriasMaterialFacade.obtenerAveriasMaterial(material);

        if (faults != null && !faults.isEmpty()) {
            for (BaruAveriasMaterial a : faults) {
                averiasMaterial.add(new BaruAveriasMaterialDTO(a.getCode(), a.getName(), a.getUMaterial(), a.getUProblema()));
            }

            Collections.sort(averiasMaterial, new Comparator<BaruAveriasMaterialDTO>() {
                @Override
                public int compare(BaruAveriasMaterialDTO o1, BaruAveriasMaterialDTO o2) {
                    return getNombreAveria(o1.getUProblema()).compareTo(getNombreAveria(o2.getUProblema()));
                }
            });

            if (averiasMaterial != null && !averiasMaterial.isEmpty()) {
                for (BaruAveriasDTO s : averias) {
                    boolean existe = false;

                    for (BaruAveriasMaterialDTO a : averiasMaterial) {
                        if (a.getUProblema().equals(s.getCode())) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        averiasDisponibles.add(s);
                    }
                }
            }
        } else {
            averiasDisponibles = new ArrayList<>(averias);
        }
    }

    public void asignarAveria() {
        String averia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code");

        BaruAveriasMaterial fault = new BaruAveriasMaterial();

        Integer id = baruAveriasMaterialFacade.obtenerSiguienteID();

        fault.setCode(id.toString());
        fault.setName(id.toString());
        fault.setUMaterial(material);
        fault.setUProblema(averia);

        try {
            baruAveriasMaterialFacade.create(fault);
            CONSOLE.log(Level.INFO, "Se agrego la averia {0} al material {1}", new Object[]{fault.getUProblema(), fault.getUMaterial()});
            averiasMaterial.add(new BaruAveriasMaterialDTO(fault.getCode(), fault.getName(), fault.getUMaterial(), fault.getUProblema()));

            Collections.sort(averiasMaterial, new Comparator<BaruAveriasMaterialDTO>() {
                @Override
                public int compare(BaruAveriasMaterialDTO o1, BaruAveriasMaterialDTO o2) {
                    return getNombreAveria(o1.getUProblema()).compareTo(getNombreAveria(o2.getUProblema()));
                }
            });

            if (averiasMaterial != null && !averiasMaterial.isEmpty()) {
                for (int i = 0; i < averiasDisponibles.size(); i++) {
                    BaruAveriasDTO s = averiasDisponibles.get(i);

                    for (BaruAveriasMaterialDTO a : averiasMaterial) {
                        if (a.getUProblema().equals(s.getCode())) {
                            averiasDisponibles.remove(i);
                            i--;
                            break;
                        }
                    }
                }
            }

            for (BaruMaterialesDTO b : materiales) {
                if (b.getCode().equals(material)) {
                    b.setCantidadAverias(averiasMaterial.size());
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    public void quitarAveria() {
        String averiaMaterial = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code");

        BaruAveriasMaterial fault = baruAveriasMaterialFacade.find(averiaMaterial);

        if (fault != null && fault.getCode() != null && !fault.getCode().isEmpty()) {
            try {
                baruAveriasMaterialFacade.remove(fault);
                CONSOLE.log(Level.INFO, "Se quito la averia {0} al material {1}", new Object[]{fault.getUProblema(), fault.getUMaterial()});
                for (BaruAveriasMaterialDTO m : averiasMaterial) {
                    if (m.getCode().equals(averiaMaterial)) {
                        averiasMaterial.remove(m);
                        break;
                    }
                }

                for (BaruAveriasDTO s : averias) {
                    if (s.getCode().equals(fault.getUProblema())) {
                        averiasDisponibles.add(s);
                        break;
                    }
                }

                Collections.sort(averiasDisponibles, new Comparator<BaruAveriasDTO>() {
                    @Override
                    public int compare(BaruAveriasDTO o1, BaruAveriasDTO o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                for (BaruMaterialesDTO b : materiales) {
                    if (b.getCode().equals(material)) {
                        b.setCantidadAverias(averiasMaterial.size());
                        break;
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public void guardarAveria() {
        if (nombreAveria == null || nombreAveria.isEmpty()) {
            mostrarMensaje("Error", "Por favor asigne el nombre que quiere para la avería.", true, false, false);
            CONSOLE.log(Level.SEVERE, "por favor asigne el nombre que quiere para la averia");
            return;
        }

        /*Se valida que no haya una averia con el nombre que se esta ingresando*/
        BaruAverias fault = baruAveriasFacade.obtenerAveria(nombreAveria);

        if (fault != null && fault.getCode() != null && !fault.getCode().isEmpty()) {
            mostrarMensaje("Error", "La avería que esta intentando agregar ya existe.", true, false, false);
            CONSOLE.log(Level.SEVERE, "La averia que esta intentando agregar ya existe");
            return;
        } else {
            fault = new BaruAverias();

            fault.setCode(String.valueOf(System.currentTimeMillis()));
            fault.setName(nombreAveria.toUpperCase());

            try {
                baruAveriasFacade.create(fault);
                CONSOLE.log(Level.INFO, "Se creo una averia con los siguientes datos [{0} {1}]", new Object[]{fault.getCode(), fault.getName()});

                averias.add(new BaruAveriasDTO(fault.getCode(), fault.getName()));
                averiasDisponibles.add(new BaruAveriasDTO(fault.getCode(), fault.getName()));

                Collections.sort(averiasDisponibles, new Comparator<BaruAveriasDTO>() {
                    @Override
                    public int compare(BaruAveriasDTO o1, BaruAveriasDTO o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                Collections.sort(averias, new Comparator<BaruAveriasDTO>() {
                    @Override
                    public int compare(BaruAveriasDTO o1, BaruAveriasDTO o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
            } catch (Exception e) {
            }
        }
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
