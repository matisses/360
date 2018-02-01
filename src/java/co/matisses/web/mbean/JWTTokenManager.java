package co.matisses.web.mbean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ApplicationScoped
@Named("jwtTokenManager")
public class JWTTokenManager implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(JWTTokenManager.class.getSimpleName());
    @Inject
    private BaruApplicationMBean applicationBean;

    public String tokenizeData(String username, String name) {
        try {
            GregorianCalendar expTime = new GregorianCalendar();
            expTime.add(Calendar.MINUTE, Integer.parseInt(applicationBean.obtenerValorPropiedad("jwt.exp")));
            Algorithm algorithm = Algorithm.HMAC256(applicationBean.obtenerValorPropiedad("jwt.secret"));
            String token = JWT.create()
                    .withIssuedAt(new Date())
                    .withExpiresAt(expTime.getTime())
                    .withIssuer("auth0")
                    .withClaim("username", username)
                    .withClaim("name", name)
                    .withClaim("admin", true)
                    .sign(algorithm);
            CONSOLE.log(Level.INFO, "Token generado: {0}", token);
            return token;
        } catch (UnsupportedEncodingException | JWTCreationException e) {
            //UnsupportedEncodingException: UTF-8 encoding not supported
            //JWTCreationException: Invalid Signing configuration / Couldn't convert Claims.
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el token JWT. ", e);
        }
        return null;
    }
}
