package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ServiceCallProblemSubtypeDTO {

    private Integer proSubTyId;
    private String name;
    private String description;

    public ServiceCallProblemSubtypeDTO() {
    }

    public ServiceCallProblemSubtypeDTO(Integer proSubTyId, String name, String description) {
        this.proSubTyId = proSubTyId;
        this.name = name;
        this.description = description;
    }

    public Integer getProSubTyId() {
        return proSubTyId;
    }

    public void setProSubTyId(Integer proSubTyId) {
        this.proSubTyId = proSubTyId;
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
