package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ServiceCallTypesDTO {

    private Integer callTypeId;
    private String name;
    private String description;

    public ServiceCallTypesDTO() {
    }

    public ServiceCallTypesDTO(Integer callTypeId, String name, String description) {
        this.callTypeId = callTypeId;
        this.name = name;
        this.description = description;
    }

    public Integer getCallTypeId() {
        return callTypeId;
    }

    public void setCallTypeId(Integer callTypeId) {
        this.callTypeId = callTypeId;
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
}
