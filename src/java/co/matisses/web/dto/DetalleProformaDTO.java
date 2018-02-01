package co.matisses.web.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author ygil
 */
public class DetalleProformaDTO {

    private static final Locale LOCALE_MX = new Locale("es", "MX");
    private static final DecimalFormat FORMATO = (DecimalFormat) NumberFormat.getInstance(LOCALE_MX);
    private Integer idDetalleProforma;
    private Integer idConfiguracion;
    private Integer lineNum;
    private Integer decimalesVisibles;
    private String valor;
    private String longitudOcupadaTabla;
    private String tipoDato;
    private boolean tipoImagen = false;
    private boolean lineaNueva = false;
    private ColumnaProformaDTO columna;

    public DetalleProformaDTO() {
    }

    public DetalleProformaDTO(Integer idDetalleProforma, Integer idConfiguracion, Integer lineNum, String valor) {
        this.idDetalleProforma = idDetalleProforma;
        this.idConfiguracion = idConfiguracion;
        this.lineNum = lineNum;
        this.valor = valor;
    }

    public DetalleProformaDTO(Integer idDetalleProforma, Integer idConfiguracion, Integer lineNum, Integer decimalesVisibles,
            String valor, String longitudOcupadaTabla, String tipoDato, boolean tipoImagen, boolean lineaNueva, ColumnaProformaDTO columna) {
        this.idDetalleProforma = idDetalleProforma;
        this.idConfiguracion = idConfiguracion;
        this.lineNum = lineNum;
        this.decimalesVisibles = decimalesVisibles;
        this.valor = valor;
        this.longitudOcupadaTabla = longitudOcupadaTabla;
        this.tipoDato = tipoDato;
        this.tipoImagen = tipoImagen;
        this.lineaNueva = lineaNueva;
        this.columna = columna;
    }

    public Integer getIdDetalleProforma() {
        return idDetalleProforma;
    }

    public void setIdDetalleProforma(Integer idDetalleProforma) {
        this.idDetalleProforma = idDetalleProforma;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public void setValorFormateado(String valor) {
        this.valor = valor;
    }

    public String getValorFormateado() {
        if (tipoDato != null) {
            switch (tipoDato) {
                case "String":
                    return valor;
                case "Integer":
                    try {
                        return FORMATO.format(NumberFormat.getNumberInstance(LOCALE_MX).parse(valor));
                    } catch (Exception e) {
                        return valor;
                    }
                case "Double":
                    try {
                        NumberFormat format = NumberFormat.getInstance(LOCALE_MX);
                        if (decimalesVisibles != null && decimalesVisibles > 0) {
                            //format.setMaximumFractionDigits(decimalesVisibles);
                        }
                        return FORMATO.format(format.parse(valor));
                    } catch (Exception e) {
                        return valor;
                    }
                default:
            }
        }
        return valor;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public Integer getDecimalesVisibles() {
        return decimalesVisibles;
    }

    public void setDecimalesVisibles(Integer decimalesVisibles) {
        this.decimalesVisibles = decimalesVisibles;
    }

    public String getLongitudOcupadaTabla() {
        return longitudOcupadaTabla;
    }

    public void setLongitudOcupadaTabla(String longitudOcupadaTabla) {
        this.longitudOcupadaTabla = longitudOcupadaTabla;
    }

    public boolean isTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(boolean tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public boolean isLineaNueva() {
        return lineaNueva;
    }

    public void setLineaNueva(boolean lineaNueva) {
        this.lineaNueva = lineaNueva;
    }

    public ColumnaProformaDTO getColumna() {
        return columna;
    }

    public void setColumna(ColumnaProformaDTO columna) {
        this.columna = columna;
    }
}
