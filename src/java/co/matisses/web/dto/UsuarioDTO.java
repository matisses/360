package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class UsuarioDTO {

    private Integer userID;
    private String userCode;
    private String name;
    private Character Locked;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer userID, String userCode, Character Locked) {
        this.userID = userID;
        this.userCode = userCode;
        this.Locked = Locked;
    }

    public UsuarioDTO(Integer userID, String userCode, String name, Character Locked) {
        this.userID = userID;
        this.userCode = userCode;
        this.name = name;
        this.Locked = Locked;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getLocked() {
        return Locked;
    }

    public void setLocked(Character Locked) {
        this.Locked = Locked;
    }
}
