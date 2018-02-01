package co.matisses.web.custom;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.component.fileupload.FileUploadRenderer;

/**
 *
 * @author ygil
 */
public class MyFileUploadRenderer extends FileUploadRenderer {

    @Override
    public void decode(FacesContext context, UIComponent component) {
        System.out.println("Inicia FileUploadRenderer");
        if (context.getExternalContext().getRequestContentType().toLowerCase().startsWith("multipart/")) {
            System.out.println("Entre a decode");
            super.decode(context, component);
        }
    }
}
