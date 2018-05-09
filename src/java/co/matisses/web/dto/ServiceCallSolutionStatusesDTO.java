package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ServiceCallSolutionStatusesDTO {

    private Long number;
    private String name;
    private String description;

    public ServiceCallSolutionStatusesDTO() {
    }

    public ServiceCallSolutionStatusesDTO(Long number, String name, String description) {
        this.number = number;
        this.name = name;
        this.description = description;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
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
