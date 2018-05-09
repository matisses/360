package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ServiceCallProblemTypesDTO {

    private Integer problemTypeId;
    private String name;
    private String description;

    public ServiceCallProblemTypesDTO() {
    }

    public ServiceCallProblemTypesDTO(Integer problemTypeId, String name, String description) {
        this.problemTypeId = problemTypeId;
        this.name = name;
        this.description = description;
    }

    public Integer getProblemTypeId() {
        return problemTypeId;
    }

    public void setProblemTypeId(Integer problemTypeId) {
        this.problemTypeId = problemTypeId;
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
