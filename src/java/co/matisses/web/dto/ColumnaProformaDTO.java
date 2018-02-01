package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class ColumnaProformaDTO implements Comparable<ColumnaProformaDTO> {

    private int posicion;
    private Integer idColumna;
    private Integer decimalesVisibles;
    private Integer idOperacionColumna;
    private String nombre1;
    private String nombre1Ingles;
    private String nombre2;
    private String nombre2Ingles;
    private String longitudOcupadaTabla;
    private boolean columnaExiste = true;
    private boolean columnaEnUso = false;
    private boolean tipoCantidad = false;
    private boolean permitirCrearItem;
    private boolean requiereOperacion;
    private boolean tipoItem;
    private boolean tipoIdentificador;
    private boolean tipoImagen;
    private boolean obligatoria = false;
    private boolean incluirProforma;
    private boolean modificable;
    private boolean modificableSiNuevo;
    private boolean tipoValorTotal;
    private boolean tipoCBM;
    private boolean tipoValorUnitario;
    private boolean tipoDescuento;
    private boolean tipoDescripcionItem;
    private TipoDatosDTO idTipoDato;
    private List<String> valores;

    public ColumnaProformaDTO() {
        idTipoDato = new TipoDatosDTO();
        valores = new ArrayList<>();
    }

    public ColumnaProformaDTO(Integer idColumna) {
        this.idColumna = idColumna;
    }

    public ColumnaProformaDTO(Integer idColumna, String nombre1, String nombre2) {
        this.idColumna = idColumna;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
    }

    public ColumnaProformaDTO(int posicion, Integer idColumna, Integer decimalesVisibles, Integer idOperacionColumna,
            String nombre1, String nombre1Ingles, String nombre2, String nombre2Ingles, String longitudOcupadaTabla, boolean tipoCantidad, boolean permitirCrearItem,
            boolean requiereOperacion, boolean tipoItem, boolean tipoIdentificador, boolean tipoImagen, boolean obligatoria, boolean incluirProforma,
            boolean modificable, boolean modificableSiNuevo, boolean tipoValorTotal, boolean tipoCBM, boolean tipoValorUnitario, boolean tipoDescuento,
            TipoDatosDTO idTipoDato, List<String> valores) {
        this.posicion = posicion;
        this.idColumna = idColumna;
        this.decimalesVisibles = decimalesVisibles;
        this.idOperacionColumna = idOperacionColumna;
        this.nombre1 = nombre1;
        this.nombre1Ingles = nombre1Ingles;
        this.nombre2 = nombre2;
        this.nombre2Ingles = nombre2Ingles;
        this.longitudOcupadaTabla = longitudOcupadaTabla;
        this.tipoCantidad = tipoCantidad;
        this.permitirCrearItem = permitirCrearItem;
        this.requiereOperacion = requiereOperacion;
        this.tipoItem = tipoItem;
        this.tipoIdentificador = tipoIdentificador;
        this.tipoImagen = tipoImagen;
        this.obligatoria = obligatoria;
        this.incluirProforma = incluirProforma;
        this.modificable = modificable;
        this.modificableSiNuevo = modificableSiNuevo;
        this.tipoValorTotal = tipoValorTotal;
        this.idTipoDato = idTipoDato;
        this.valores = valores;
        this.tipoDescuento = tipoDescuento;
    }

    public ColumnaProformaDTO(int posicion, Integer idColumna, Integer decimalesVisibles, Integer idOperacionColumna,
            String nombre1, String nombre1Ingles, String nombre2, String nombre2Ingles, String longitudOcupadaTabla, boolean tipoCantidad, boolean permitirCrearItem,
            boolean requiereOperacion, boolean tipoItem, boolean tipoIdentificador, boolean tipoImagen, boolean obligatoria, boolean incluirProforma,
            boolean modificable, boolean modificableSiNuevo, boolean tipoValorTotal, boolean tipoCBM, boolean tipoValorUnitario, boolean tipoDescuento, boolean tipoDescripcionItem,
            TipoDatosDTO idTipoDato, List<String> valores) {
        this.posicion = posicion;
        this.idColumna = idColumna;
        this.decimalesVisibles = decimalesVisibles;
        this.idOperacionColumna = idOperacionColumna;
        this.nombre1 = nombre1;
        this.nombre1Ingles = nombre1Ingles;
        this.nombre2 = nombre2;
        this.nombre2Ingles = nombre2Ingles;
        this.longitudOcupadaTabla = longitudOcupadaTabla;
        this.tipoCantidad = tipoCantidad;
        this.permitirCrearItem = permitirCrearItem;
        this.requiereOperacion = requiereOperacion;
        this.tipoItem = tipoItem;
        this.tipoIdentificador = tipoIdentificador;
        this.tipoImagen = tipoImagen;
        this.obligatoria = obligatoria;
        this.incluirProforma = incluirProforma;
        this.modificable = modificable;
        this.modificableSiNuevo = modificableSiNuevo;
        this.tipoValorTotal = tipoValorTotal;
        this.idTipoDato = idTipoDato;
        this.valores = valores;
        this.tipoDescuento = tipoDescuento;
        this.tipoDescripcionItem = tipoDescripcionItem;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Integer getIdColumna() {
        return idColumna;
    }

    public void setIdColumna(Integer idColumna) {
        this.idColumna = idColumna;
    }

    public TipoDatosDTO getIdTipoDato() {
        return idTipoDato;
    }

    public void setIdTipoDato(TipoDatosDTO idTipoDato) {
        this.idTipoDato = idTipoDato;
    }

    public Integer getDecimalesVisibles() {
        return decimalesVisibles;
    }

    public void setDecimalesVisibles(Integer decimalesVisibles) {
        this.decimalesVisibles = decimalesVisibles;
    }

    public Integer getIdOperacionColumna() {
        return idOperacionColumna;
    }

    public void setIdOperacionColumna(Integer idOperacionColumna) {
        this.idOperacionColumna = idOperacionColumna;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre1Ingles() {
        return nombre1Ingles;
    }

    public void setNombre1Ingles(String nombre1Ingles) {
        this.nombre1Ingles = nombre1Ingles;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombre2Ingles() {
        return nombre2Ingles;
    }

    public void setNombre2Ingles(String nombre2Ingles) {
        this.nombre2Ingles = nombre2Ingles;
    }

    public String getLongitudOcupadaTabla() {
        return longitudOcupadaTabla;
    }

    public void setLongitudOcupadaTabla(String longitudOcupadaTabla) {
        this.longitudOcupadaTabla = longitudOcupadaTabla;
    }

    public List<String> getValores() {
        return valores;
    }

    public void setValores(List<String> valores) {
        this.valores = valores;
    }

    public boolean isTipoValorTotal() {
        return tipoValorTotal;
    }

    public void setTipoValorTotal(boolean tipoValorTotal) {
        this.tipoValorTotal = tipoValorTotal;
    }

    public boolean isTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(boolean tipoItem) {
        this.tipoItem = tipoItem;
    }

    public boolean isTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(boolean tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public boolean isTipoIdentificador() {
        return tipoIdentificador;
    }

    public void setTipoIdentificador(boolean tipoIdentificador) {
        this.tipoIdentificador = tipoIdentificador;
    }

    public boolean isTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(boolean tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    public boolean isRequiereOperacion() {
        return requiereOperacion;
    }

    public void setRequiereOperacion(boolean requiereOperacion) {
        this.requiereOperacion = requiereOperacion;
    }

    public boolean isPermitirCrearItem() {
        return permitirCrearItem;
    }

    public void setPermitirCrearItem(boolean permitirCrearItem) {
        this.permitirCrearItem = permitirCrearItem;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public boolean isModificableSiNuevo() {
        return modificableSiNuevo;
    }

    public void setModificableSiNuevo(boolean modificableSiNuevo) {
        this.modificableSiNuevo = modificableSiNuevo;
    }

    public boolean isModificable() {
        return modificable;
    }

    public void setModificable(boolean modificable) {
        this.modificable = modificable;
    }

    public boolean isIncluirProforma() {
        return incluirProforma;
    }

    public void setIncluirProforma(boolean incluirProforma) {
        this.incluirProforma = incluirProforma;
    }

    public boolean isColumnaExiste() {
        return columnaExiste;
    }

    public void setColumnaExiste(boolean columnaExiste) {
        this.columnaExiste = columnaExiste;
    }

    public boolean isColumnaEnUso() {
        return columnaEnUso;
    }

    public void setColumnaEnUso(boolean columnaEnUso) {
        this.columnaEnUso = columnaEnUso;
    }

    public boolean isTipoCBM() {
        return tipoCBM;
    }

    public void setTipoCBM(boolean tipoCBM) {
        this.tipoCBM = tipoCBM;
    }

    public boolean isTipoValorUnitario() {
        return tipoValorUnitario;
    }

    public void setTipoValorUnitario(boolean tipoValorUnitario) {
        this.tipoValorUnitario = tipoValorUnitario;
    }

    public boolean isTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(boolean tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public boolean isTipoDescripcionItem() {
        return tipoDescripcionItem;
    }

    public void setTipoDescripcionItem(boolean tipoDescripcionItem) {
        this.tipoDescripcionItem = tipoDescripcionItem;
    }

    @Override
    public int compareTo(ColumnaProformaDTO o) {
        int i = this.getNombre1().compareTo(o.getNombre1());
        if (i == 0) {
            if (this.getNombre2() == null) {
                this.setNombre2("");
            }
            if (o.getNombre2() == null) {
                o.setNombre2("");
            }
            i = this.getNombre2().compareTo(o.getNombre2());
        }
        return i;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idColumna);
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
        final ColumnaProformaDTO other = (ColumnaProformaDTO) obj;
        if (!Objects.equals(this.idColumna, other.idColumna)) {
            return false;
        }
        return true;
    }
}
