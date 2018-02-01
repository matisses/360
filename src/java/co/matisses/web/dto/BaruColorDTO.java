package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BaruColorDTO {

    private String code;
    private String name;
    private String colorGenerico;
    private String codigoHexa;
    private boolean seleccionado;
    private boolean principal;

    public BaruColorDTO() {
    }

    public BaruColorDTO(String code, String name, String colorGenerico, String codigoHexa) {
        this.code = code;
        this.name = name;
        this.colorGenerico = colorGenerico;
        this.codigoHexa = codigoHexa;
        this.seleccionado = false;
        this.principal = false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorGenerico() {
        return colorGenerico;
    }

    public void setColorGenerico(String colorGenerico) {
        this.colorGenerico = colorGenerico;
    }

    public String getCodigoHexa() {
        return codigoHexa;
    }

    public void setCodigoHexa(String codigoHexa) {
        this.codigoHexa = codigoHexa;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }
}
