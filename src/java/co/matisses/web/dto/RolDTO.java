package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class RolDTO {

    private Integer typeID;
    private String name;
    private String descriptio;
    private Character locked;

    public RolDTO() {
    }

    public RolDTO(Integer typeID, String name, String descriptio, Character locked) {
        this.typeID = typeID;
        this.name = name;
        this.descriptio = descriptio;
        this.locked = locked;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
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
