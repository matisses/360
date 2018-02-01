package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class PosicionEmpleadoDTO {

    private Integer posID;
    private String name;
    private String descriptio;
    private Character locFields;

    public PosicionEmpleadoDTO() {
    }

    public PosicionEmpleadoDTO(Integer posID, String name, String descriptio, Character locFields) {
        this.posID = posID;
        this.name = name;
        this.descriptio = descriptio;
        this.locFields = locFields;
    }

    public Integer getPosID() {
        return posID;
    }

    public void setPosID(Integer posID) {
        this.posID = posID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptio() {
        return descriptio;
    }

    public void setDescriptio(String descriptio) {
        this.descriptio = descriptio;
    }

    public Character getLocFields() {
        return locFields;
    }

    public void setLocFields(Character locFields) {
        this.locFields = locFields;
    }
}
