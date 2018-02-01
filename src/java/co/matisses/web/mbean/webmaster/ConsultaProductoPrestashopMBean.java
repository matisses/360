package co.matisses.web.mbean.webmaster;

import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.web.dto.AtributoItemDTO;
import co.matisses.web.dto.InventoryItemDTO;
import co.matisses.web.dto.ItemColorDTO;
import co.matisses.web.dto.ItemMaterialDTO;
import co.matisses.web.dto.ResultadoBusquedaPrestashopDTO;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@ConversationScoped
@Named(value = "consultaProductoPrestashopMBean")
public class ConsultaProductoPrestashopMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ConsultaProductoPrestashopMBean.class.getSimpleName());
    @EJB
    private ItemInventarioFacade itemFacade;
    private Map<String, Map<String, String>> valores;
    private List<ResultadoBusquedaPrestashopDTO> atributos;

    private String busqueda;

    public ConsultaProductoPrestashopMBean() {
        valores = new HashMap<>();
        atributos = new ArrayList<>();
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public Map<String, Map<String, String>> getValores() {
        return valores;
    }

    public void setValores(Map<String, Map<String, String>> valores) {
        this.valores = valores;
    }

    public List<ResultadoBusquedaPrestashopDTO> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<ResultadoBusquedaPrestashopDTO> atributos) {
        this.atributos = atributos;
    }

    public List<Map.Entry<String, Map<String, String>>> obtenerLista() {
        return new ArrayList<>(valores.entrySet());
    }

    public void buscar() {
        atributos = new ArrayList<>();
        log.log(Level.INFO, "Ingreso al metodo buscar");
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            log.log(Level.INFO, "Buscando [{0}]", busqueda);
            if (esReferenciaCompleta(busqueda) || esReferenciaCorta(busqueda)) {
                realizarBusquedaReferencia(busqueda);
            } else {
                realizarBusquedaModelo(busqueda);
            }
        } else {
            log.log(Level.INFO, "El metodo no recibio una cadena valida para realizar la busqueda");
        }
        log.log(Level.INFO, "Luego de la busqueda, exiten [{0}] registros para mostrar", obtenerLista().size());
    }

    private boolean esReferenciaCorta(String referencia) {
        if (referencia.length() > 8 && referencia.length() < 6) {
            return false;
        }
        if (referencia.contains("*")) {
            if (referencia.substring(0, referencia.indexOf("*")).length() == 3) {
                if (referencia.substring(referencia.indexOf("*")).length() >= 2) {
                    return true;
                }
            }
        } else if (referencia.contains(".")) {
            if (referencia.substring(0, referencia.indexOf(".")).length() == 3) {
                if (referencia.substring(referencia.indexOf(".")).length() >= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean esReferenciaCompleta(String referencia) {
        if (referencia.length() == 20 && referencia.contains("000000000000")) {
            return true;
        }
        return false;
    }

    private void realizarBusquedaReferencia(String referencia) {
        if (referencia.contains("*") || referencia.contains(".")) {
            String proov;
            String ref;
            if (referencia.contains("*")) {
                proov = referencia.substring(0, referencia.indexOf("*"));
                ref = referencia.substring(referencia.indexOf("*") + 1);
            } else {
                proov = referencia.substring(0, referencia.indexOf("."));
                ref = referencia.substring(referencia.indexOf(".") + 1);
            }

            referencia = StringUtils.rightPad(proov, 20 - ref.length(), "0") + ref;
        }

        //buscar por referencia
        InventoryItemDTO invItem = null;
        List<Object> results = itemFacade.getWebEnabledItem(referencia);
        for (Object result : results) {
            Object cols[] = (Object[]) result;
            if (invItem == null) {
                invItem = processQueryResult(cols);
            } else {
                invItem.mergeStockAndMaterials(processQueryResult(cols));
            }
        }
        try {
            newItemToNode(invItem);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al procesar el item. ", e);
        }
    }

    private void realizarBusquedaModelo(String modelo) {
        //consultar referencias modelo
        List<String> referencias = itemFacade.consultarReferenciasModelos(modelo);
        for(String ref : referencias){
            realizarBusquedaReferencia(ref);
        }
    }

    private InventoryItemDTO processQueryResult(Object cols[]) {
        int col = 0;

        String itemCode = (String) cols[col++];
        String providerCode = itemCode != null ? itemCode.substring(0, 3) : "";
        String itemName = (String) cols[col++];
        String webName = (String) cols[col++];
        String keyWords = (String) cols[col++];
        Integer price = (Integer) cols[col++];
        String subgroupCode = (String) cols[col++];
        Integer height = (Integer) cols[col++];
        Integer depth = (Integer) cols[col++];
        Integer width = (Integer) cols[col++];
        Integer weight = (Integer) cols[col++];
        String warehouse = (String) cols[col++];
        Integer quantity = (Integer) cols[col++];
        String model = (String) cols[col++];
        String colorCode = (String) cols[col++];
        String colorName = (String) cols[col++];
        String colorHexa = (String) cols[col++];
        String materialCode = (String) cols[col++];
        String materialName = (String) cols[col++];
        String materialCare = (String) cols[col++];
        String description = (String) cols[col++];
        Date newFrom = (Date) cols[col++];
        String idYoutube = (String) cols[col++];
        String descCorta = (String) cols[col++];
        Date reprocessImages = (Date) cols[col++];
        String mainCombination = (String) cols[col++];

        String departmentCode = null;
        String departmentName = null;
        String groupCode = null;
        String groupName = null;
        String subgroupName = null;

        InventoryItemDTO invItem = new InventoryItemDTO();
        invItem.setItemCode(itemCode);
        invItem.setProviderCode(providerCode);
        invItem.setItemName(itemName);
        invItem.setDepartmentCode(departmentCode);
        invItem.setDepartmentName(departmentName);
        invItem.setGroupCode(groupCode);
        invItem.setGroupName(groupName);
        invItem.setSubgroupCode(subgroupCode);
        invItem.setSubgroupName(subgroupName);
        invItem.setWebName(webName);
        invItem.setIdYoutube(idYoutube);
        invItem.setKeyWords(keyWords);
        invItem.setPrice(price != null ? price.toString() : "0");
        invItem.setHeight(height != null ? height.toString() : "");
        invItem.setDepth(depth != null ? depth.toString() : "");
        invItem.setWidth(width != null ? width.toString() : "");
        invItem.setWeight(weight != null ? weight.toString() : "");
        invItem.setModel(model);
        invItem.setMainCombination(mainCombination);
        invItem.setDescription(description);
        if (descCorta == null || descCorta.trim().isEmpty()) {
            descCorta = description;
        }
        invItem.setShortDescription(descCorta);
        if (newFrom != null) {
            invItem.setNewFrom(Long.toString(newFrom.getTime()));
        }
        if (reprocessImages != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.format(reprocessImages).equals(sdf.format(new Date()))) {
                    invItem.setProcessImages("1");
                } else {
                    invItem.setProcessImages("0");
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al procesar la fecha del producto. [" + reprocessImages + "]", e);
                invItem.setProcessImages("0");
            }

        } else {
            invItem.setProcessImages("0");
        }
        invItem.addStock(warehouse, quantity, 0, null);

        ItemColorDTO itemColor = new ItemColorDTO(colorCode, colorName, colorHexa);
        invItem.setColor(itemColor);

        ItemMaterialDTO itemMaterial = new ItemMaterialDTO(materialCode, materialName, materialCare);
        invItem.addMaterial(itemMaterial);

        return invItem;
    }

    private void newItemToNode(InventoryItemDTO invItem) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (invItem == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El producto no se encuentra disponible para web"));
            return;
        }

        for (Field campo : InventoryItemDTO.class.getDeclaredFields()) {
            String refCompleta = invItem.getItemCode();
            ResultadoBusquedaPrestashopDTO dto = new ResultadoBusquedaPrestashopDTO(refCompleta.substring(0, 3) + "*" + refCompleta.substring(16, 20));

            if (!campo.getName().equals("itemCode")) {
                AtributoItemDTO atributo = new AtributoItemDTO();
                atributo.setNombre(campo.getName());
                Object valor = InventoryItemDTO.class.getDeclaredMethod(obtenerNombreGetter(campo.getName())).invoke(invItem);
                if (valor != null) {
                    atributo.setValor(valor.toString());
                } else {
                    atributo.setValor("[Sin Asignar]");
                }

                if (atributos.contains(dto)) {
                    atributos.get(atributos.indexOf(dto)).getAtributos().add(atributo);
                } else {
                    dto.getAtributos().add(atributo);
                    atributos.add(dto);
                }
            }
        }
    }

    private void itemToNode(InventoryItemDTO invItem) throws IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, String> atributos;
        if (valores.containsKey(invItem.getItemCode())) {
            atributos = valores.get(invItem.getItemCode());
        } else {
            atributos = new HashMap<>();
        }
        if (invItem == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("El producto no se encuentra disponible para web"));
            return;
        }
        //if (root == null) {
        //    root = new DefaultTreeNode(invItem.getModel(), null);
        //}

        for (Field campo : InventoryItemDTO.class.getDeclaredFields()) {
            //TreeNode node = new DefaultTreeNode(campo.getName(), root);
            Object obj = InventoryItemDTO.class.getDeclaredMethod(obtenerNombreGetter(campo.getName())).invoke(invItem);
            if (campo.getType().getName().contains("co.matisses")) {
                for (Field subcampo : campo.getType().getDeclaredFields()) {
                    //TreeNode subnode = new DefaultTreeNode(subcampo.getName(), node);

                    Method submethod = obj.getClass().getDeclaredMethod(obtenerNombreGetter(subcampo.getName()));
                    Object subobject = submethod.invoke(obj);

                    atributos.put(subcampo.getName(), (String) subobject);

                    //subnode.getChildren().add(new DefaultTreeNode((String) subobject));
                    //node.getChildren().add(subnode);
                }
                //root.getChildren().add(node);
            } else if (campo.getType().getName().contains("java.util.List")) {
                log.log(Level.INFO, "El campo {0} es una lista", campo.getName());
                Method method = InventoryItemDTO.class.getDeclaredMethod(obtenerNombreGetter(campo.getName()));
                List objects = (List) method.invoke(invItem);
                StringBuilder valoresCampo = new StringBuilder();
                valoresCampo.append("{");
                for (Object object : objects) {
                    //TreeNode subnode = new DefaultTreeNode(campo.getName(), node);
                    for (Field subcampo : object.getClass().getDeclaredFields()) {
                        //subnode.getChildren().add(new DefaultTreeNode(subcampo.getName()));
                        //TreeNode detailsubnode = new DefaultTreeNode(subcampo.getName(), node);
                        Object subobject = object.getClass().getDeclaredMethod(obtenerNombreGetter(subcampo.getName())).invoke(object);

                        valoresCampo.append(subcampo.getName());
                        valoresCampo.append("|");
                        if (subobject instanceof java.lang.Integer) {
                            valoresCampo.append(Integer.toString((Integer) subobject));
                        } else {
                            valoresCampo.append((String) subobject);
                        }

                        //detailsubnode.getChildren().add(new DefaultTreeNode(subobject));
                        //subnode.getChildren().add(detailsubnode);
                    }
                    //node.getChildren().add(subnode);
                }
                valoresCampo.append("}");
                atributos.put(campo.getName(), valoresCampo.toString());
                //root.getChildren().add(node);
            } else {
                //node.getChildren().add(new DefaultTreeNode((String) obj));
                //root.getChildren().add(node);
            }
        }
    }

    private String obtenerNombreGetter(String nombreCampo) {
        //reemplaza la primera letra del nombre del campo por mayuscula y le 
        //antepone la palabra get
        StringBuilder sb = new StringBuilder();
        for (char c : nombreCampo.toCharArray()) {
            if (sb.length() == 0) {
                sb.append((char) (c - 32));
            } else {
                sb.append(c);
            }
        }
        sb.insert(0, "get");
        return sb.toString();
    }
}
