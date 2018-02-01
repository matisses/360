package co.matisses.web.dto;

import java.util.List;

/**
 *
 * @author jguisao
 */
public class PrintReportDTO {

    private int id;
    private int copias;
    private Double comision;
    private String sucursal;
    private String alias;
    private String documento;
    private String dirigido;
    private String inicio;
    private String fin;
    private boolean imprimir;
    private List<String[]> documentosRelacionados;

    public PrintReportDTO() {
    }

    public PrintReportDTO(int id, int copias, Double comision, String sucursal, String alias, String documento, String dirigido, String inicio, String fin, boolean imprimir, List<String[]> documentosRelacionados) {
        this.id = id;
        this.copias = copias;
        this.comision = comision;
        this.sucursal = sucursal;
        this.alias = alias;
        this.documento = documento;
        this.dirigido = dirigido;
        this.inicio = inicio;
        this.fin = fin;
        this.imprimir = imprimir;
        this.documentosRelacionados = documentosRelacionados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCopias() {
        return copias;
    }

    public void setCopias(int copias) {
        this.copias = copias;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDirigido() {
        return dirigido;
    }

    public void setDirigido(String dirigido) {
        this.dirigido = dirigido;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public List<String[]> getDocumentosRelacionados() {
        return documentosRelacionados;
    }

    public void setDocumentosRelacionados(List<String[]> documentosRelacionados) {
        this.documentosRelacionados = documentosRelacionados;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    @Override
    public String toString() {
        return "PrintReportDTO{" + "id=" + id + ", copias=" + copias + ", comision=" + comision + ", sucursal=" + sucursal + ", alias=" + alias + ", documento="
                + documento + ", dirigido=" + dirigido + ", inicio=" + inicio + ", fin=" + fin + ", imprimir=" + imprimir + ", documentosRelacionados=" + documentosRelacionados + '}';
    }
}