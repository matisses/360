package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class MenuItemDTO implements Comparable<MenuItemDTO> {

    private Integer idMenu;
    private ObjetoBWSDTO objeto;
    private Integer idAccionObjeto;
    private int orden;
    private String nombre;
    private String nombreAlterno;
    private String ruta;
    private String destino;
    private MenuItemDTO menuPadre;
    private boolean padre;
    private boolean tieneSeparadorAntes;
    private boolean tieneSeparadorDespues;
    private boolean activo;
    private List<MenuItemDTO> hijos;

    public MenuItemDTO() {
        hijos = new ArrayList<>();
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Integer idMenu) {
        this.idMenu = idMenu;
    }

    public ObjetoBWSDTO getObjeto() {
        return objeto;
    }

    public void setObjeto(ObjetoBWSDTO objeto) {
        this.objeto = objeto;
    }

    public Integer getIdAccionObjeto() {
        return idAccionObjeto;
    }

    public void setIdAccionObjeto(Integer idAccionObjeto) {
        this.idAccionObjeto = idAccionObjeto;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreAlterno() {
        return nombreAlterno;
    }

    public void setNombreAlterno(String nombreAlterno) {
        this.nombreAlterno = nombreAlterno;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDestinoSeleccionado() {
        if (destino != null && !destino.isEmpty()) {
            if (destino.equals("_self")) {
                return "Misma página";
            } else if (destino.equals("_blank")) {
                return "Nueva página";
            }
        }
        return "Seleccione";
    }

    public MenuItemDTO getMenuPadre() {
        return menuPadre;
    }

    public void setMenuPadre(MenuItemDTO menuPadre) {
        this.menuPadre = menuPadre;
    }

    public boolean isPadre() {
        return padre;
    }

    public void setPadre(boolean padre) {
        this.padre = padre;
    }

    public String getEsMenuSeleccionado() {
        if (padre) {
            return "Sí";
        } else if (!padre) {
            return "No";
        }
        return "Seleccione";
    }

    public boolean isTieneSeparadorAntes() {
        return tieneSeparadorAntes;
    }

    public void setTieneSeparadorAntes(boolean tieneSeparadorAntes) {
        this.tieneSeparadorAntes = tieneSeparadorAntes;
    }

    public String getTieneSeparadorAntesSeleccionado() {
        if (tieneSeparadorAntes) {
            return "Sí";
        } else if (!tieneSeparadorAntes) {
            return "No";
        }
        return "Seleccione";
    }

    public boolean isTieneSeparadorDespues() {
        return tieneSeparadorDespues;
    }

    public void setTieneSeparadorDespues(boolean tieneSeparadorDespues) {
        this.tieneSeparadorDespues = tieneSeparadorDespues;
    }

    public String getTieneSeparadorDespuesSeleccionado() {
        if (tieneSeparadorDespues) {
            return "Sí";
        } else if (!tieneSeparadorDespues) {
            return "No";
        }
        return "Seleccione";
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getActivoSeleccionado() {
        if (activo) {
            return "Sí";
        } else if (!activo) {
            return "No";
        }
        return "Seleccione";
    }

    public List<MenuItemDTO> getHijos() {
        return hijos;
    }

    public void setHijos(List<MenuItemDTO> hijos) {
        this.hijos = hijos;
    }

    public void agregarHijo(MenuItemDTO dto) {
        hijos.add(dto);
        Collections.sort(hijos);
    }

    @Override
    public int compareTo(MenuItemDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public String toString() {
        return "MenuItemDTO{" + "idMenu=" + idMenu + ", objeto=" + objeto + ", idAccionObjeto=" + idAccionObjeto + ", orden=" + orden + ", nombre=" + nombre
                + ", nombreAlterno=" + nombreAlterno + ", ruta=" + ruta + ", menuPadre=" + menuPadre + ", padre=" + padre + ", tieneSeparadorAntes=" + tieneSeparadorAntes
                + ", tieneSeparadorDespues=" + tieneSeparadorDespues + ", activo=" + activo + ", hijos=" + hijos.size() + '}';
    }
}
