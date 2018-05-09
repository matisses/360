package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class LugarReunionDTO {

    private Long code;
    private Long userSign;
    private String name;
    private Character dataSource;
    private Character locked;

    public LugarReunionDTO() {
    }

    public LugarReunionDTO(Long code, Long userSign, String name, Character dataSource, Character locked) {
        this.code = code;
        this.userSign = userSign;
        this.name = name;
        this.dataSource = dataSource;
        this.locked = locked;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getUserSign() {
        return userSign;
    }

    public void setUserSign(Long userSign) {
        this.userSign = userSign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getDataSource() {
        return dataSource;
    }

    public void setDataSource(Character dataSource) {
        this.dataSource = dataSource;
    }

    public Character getLocked() {
        return locked;
    }

    public void setLocked(Character locked) {
        this.locked = locked;
    }
}
