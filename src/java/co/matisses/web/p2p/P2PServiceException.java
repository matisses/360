package co.matisses.web.p2p;

/**
 *
 * @author dbotero
 */
public class P2PServiceException extends Exception {

    public P2PServiceException() {
    }

    public P2PServiceException(String message) {
        super(message);
    }

    public P2PServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
