package co.matisses.web.p2p;

import co.matisses.web.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dbotero
 */
public class P2PServiceConnector {

    private static final Logger log = Logger.getLogger(P2PServiceConnector.class.getSimpleName());

    public static <T> T postPlaceToPayRequest(String serviceUrl, String serviceOperation, Object inputObject, Class<T> type) throws P2PServiceException {
        try {
            URL url = new URL(serviceUrl + serviceOperation);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(inputObject);
            log.log(Level.INFO, "Enviando datos a placetopay {0}{1} - {2}", new Object[]{serviceUrl, serviceOperation, input});

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));

            //OutputStream os = conn.getOutputStream();
            writer.write(input);
            writer.close();
            wr.close();
            //os.write(input.getBytes());
            //os.flush();
            log.log(Level.INFO, "Respuesta recibida de placetopay: ({0}) {1}", new Object[]{conn.getResponseCode(), conn.getResponseMessage()});
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                log.log(Level.SEVERE, "Ocurrio un error al invocar el servicio de redireccion de placetopay. ({0}) {1}",
                        new Object[]{conn.getResponseCode(), conn.getResponseMessage()});
                throw new P2PServiceException("La llamada al servicio genero un error. (" + conn.getResponseCode() + ") " + conn.getResponseMessage());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            log.log(Level.INFO, "Se recibio la respuesta de placetopay. {0}", sb.toString());
            try {
                return type.cast(mapper.readValue(sb.toString(), type));
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al procesar el valor retornado por placetopay. ", e);
                throw new P2PServiceException("Ocurrio un error al procesar el valor retornado por placetopay. ", e);
            } finally {
                conn.disconnect();
            }
        } catch (IOException ex) {
            throw new P2PServiceException("Ocurrio un error al ejecutar el servicio. ", ex);
        }
    }

    public static void main(String[] args) throws P2PServiceException {
        String login = "cae610895dd2d726eff2bfe810065379";
        String tranKey = "0xoLWzKu";
        TransactionStatusResponseDTO response = P2PServiceConnector.postPlaceToPayRequest("https://test.placetopay.com/redirection/api/", "session/2835", new TransactionStatusRequestDTO(login, tranKey), TransactionStatusResponseDTO.class);
        System.out.println(ObjectUtils.toString(response));
    }
}
