package co.matisses.web.rest.regalos.dto;

import co.matisses.web.dto.InventoryItemDTO;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class ConsultaProductosResponseDTO {
    private int totalProductos;
    private List<InventoryItemDTO> productos;

    public ConsultaProductosResponseDTO(int totalProductos, List<InventoryItemDTO> productos) {
        this.totalProductos = totalProductos;
        this.productos = productos;
    }

    public int getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(int totalProductos) {
        this.totalProductos = totalProductos;
    }

    public List<InventoryItemDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<InventoryItemDTO> productos) {
        this.productos = productos;
    }
    
    
}
