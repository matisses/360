package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class MotivoCancelacionDTO {

    private Integer reasonID;
    private String name;
    private String descriptio;

    public MotivoCancelacionDTO() {
    }

    public MotivoCancelacionDTO(Integer reasonID, String name, String descriptio) {
        this.reasonID = reasonID;
        this.name = name;
        this.descriptio = descriptio;
    }

    public Integer getReasonID() {
        return reasonID;
    }

    public void setReasonID(Integer reasonID) {
        this.reasonID = reasonID;
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
}
