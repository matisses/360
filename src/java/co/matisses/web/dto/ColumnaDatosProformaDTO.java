package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class ColumnaDatosProformaDTO {

    private int posicion;
    private String size;
    private String nombre;
    private String nombreIngles;
    private List<ColumnaProformaDTO> columnas;

    public ColumnaDatosProformaDTO() {
        columnas = new ArrayList<>();
    }

    public ColumnaDatosProformaDTO(int posicion, String nombre) {
        this.posicion = posicion;
        this.nombre = nombre;
    }

    public ColumnaDatosProformaDTO(int posicion, String size, String nombre, String nombreIngles, List<ColumnaProformaDTO> columnas) {
        this.posicion = posicion;
        this.size = size;
        this.nombre = nombre;
        this.nombreIngles = nombreIngles;
        this.columnas = columnas;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ColumnaProformaDTO> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<ColumnaProformaDTO> columnas) {
        this.columnas = columnas;
    }

    public String getNombreIngles() {
        return nombreIngles;
    }

    public void setNombreIngles(String nombreIngles) {
        this.nombreIngles = nombreIngles;
    }
}
