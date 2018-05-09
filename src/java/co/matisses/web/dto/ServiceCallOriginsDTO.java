package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ServiceCallOriginsDTO {

    private Integer originId;
    private String name;
    private String description;
    private Character locked;

    public ServiceCallOriginsDTO() {
    }

    public ServiceCallOriginsDTO(Integer originId, String name, String description, Character locked) {
        this.originId = originId;
        this.name = name;
        this.description = description;
        this.locked = locked;
    }

    public Integer getOriginId() {
        return originId;
    }

    public void setOriginId(Integer originId) {
        this.originId = originId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getLocked() {
        return locked;
    }

    public void setLocked(Character locked) {
        this.locked = locked;
    }
}
