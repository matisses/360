package co.matisses.web.p2p;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

public class P2PWSAuthentication {

    protected String login;
    protected String tranKey;
    protected String seed;
    protected String nonce;

    public P2PWSAuthentication(String login, String tranKey) {
        this.login = login;
        this.tranKey = tranKey;
        this.generate();
    }

    public final P2PWSAuthentication generate() {
        this.nonce = new BigInteger(130, new SecureRandom()).toString();
        this.seed = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault())).format(new Date());
        return this;
    }

    public String getLogin() {
        return this.login;
    }

    public String getTranKey() {
        try {
            return base64(sha1(nonce + seed + tranKey));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public String getSeed() {
        return this.seed;
    }

    public String getNonce() {
        return base64(nonce.getBytes());
    }

    static byte[] sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        return mDigest.digest(input.getBytes());
    }

    static String base64(byte[] input) {
        byte[] encodedBytes = (Base64.getEncoder()).encode(input);
        return new String(encodedBytes);
    }

    public static void main(String[] args) {
        P2PWSAuthentication a = new P2PWSAuthentication("c1f6fa952c077932c5224dcb2730a782", "5IOK23Ei");
        System.out.println("login\t" + a.getLogin());
        System.out.println("seed\t" + a.getSeed());
        System.out.println("nonce\t" + a.getNonce());
        System.out.println("trankey\t" + a.getTranKey());
    }
}
