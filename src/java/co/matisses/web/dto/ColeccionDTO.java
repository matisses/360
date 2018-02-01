package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class ColeccionDTO {
    private String codigo;
    private String nombre;
    private Date fechaInicio;
    private List<CategoriaColeccionDTO> categorias;

    public ColeccionDTO() {
        categorias = new ArrayList<>();
    }

    public ColeccionDTO(String codigo, String nombre, Date fechaInicio, List<CategoriaColeccionDTO> categorias) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.categorias = categorias;
    }

    public List<CategoriaColeccionDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaColeccionDTO> categorias) {
        this.categorias = categorias;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void agregarCategoria(CategoriaColeccionDTO categoria){
        categorias.add(categoria);
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}
