package co.matisses.web.mbean.session;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@SessionScoped
@Named(value = "conteosSessionMBean")
public class ConteosSessionMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ConteosSessionMBean.class.getSimpleName());
    private Integer idConteo;

    public ConteosSessionMBean() {
    }

    @PostConstruct
    protected void initialize() {
    }

    public Integer getIdConteo() {
        return idConteo;
    }

    public void setIdConteo(Integer idConteo) {
        this.idConteo = idConteo;
    }
}
