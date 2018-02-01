package co.matisses.web.p2p;

/**
 *
 * @author dbotero
 */
public class TransactionStatusRequestDTO {

    private P2PWSAuthentication auth;
    private String internalReference;

    public TransactionStatusRequestDTO(String login, String tranKey) {
        this.auth = new P2PWSAuthentication(login, tranKey);
    }

    public TransactionStatusRequestDTO(String login, String tranKey, String internalReference) {
        this.auth = new P2PWSAuthentication(login, tranKey);
        this.internalReference = internalReference;
    }

    public P2PWSAuthentication getAuth() {
        return auth;
    }

    public String getInternalReference() {
        return internalReference;
    }

    public void setInternalReference(String internalReference) {
        this.internalReference = internalReference;
    }
}
