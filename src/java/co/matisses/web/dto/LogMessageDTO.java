package co.matisses.web.dto;

import java.io.Serializable;
import java.util.logging.Level;

/**
 *
 * @author dbotero
 */
public class LogMessageDTO implements Serializable {

    private Level level;
    private String username;
    private String className;
    private String message;
    private Object param;
    private Object[] params;
    private Throwable thrown;

    public LogMessageDTO() {
    }

    public LogMessageDTO(Level level, String username, String className, String message) {
        this.level = level;
        this.username = username;
        this.className = className;
        this.message = message;
    }

    public LogMessageDTO(Level level, String username, String className, String message, Object param) {
        this.level = level;
        this.username = username;
        this.className = className;
        this.message = message;
        this.param = param;
    }

    public LogMessageDTO(Level level, String username, String className, String message, Object[] params) {
        this.level = level;
        this.username = username;
        this.className = className;
        this.message = message;
        this.params = params;
    }

    public LogMessageDTO(Level level, String username, String className, Throwable thrown) {
        this.level = level;
        this.username = username;
        this.className = className;
        this.thrown = thrown;
    }

    public LogMessageDTO(Level level, String username, String className, String message, Throwable thrown) {
        this.level = level;
        this.username = username;
        this.className = className;
        this.message = message;
        this.thrown = thrown;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getThrown() {
        return thrown;
    }

    public void setThrown(Throwable thrown) {
        this.thrown = thrown;
    }

    @Override
    public String toString() {
        return "LogMessageDTO{" + "level=" + level + ", username=" + username + ", className=" + className + ", message=" + message + ", param=" + param + ", params=" + params + ", thrown=" + thrown + '}';
    }
}
