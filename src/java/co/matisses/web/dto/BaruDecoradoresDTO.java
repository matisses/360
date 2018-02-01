package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BaruDecoradoresDTO {

    private String code;
    private String name;
    private String uNit;
    private String uFechaIngreso;
    private String uEstado;

    public BaruDecoradoresDTO() {
    }

    public BaruDecoradoresDTO(String code, String name, String uNit, String uFechaIngreso, String uEstado) {
        this.code = code;
        this.name = name;
        this.uNit = uNit;
        this.uFechaIngreso = uFechaIngreso;
        this.uEstado = uEstado;
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

    public String getuNit() {
        return uNit;
    }

    public void setuNit(String uNit) {
        this.uNit = uNit;
    }

    public String getuFechaIngreso() {
        return uFechaIngreso;
    }

    public void setuFechaIngreso(String uFechaIngreso) {
        this.uFechaIngreso = uFechaIngreso;
    }

    public String getuEstado() {
        return uEstado;
    }

    public void setuEstado(String uEstado) {
        this.uEstado = uEstado;
    }
}
