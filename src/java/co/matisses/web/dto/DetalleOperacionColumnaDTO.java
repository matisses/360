package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class DetalleOperacionColumnaDTO {

    private Integer idDetalleOperacion;
    private Integer orden;
    private Integer idOperacionColumnaProforma;
    private String constante;
    private ColumnaProformaDTO idColumna2;
    private ColumnaProformaDTO idColumna1;
    private OperacionDTO idOperacion;

    public DetalleOperacionColumnaDTO() {
    }

    public DetalleOperacionColumnaDTO(Integer idDetalleOperacion, Integer orden, Integer idOperacionColumnaProforma, String constante, ColumnaProformaDTO idColumna1, ColumnaProformaDTO idColumna2,
            OperacionDTO idOperacion) {
        this.idDetalleOperacion = idDetalleOperacion;
        this.orden = orden;
        this.idOperacionColumnaProforma = idOperacionColumnaProforma;
        this.idOperacion = idOperacion;
        this.idColumna2 = idColumna2;
        this.idColumna1 = idColumna1;
        this.constante = constante;
    }

    public Integer getIdDetalleOperacion() {
        return idDetalleOperacion;
    }

    public void setIdDetalleOperacion(Integer idDetalleOperacion) {
        this.idDetalleOperacion = idDetalleOperacion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIdOperacionColumnaProforma() {
        return idOperacionColumnaProforma;
    }

    public void setIdOperacionColumnaProforma(Integer idOperacionColumnaProforma) {
        this.idOperacionColumnaProforma = idOperacionColumnaProforma;
    }

    public OperacionDTO getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(OperacionDTO idOperacion) {
        this.idOperacion = idOperacion;
    }

    public ColumnaProformaDTO getIdColumna2() {
        return idColumna2;
    }

    public void setIdColumna2(ColumnaProformaDTO idColumna2) {
        this.idColumna2 = idColumna2;
    }

    public ColumnaProformaDTO getIdColumna1() {
        return idColumna1;
    }

    public void setIdColumna1(ColumnaProformaDTO idColumna1) {
        this.idColumna1 = idColumna1;
    }

    public String getConstante() {
        return constante;
    }

    public void setConstante(String constante) {
        this.constante = constante;
    }
}
