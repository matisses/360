package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BaruMaterialesDTO {

    private Integer cantidadAverias;
    private String code;
    private String name;
    private String cuidados;
    private String uParte;
    private String uNombreWeb;
    private boolean seleccionado;

    public BaruMaterialesDTO() {
    }

    public BaruMaterialesDTO(String code, String name, String cuidados) {
        this.code = code;
        this.name = name;
        this.cuidados = cuidados;
    }

    public BaruMaterialesDTO(String code, String name, String cuidados, String uParte, String uNombreWeb) {
        this.code = code;
        this.name = name;
        this.cuidados = cuidados;
        this.uParte = uParte;
        this.uNombreWeb = uNombreWeb;
        this.seleccionado = false;
    }

    public BaruMaterialesDTO(Integer cantidadAverias, String code, String name, String cuidados) {
        this.cantidadAverias = cantidadAverias;
        this.code = code;
        this.name = name;
        this.cuidados = cuidados;
    }

    public Integer getCantidadAverias() {
        return cantidadAverias;
    }

    public void setCantidadAverias(Integer cantidadAverias) {
        this.cantidadAverias = cantidadAverias;
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

    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    public String getuParte() {
        return uParte;
    }

    public void setuParte(String uParte) {
        this.uParte = uParte;
    }

    public String getuNombreWeb() {
        return uNombreWeb;
    }

    public void setuNombreWeb(String uNombreWeb) {
        this.uNombreWeb = uNombreWeb;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
