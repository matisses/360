package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ClaseFormacionDTO {

    private Integer edType;
    private String name;
    private String descriptio;
    private Character locked;

    public ClaseFormacionDTO() {
    }

    public ClaseFormacionDTO(Integer edType, String name, String descriptio, Character locked) {
        this.edType = edType;
        this.name = name;
        this.descriptio = descriptio;
        this.locked = locked;
    }

    public Integer getEdType() {
        return edType;
    }

    public void setEdType(Integer edType) {
        this.edType = edType;
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

    public Character getLocked() {
        return locked;
    }

    public void setLocked(Character locked) {
        this.locked = locked;
    }
}
