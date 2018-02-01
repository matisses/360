package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class EquipoDTO {

    private Integer teamID;
    private String name;
    private String descriptio;

    public EquipoDTO() {
    }

    public EquipoDTO(Integer teamID, String name, String descriptio) {
        this.teamID = teamID;
        this.name = name;
        this.descriptio = descriptio;
    }

    public Integer getTeamID() {
        return teamID;
    }

    public void setTeamID(Integer teamID) {
        this.teamID = teamID;
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
