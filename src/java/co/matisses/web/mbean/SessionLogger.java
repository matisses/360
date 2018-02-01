package co.matisses.web.mbean;

//import co.matisses.web.dto.LogMessageDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;

/**
 *
 * @author dbotero
 */
public class SessionLogger {

    private static final Logger log = Logger.getLogger(SessionLogger.class.getSimpleName());
    private String className;
    private String username;
    private QueueConnection connection;
    private QueueSession session;
    private MessageProducer producer;

    //@Resource(lookup = "java:/JMS360/JMS360ConnectionFactory")
    private ConnectionFactory connectionFactory;
    //@Resource(lookup = "java:/JMS360/Log360")
    private Destination destination;

    public SessionLogger(String className) {
        this.className = className;
        //initialize();
    }

    protected void initialize() {
        try {
            connection = (QueueConnection) connectionFactory.createConnection();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(destination);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible inicializar los parametros para la cola de log. ", e);
        }
    }

    protected void destroy() {
        try {
            producer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible finalizar los parametros para la cola de log. ", e);
        }
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public void log(Level level, String msg) {
//        LogMessageDTO dto = new LogMessageDTO(level, username, className, msg);
//        sendMessage(dto);
//    }
//
//    public void log(Level level, String msg, Object param) {
//        LogMessageDTO dto = new LogMessageDTO(level, username, className, msg, param);
//        sendMessage(dto);
//    }
//
//    public void log(Level level, String msg, Object[] params) {
//        LogMessageDTO dto = new LogMessageDTO(level, username, className, msg, params);
//        sendMessage(dto);
//    }
//
//    public void info(String msg) {
//        LogMessageDTO dto = new LogMessageDTO(Level.INFO, username, className, msg);
//        sendMessage(dto);
//    }
//
//    public void info(String msg, Object param) {
//        LogMessageDTO dto = new LogMessageDTO(Level.INFO, username, className, msg, param);
//        sendMessage(dto);
//    }
//
//    public void info(String msg, Object[] params) {
//        LogMessageDTO dto = new LogMessageDTO(Level.INFO, username, className, msg, params);
//        sendMessage(dto);
//    }
//
//    public void warning(String msg) {
//        LogMessageDTO dto = new LogMessageDTO(Level.WARNING, username, className, msg);
//        sendMessage(dto);
//    }
//
//    public void severe(String msg) {
//        LogMessageDTO dto = new LogMessageDTO(Level.SEVERE, username, className, msg);
//        sendMessage(dto);
//    }
//
//    public void severe(Throwable thrown) {
//        LogMessageDTO dto = new LogMessageDTO(Level.SEVERE, username, className, thrown);
//        sendMessage(dto);
//    }
//
//    public void severe(String msg, Throwable thrown) {
//        LogMessageDTO dto = new LogMessageDTO(Level.SEVERE, username, className, msg, thrown);
//        sendMessage(dto);
//    }

//    private void sendMessage(LogMessageDTO dto) {
//        try {
//            ObjectMessage message = session.createObjectMessage(dto);
//            producer.send(message);
//        } catch (Exception e) {
//            log.log(Level.SEVERE, dto.getMessage(), dto.getParams());
//            log.log(Level.SEVERE, "No fue posible escribir el mensaje en la cola JMS. ", e);
//        }
//    }
}
