package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class ZebraPrintDTO {

    public final static int LABEL_32X15 = 1;
    public final static int LABEL_40X30 = 2;
    public final static int LABEL_32X25 = 3;

    private String printerName;
    private int columns;
    private int labelType;
    private List<ItemLabelDTO> items;
    private List<CodigoRevisadoLabelDTO> codigos;

    public ZebraPrintDTO() {
        items = new ArrayList<>();
        codigos = new ArrayList<>();
    }

    public List<CodigoRevisadoLabelDTO> getCodigos() {
        return codigos;
    }

    public void setCodigos(List<CodigoRevisadoLabelDTO> codigos) {
        this.codigos = codigos;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getLabelType() {
        return labelType;
    }

    public void setLabelType(int labelType) {
        this.labelType = labelType;
    }

    public List<ItemLabelDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemLabelDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ZebraPrintDTO{" + "printerName=" + printerName + ", columns=" + columns + ", labelType=" + labelType + ", items=" + items + ", codigos=" + codigos + '}';
    }

}
