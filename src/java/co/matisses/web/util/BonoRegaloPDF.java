package co.matisses.web.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.apache.pdfbox.pdmodel.font.encoding.Encoding;
//import org.krysalis.barcode4j.impl.code128.Code128Bean;
//import org.krysalis.barcode4j.impl.fourstate.RoyalMailCBCBean;
//import org.krysalis.barcode4j.impl.fourstate.USPSIntelligentMailBean;
//import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
//import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 *
 * @author dbotero
 */
public class BonoRegaloPDF {
    
    private static final Logger log = Logger.getLogger(BonoRegaloPDF.class.getSimpleName());
    private PDPageContentStream contentStream;
    private ByteArrayOutputStream outputStream;
    
    public BonoRegaloPDF() {
        outputStream = new ByteArrayOutputStream();
    }
    
    public void escribirPDF() throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDRectangle pageSize = new PDRectangle(612f, 396f);
            PDPage page = new PDPage(pageSize);
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            
            ponerIdCodigoBarras(document);
            
            contentStream.close();
            
            log.log(Level.INFO, "Tamano del stream antes de guardar [{0}] bytes ", outputStream.size());
            document.save(outputStream);
            document.close();
        }
        FileOutputStream fos = new FileOutputStream("bono.pdf");
        outputStream.writeTo(fos);
    }
    
    private String encodedBytesToString(int[] encoded) {
        StringBuilder sb = new StringBuilder();
        for (int i : encoded) {
            sb.append((char) i);
        }
        return sb.toString();
    }
    
//    private void crearImagenCodigoBarras() throws FileNotFoundException, IOException, NoSuchAlgorithmException {
//        USPSIntelligentMailBean codebarBean = new USPSIntelligentMailBean();
//        final int dpi = 300;
//        //codebarBean.setBarHeight(15d);
//        codebarBean.doQuietZone(true);
//        File outputFile = new File("codigo de barras.jpg");
//        OutputStream out = new FileOutputStream(outputFile);
//        try {
//            //Set up the canvas provider for monochrome JPEG output 
//            BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
//            //Generate the barcode
//            String digest = "00B39400938";
//            //String digest = DigestUtils.sha256Hex("P8356881M3500000B39400938");
//            codebarBean.generateBarcode(canvas, digest);
//            //Signal end of generation
//            canvas.finish();
//        } finally {
//            out.close();
//        }
//    }
    
    private void ponerIdCodigoBarras(PDDocument document) throws IOException {
        
        File trueTypeFontFile = new File("C:\\Windows\\Fonts\\Code128.ttf");
        PDFont font = PDTrueTypeFont.load(document, trueTypeFontFile, Encoding.getInstance(COSName.BASE_ENCODING));
        contentStream.beginText();
        contentStream.setFont(font, 108);
        contentStream.newLineAtOffset(30, 285);
        //contentStream.showText(encodedBytesToString(encodedText));
        contentStream.endText();
    }
}
