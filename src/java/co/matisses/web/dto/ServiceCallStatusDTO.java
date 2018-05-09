package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ServiceCallStatusDTO {

    private Integer statusId;
    private String name;
    private String description;
    private Character locked;

    public ServiceCallStatusDTO() {
    }

    public ServiceCallStatusDTO(Integer statusId, String name, String description, Character locked) {
        this.statusId = statusId;
        this.name = name;
        this.description = description;
        this.locked = locked;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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
