package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class ItemCcygaDTO implements Comparable<ItemCcygaDTO> {

    private Integer idProducto;
    private String referencia;
    private String refProv;
    private String descripcion;
    private Integer cantidad;
    private boolean cliente;
    private boolean demo;

    public ItemCcygaDTO() {
    }

    public ItemCcygaDTO(Integer idProducto, String referencia, String refProv, String descripcion, Integer cantidad, boolean cliente, boolean demo) {
        this.idProducto = idProducto;
        this.referencia = referencia;
        this.refProv = refProv;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.cliente = cliente;
        this.demo = demo;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isCliente() {
        return cliente;
    }

    public boolean isDemo() {
        return demo;
    }

    public void setDemo(boolean demo) {
        this.demo = demo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRefProv() {
        return refProv;
    }

    public void setRefProv(String refProv) {
        this.refProv = refProv;
    }

    @Override
    public int compareTo(ItemCcygaDTO o) {
        int compare = referencia.compareTo(o.getReferencia());
        if (compare == 0) {
            compare = cantidad - o.getCantidad();
        }
        return compare;
    }
}
