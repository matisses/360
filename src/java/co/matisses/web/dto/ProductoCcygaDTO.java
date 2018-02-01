package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class ProductoCcygaDTO implements Comparable<ProductoCcygaDTO> {

    private Integer idProducto;
    private String referencia;
    private String descripcion;
    private String refProveedor;
    private String documento;
    private String requerimiento;
    private boolean cliente;
    private boolean demo;
    private List<ProcesoCcygaDTO> procesos;

    public ProductoCcygaDTO() {
        procesos = new ArrayList<>();
    }

    public ProductoCcygaDTO(Integer idProducto) {
        this.idProducto = idProducto;
        procesos = new ArrayList<>();
    }

    public ProductoCcygaDTO(Integer idProducto, String referencia, String descripcion, String refProveedor, String documento, String requerimiento,
            boolean cliente, boolean demo, List<ProcesoCcygaDTO> procesos) {
        this.idProducto = idProducto;
        this.referencia = referencia;
        this.descripcion = descripcion;
        this.refProveedor = refProveedor;
        this.documento = documento;
        this.requerimiento = requerimiento;
        this.cliente = cliente;
        this.demo = demo;
        this.procesos = procesos;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRefProveedor() {
        return refProveedor;
    }

    public void setRefProveedor(String refProveedor) {
        this.refProveedor = refProveedor;
    }

    public String getRefCorta() {
        if (referencia == null || referencia.isEmpty() || referencia.length() < 20) {
            return "";
        }
        return referencia.substring(0, 3) + "." + referencia.substring(16);
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(String requerimiento) {
        this.requerimiento = requerimiento;
    }

    public List<ProcesoCcygaDTO> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<ProcesoCcygaDTO> procesos) {
        this.procesos = procesos;
    }

    public boolean isCliente() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isDemo() {
        return demo;
    }

    public void setDemo(boolean demo) {
        this.demo = demo;
    }

    @Override
    public int compareTo(ProductoCcygaDTO o) {
        return this.getReferencia().compareTo(o.getReferencia());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idProducto);
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
        final ProductoCcygaDTO other = (ProductoCcygaDTO) obj;
        if (!Objects.equals(this.idProducto, other.idProducto)) {
            return false;
        }
        return true;
    }

}
