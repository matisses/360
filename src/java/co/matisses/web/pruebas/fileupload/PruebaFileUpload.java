package co.matisses.web.pruebas.fileupload;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 *
 * @author dbotero
 */
@ConversationScoped
@Named(value = "pruebaFileUpload")
public class PruebaFileUpload implements Serializable {

    private static final Logger log = Logger.getLogger(PruebaFileUpload.class.getSimpleName());

    private Part archivo;

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
            }
        }
        return null;
    }

    public String upload() throws IOException {
        InputStream inputStream = archivo.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(getFileName(archivo));
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while (true) {
            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            } else {
                break;
            }
        }
        outputStream.close();
        inputStream.close();

        return null;
    }
}
