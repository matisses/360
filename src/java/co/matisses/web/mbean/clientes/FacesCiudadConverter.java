package co.matisses.web.mbean.clientes;

import co.matisses.web.dto.CiudadDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author dbotero
 */
@FacesConverter("ciudadConverter")
public class FacesCiudadConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println("Convirtiendo el string " + value + " a CiudadDTO");
        if (value != null && value.trim().length() > 0) {
            BaruApplicationMBean applicationBean = fc.getApplication().evaluateExpressionGet(fc, "#{baruApplicationBean}", BaruApplicationMBean.class);
            //BaruApplicationMBean applicationBean = (BaruApplicationMBean) fc.getExternalContext().getApplicationMap().get("baruApplicationBean");
            return applicationBean.obtenerCiudad(value);
            //return CiudadDTO.valueOf(value);
        } else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof String) {
                return (String) object;
            } else if (object instanceof CiudadDTO) {
                return String.valueOf(((CiudadDTO) object).getNombre());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
