package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class LlamadaSolucionDTO {

    private Integer docEntry;
    private Integer numero;
    private Long idLlamadaSolucion;
    private String itemCode;
    private Integer estado;
    private String propietario;
    private String solucion;
    private String sintoma;
    private String causa;
    private String comentarios;
    private Date fecha;

    public LlamadaSolucionDTO() {
    }

    public LlamadaSolucionDTO(Long idLlamadaSolucion, Integer docEntry, Integer numero, String itemCode, Integer estado, String propietario,
            String solucion, String sintoma, String causa, String comentarios, Date fecha) {
        this.idLlamadaSolucion = idLlamadaSolucion;
        this.docEntry = docEntry;
        this.numero = numero;
        this.itemCode = itemCode;
        this.estado = estado;
        this.propietario = propietario;
        this.solucion = solucion;
        this.sintoma = sintoma;
        this.causa = causa;
        this.comentarios = comentarios;
        this.fecha = fecha;
    }

    public Long getIdLlamadaSolucion() {
        return idLlamadaSolucion;
    }

    public void setIdLlamadaSolucion(Long idLlamadaSolucion) {
        this.idLlamadaSolucion = idLlamadaSolucion;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }

    public String getSintoma() {
        return sintoma;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
