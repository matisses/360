package co.matisses.web.util;

import co.matisses.web.dto.ProcesoProductoDTO;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 *
 * @author dbotero
 */
public class RotuloCcygaPDF {

    private static final Logger log = Logger.getLogger(RotuloCcygaPDF.class.getSimpleName());
    private Boolean esCliente;
    private Boolean esDemo;
    private Integer idProducto;
    private String nroDocumento;
    private String observacionesGenerales;
    private String referencia;
    private String descripcionProducto;
    private List<ProcesoProductoDTO> procesos;
    private PDPageContentStream contentStream;
    private ByteArrayOutputStream outputStream;

    public RotuloCcygaPDF() {
        procesos = new ArrayList<>();
        outputStream = new ByteArrayOutputStream();
        esDemo = false;
        esCliente = false;
    }

    public Boolean getEsCliente() {
        return esCliente;
    }

    public void setEsCliente(Boolean esCliente) {
        this.esCliente = esCliente;
        if (this.esDemo && esCliente) {
            this.esDemo = false;
        }
    }

    public Boolean getEsDemo() {
        return esDemo;
    }

    public void setEsDemo(Boolean esDemo) {
        this.esDemo = esDemo;
        if (this.esCliente && esDemo) {
            this.esCliente = false;
        }
    }

    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getObservacionesGenerales() {
        return observacionesGenerales;
    }

    public void setObservacionesGenerales(String observacionesGenerales) {
        this.observacionesGenerales = observacionesGenerales;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public List<ProcesoProductoDTO> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<ProcesoProductoDTO> procesos) {
        this.procesos = procesos;
    }

    public ByteArrayOutputStream generar() throws IOException {
        try (PDDocument document = new PDDocument()) {
            // Configura la pagina como media carta horizontal
            PDRectangle pageSize = new PDRectangle(612f, 396f);
            PDPage page = new PDPage(pageSize);
            document.addPage(page);

            contentStream = new PDPageContentStream(document, page);

            ponerIdProducto();
            ponerFechaImpresion();
            ponerImagen(document);
            ponerTipoMcia();
            ponerNotasGenerales();
            dibujarTablaProcesos();
            ponerTitulosTablaProcesos();
            llenarTablaProcesos();
            ponerReferencia();
            ponerDescripcionProducto();

            contentStream.close();

            log.log(Level.INFO, "Tamano del stream antes de guardar [{0}] bytes ", outputStream.size());
            document.save(outputStream);
            document.close();
        }
        log.log(Level.INFO, "Se escribieron [{0}] bytes en el stream", outputStream.size());

        return outputStream;
    }

    private void ponerIdProducto() throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        contentStream.beginText();
        contentStream.setFont(font, 108);
        contentStream.newLineAtOffset(30, 285);
        contentStream.showText(idProducto.toString());
        contentStream.endText();
    }

    private void ponerFechaImpresion() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        PDFont font = PDType1Font.HELVETICA;
        contentStream.beginText();
        contentStream.setFont(font, 18);
        contentStream.newLineAtOffset(30, 365);
        contentStream.showText(sdf.format(new Date()));
        contentStream.endText();
    }

    private void ponerImagen(PDDocument document) throws IOException {
        String imagePath = "https://img.matisses.co/" + referencia + "/images/" + referencia + "_01.jpg";
        log.log(Level.INFO, "Intentando abrir la imagen [{0}]", imagePath);
        URL urlImagen = new URL(imagePath);

        HttpURLConnection con = (HttpURLConnection) urlImagen.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == 404) {
            imagePath = "https://img.matisses.co/no_image.jpg";
            urlImagen = new URL(imagePath);
            log.log(Level.WARNING, "La imagen no existe, cargando imagen comodin [{0}]", "https://img.matisses.co/no_image.jpg");
        }

        File f = new File(referencia + "_01.jpg");
        FileUtils.copyURLToFile(urlImagen, f);

        PDImageXObject pdImage = PDImageXObject.createFromFile(f, document);
        contentStream.drawImage(pdImage, 30, 25, 257.5f, 207.5f);
        f.delete();
    }

    private void ponerTipoMcia() throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        String tipo = "CEDI";
        if (esCliente) {
            tipo = "Cliente";
            ponerNroDocumento(470);
            contentStream.setNonStrokingColor(Color.RED);
        } else if (esDemo) {
            tipo = "Demo";
            ponerNroDocumento(440);
            contentStream.setNonStrokingColor(Color.BLUE);
        }
        contentStream.beginText();
        contentStream.setFont(font, 49);
        contentStream.newLineAtOffset(300, 345);
        contentStream.showText(tipo);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.endText();
    }

    private void ponerNroDocumento(int offsetX) throws IOException {
        escribirVariasLineas(nroDocumento, 14, offsetX, 360, 18, 18);
    }

    private void ponerNotasGenerales() throws IOException {
        escribirVariasLineas(observacionesGenerales, 40, 300, 325, 12, 12);
    }

    private void dibujarTablaProcesos() throws IOException {
        contentStream.moveTo(300, 205);
        contentStream.lineTo(570, 205);
        contentStream.stroke();
        contentStream.moveTo(380, 235);
        contentStream.lineTo(380, 30);
        contentStream.stroke();
    }

    private void ponerTitulosTablaProcesos() throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        contentStream.beginText();
        contentStream.setFont(font, 14);
        contentStream.newLineAtOffset(305, 215);
        contentStream.showText("PROCESO");
        contentStream.endText();
        contentStream.beginText();
        contentStream.newLineAtOffset(385, 215);
        contentStream.showText("OBSERVACIONES");
        contentStream.endText();
    }

    private void llenarTablaProcesos() throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        contentStream.setFont(font, 12);
        int offsetY = 190;
        for (ProcesoProductoDTO dto : procesos) {
            contentStream.beginText();
            contentStream.newLineAtOffset(305, offsetY);
            contentStream.showText(dto.getNombreProceso());
            contentStream.endText();

            offsetY = escribirVariasLineas(dto.getComentario(), 30, 385, offsetY, 15, 12);

            contentStream.setStrokingColor(new Color(150, 150, 150));
            contentStream.moveTo(300, offsetY - 4);
            contentStream.lineTo(570, offsetY - 4);
            contentStream.stroke();

            offsetY -= 15;
        }
    }

    private void ponerReferencia() throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        contentStream.setFont(font, 18);
        contentStream.beginText();
        contentStream.newLineAtOffset(30, 265);
        contentStream.showText(referencia);
        contentStream.endText();
    }

    private void ponerDescripcionProducto() throws IOException {
        escribirVariasLineas(descripcionProducto, 35, 30, 250, 12, 12);
    }

    private int escribirVariasLineas(String texto, int maximoCaracteres, int offsetX, int offsetY, int interlineado, float fontSize) throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        String[] textoDividido = texto.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String textoDividido1 : textoDividido) {
            if (sb.length() + 1 + textoDividido1.length() <= maximoCaracteres) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(textoDividido1);
            } else {
                contentStream.beginText();
                contentStream.setFont(font, fontSize);
                contentStream.newLineAtOffset(offsetX, offsetY);
                contentStream.showText(sb.toString());
                contentStream.endText();
                offsetY -= interlineado;
                sb = new StringBuilder(textoDividido1);
            }
        }
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(offsetX, offsetY);
        contentStream.showText(sb.toString());
        contentStream.endText();
        return offsetY;
    }
}
