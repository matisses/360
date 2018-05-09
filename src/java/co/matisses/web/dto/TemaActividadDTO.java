package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class TemaActividadDTO {

    private Integer type;
    private Long code;
    private Long userSign;
    private String name;
    private Character dataSource;
    private Character active;

    public TemaActividadDTO() {
    }

    public TemaActividadDTO(Integer type, Long code, Long userSign, String name, Character dataSource, Character active) {
        this.type = type;
        this.code = code;
        this.userSign = userSign;
        this.name = name;
        this.dataSource = dataSource;
        this.active = active;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }
}
