package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BaruAveriasMaterialDTO {

    private String code;
    private String name;
    private String uMaterial;
    private String uProblema;

    public BaruAveriasMaterialDTO() {
    }

    public BaruAveriasMaterialDTO(String code, String name, String uMaterial, String uProblema) {
        this.code = code;
        this.name = name;
        this.uMaterial = uMaterial;
        this.uProblema = uProblema;
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

    public String getUMaterial() {
        return uMaterial;
    }

    public void setUMaterial(String uMaterial) {
        this.uMaterial = uMaterial;
    }

    public String getUProblema() {
        return uProblema;
    }

    public void setUProblema(String uProblema) {
        this.uProblema = uProblema;
    }
}
