package co.matisses.web.ws;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author dbotero
 */
@Stateless
public class BonoRegaloWS {

    public static String crearBono(String semilla) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] passBytes = semilla.getBytes();
        md.reset();
        byte[] digested = md.digest(passBytes);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < digested.length; i++) {
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        return sb.toString();
        //System.out.println(sb.toString());
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(crearBono("danielbotero835688119840210"));
    }
}
