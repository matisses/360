package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class DatosProformaDTO {

    private int linea;
    private String imagenObtenida;
    private boolean incluir;
    private boolean cantidadDiferente = false;
    private boolean lineaIncluida = false;
    private List<DetalleProformaDTO> detalleProforma;
    private List<DetalleProformaDTO> detalleProformaFull;
    private List<DetalleProformaDTO> detalleProformaEspecial;

    public DatosProformaDTO() {
        detalleProforma = new ArrayList<>();
    }

    public DatosProformaDTO(int linea, boolean lineaIncluida, List<DetalleProformaDTO> detalleProforma) {
        this.linea = linea;
        this.lineaIncluida = lineaIncluida;
        this.detalleProforma = detalleProforma;
    }

    public DatosProformaDTO(int linea, String imagenObtenida, boolean incluir, List<DetalleProformaDTO> detalleProformaEspecial) {
        this.linea = linea;
        this.imagenObtenida = imagenObtenida;
        this.incluir = incluir;
        this.detalleProformaEspecial = detalleProformaEspecial;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public boolean isIncluir() {
        return incluir;
    }

    public void setIncluir(boolean incluir) {
        this.incluir = incluir;
    }

    public List<DetalleProformaDTO> getDetalleProforma() {
        return detalleProforma;
    }

    public void setDetalleProforma(List<DetalleProformaDTO> detalleProforma) {
        this.detalleProforma = detalleProforma;
    }

    public void agregarDetalle(DetalleProformaDTO dto) {
        detalleProforma.add(dto);
    }

    public String getImagenObtenida() {
        return imagenObtenida;
    }

    public void setImagenObtenida(String imagenObtenida) {
        this.imagenObtenida = imagenObtenida;
    }

    public boolean isCantidadDiferente() {
        return cantidadDiferente;
    }

    public void setCantidadDiferente(boolean cantidadDiferente) {
        this.cantidadDiferente = cantidadDiferente;
    }

    public boolean isLineaIncluida() {
        return lineaIncluida;
    }

    public void setLineaIncluida(boolean lineaIncluida) {
        this.lineaIncluida = lineaIncluida;
    }

    public List<DetalleProformaDTO> getDetalleProformaEspecial() {
        return detalleProformaEspecial;
    }

    public void setDetalleProformaEspecial(List<DetalleProformaDTO> detalleProformaEspecial) {
        this.detalleProformaEspecial = detalleProformaEspecial;
    }

    public List<DetalleProformaDTO> getDetalleProformaFull() {
        return detalleProformaFull;
    }

    public void setDetalleProformaFull(List<DetalleProformaDTO> detalleProformaFull) {
        this.detalleProformaFull = detalleProformaFull;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.linea;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatosProformaDTO other = (DatosProformaDTO) obj;
        if (this.linea != other.linea) {
            return false;
        }
        return true;
    }
}
