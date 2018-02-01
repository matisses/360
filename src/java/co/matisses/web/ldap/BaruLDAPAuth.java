package co.matisses.web.ldap;

import co.matisses.web.mbean.BaruApplicationMBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author dbotero
 */
@Stateless
public class BaruLDAPAuth {

    private static final Logger log = Logger.getLogger(BaruLDAPAuth.class.getSimpleName());
    @Inject
    private BaruApplicationMBean aplicacionBean;

    public BaruLDAPAuth() {
    }
    
    private String getProp(String key){
        return aplicacionBean.obtenerValorPropiedad(key);
    }

    public boolean authenticateUser(String username, String password) {
        Hashtable<String, Object> auth = new Hashtable<>();
        auth.put(Context.INITIAL_CONTEXT_FACTORY, getProp("initial.context.factory"));
        auth.put(Context.PROVIDER_URL, getProp("provider.url"));
        auth.put(Context.SECURITY_AUTHENTICATION, getProp("security.authentication"));
        auth.put(Context.SECURITY_PRINCIPAL, getProp("security.principal.domain") + username);
        auth.put(Context.SECURITY_CREDENTIALS, password);

        try {
            // Create the initial context
            DirContext ctx = new InitialDirContext(auth);
            log.log(Level.INFO, "Usuario [{0}] autenticado.", username);
            return true;
        } catch (NamingException ex) {
            log.log(Level.SEVERE, "Error autenticando al usuario. {0}", ex.getMessage());
            String mensaje = ex.getMessage();
            String valor = mensaje.substring(mensaje.indexOf("data") + 5, mensaje.indexOf("data") + 8);
            switch (valor) {
                case "525":
                    //525 - user not found
                    log.log(Level.SEVERE, "El usuario no existe. ");
                    break;
                case "52e":
                    //52e - invalid credentials
                    log.log(Level.SEVERE, "Clave invalida. ");
                    break;
                case "530":
                    //530 - not permitted to logon at this time
                    log.log(Level.SEVERE, "No fue posible iniciar en este momento, intente de nuevo mas tarde. ");
                    break;
                case "532":
                    //532 - password expired
                    log.log(Level.SEVERE, "La clave ha expirado. ");
                    break;
                case "533":
                    //533 - account disabled
                    log.log(Level.SEVERE, "Cuenta deshabilitada. ");
                    break;
                case "701":
                    //701 - account expired
                    log.log(Level.SEVERE, "La cuenta ha expirado. ");
                    break;
                case "773":
                    //773 - user must reset password
                    log.log(Level.SEVERE, "Debe restablecer su contraseña antes de iniciar sesion. ");
                    break;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error no identificado al validar el usuario contra el directorio activo. ", e);
        }
        return false;
    }

    public List<String> listEmployees() {
        List<String> usuarios = new ArrayList<>();

        Hashtable<String, Object> auth = new Hashtable<>();
        auth.put(Context.INITIAL_CONTEXT_FACTORY, getProp("initial.context.factory"));
        auth.put(Context.PROVIDER_URL, getProp("provider.url"));
        auth.put(Context.SECURITY_AUTHENTICATION, getProp("security.authentication"));
        auth.put(Context.SECURITY_PRINCIPAL, getProp("security.principal.domain") + getProp("ldap.query.user"));
        auth.put(Context.SECURITY_CREDENTIALS, getProp("ldap.query.password"));

        String usersContainer = "OU=Mail_matisses,DC=baru,DC=local";

        try {
            LdapContext ctx = new InitialLdapContext(auth, null);

            DirContext ctx1 = new InitialDirContext(auth);
            SearchControls ctls = new SearchControls();
            String[] attrIDs = {"distinguishedName", "cn", "name", "uid", "sn", "givenname", "memberOf", "samaccountname", "userPrincipalName", "description", "sAMAccountName"};

            ctls.setReturningAttributes(attrIDs);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration answer = ctx1.search(usersContainer, "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=2)))", ctls);

            while (answer.hasMore()) {
                SearchResult rslt = (SearchResult) answer.next();
                Attributes attrs = rslt.getAttributes();
                Attribute usuario = attrs.get("sAMAccountName");
                if (usuario != null) {
                    Attribute memberOf = attrs.get("memberOf");
                    if (memberOf != null && memberOf.contains("CN=empleados,CN=Users,DC=baru,DC=local")) {
                        usuarios.add((String) usuario.get());
                    }
                }
                ctx1.close();
            }
            Collections.sort(usuarios);
        } catch (NamingException nex) {
            log.log(Level.SEVERE, "Ocurrio un error al iniciar la conexion LDAP. ", nex);
        }

        return usuarios;
    }

    public List<String> listOtherUsers() {
        List<String> usuarios = new ArrayList<>();

        Hashtable<String, Object> auth = new Hashtable<>();
        auth.put(Context.INITIAL_CONTEXT_FACTORY, getProp("initial.context.factory"));
        auth.put(Context.PROVIDER_URL, getProp("provider.url"));
        auth.put(Context.SECURITY_AUTHENTICATION, getProp("security.authentication"));
        auth.put(Context.SECURITY_PRINCIPAL, getProp("security.principal.domain") + getProp("ldap.query.user"));
        auth.put(Context.SECURITY_CREDENTIALS, getProp("ldap.query.password"));

        String usersContainer = "OU=externo,DC=baru,DC=local";

        try {
            LdapContext ctx = new InitialLdapContext(auth, null);

            DirContext ctx1 = new InitialDirContext(auth);
            SearchControls ctls = new SearchControls();
            String[] attrIDs = {"distinguishedName", "cn", "name", "uid", "sn", "givenname", "memberOf", "samaccountname", "userPrincipalName", "description", "sAMAccountName"};

            ctls.setReturningAttributes(attrIDs);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration answer = ctx1.search(usersContainer, "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=2)))", ctls);

            while (answer.hasMore()) {
                SearchResult rslt = (SearchResult) answer.next();
                Attributes attrs = rslt.getAttributes();
                Attribute usuario = attrs.get("sAMAccountName");
                if (usuario != null) {
                    //Attribute memberOf = attrs.get("memberOf");
                    //if (memberOf != null && memberOf.contains("CN=empleados,CN=Users,DC=baru,DC=local")) {
                    usuarios.add((String) usuario.get());
                    //}
                }
                ctx1.close();
            }
            Collections.sort(usuarios);
        } catch (NamingException nex) {
            log.log(Level.SEVERE, "Ocurrio un error al iniciar la conexion LDAP. ", nex);
        }

        return usuarios;
    }
}
