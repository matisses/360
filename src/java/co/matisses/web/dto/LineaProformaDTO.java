package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class LineaProformaDTO {

    private Integer cantidadNecesaria = 0;
    private Integer cantidadUsada = 0;
    private List<ContenedorLineaDTO> contenedores;

    public LineaProformaDTO() {
        contenedores = new ArrayList<>();
    }

    public LineaProformaDTO(Integer cantidadNecesaria, Integer cantidadUsada, List<ContenedorLineaDTO> contenedores) {
        this.cantidadNecesaria = cantidadNecesaria;
        this.cantidadUsada = cantidadUsada;
        this.contenedores = contenedores;
    }

    public Integer getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(Integer cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public Integer getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(Integer cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public List<ContenedorLineaDTO> getContenedores() {
        return contenedores;
    }

    public void setContenedores(List<ContenedorLineaDTO> contenedores) {
        this.contenedores = contenedores;
    }
}
