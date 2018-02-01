package co.matisses.web.poi;

import co.matisses.web.dto.DatosVentasDTO;
import co.matisses.web.dto.DetalleDatosVentasDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ygil
 */
public class ExcelGeneratorDatosVentas implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private List<DatosVentasDTO> datos;

    public ExcelGeneratorDatosVentas() {
    }

    public ExcelGeneratorDatosVentas(BaruApplicationMBean applicationMBean, List<DatosVentasDTO> datos) {
        this.applicationMBean = applicationMBean;
        this.datos = datos;
    }

    public void generarInfome() throws FileNotFoundException, IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet hoja = wb.createSheet("Ventas");

        XSSFCreationHelper helper = wb.getCreationHelper();
        XSSFCellStyle estiloTitulo = wb.createCellStyle();

        XSSFCellStyle estiloSubTotal = wb.createCellStyle();
        estiloSubTotal.setDataFormat(helper.createDataFormat().getFormat("#,##0"));

        XSSFCellStyle estiloFecha = wb.createCellStyle();
        estiloFecha.setDataFormat(helper.createDataFormat().getFormat("yyyy/MM/dd"));

        XSSFCellStyle estiloNumeros = wb.createCellStyle();
        estiloNumeros.setDataFormat(helper.createDataFormat().getFormat("#,##0"));

        XSSFFont fuenteTitulo = wb.createFont();
        fuenteTitulo.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        estiloTitulo.setFont(fuenteTitulo);
        estiloTitulo.setAlignment(XSSFCellStyle.ALIGN_LEFT);

        XSSFFont fuenteSubTotal = wb.createFont();
        fuenteSubTotal.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        estiloSubTotal.setFont(fuenteSubTotal);
        estiloSubTotal.setAlignment(XSSFCellStyle.ALIGN_RIGHT);

        short fila = 0;
        short columna = 0;

        XSSFRow row = hoja.createRow(fila);
        row.createCell(columna).setCellValue("Ventas por tienda");
        hoja.addMergedRegion(new CellRangeAddress(fila, fila, columna, 3));
        fila++;

        for (DatosVentasDTO d : datos) {
            columna = 0;
            row = hoja.createRow(fila);

            Cell titleCell = row.createCell(columna);
            titleCell.setCellValue(obtenerNombreTienda(d.getAlmacen()));
            titleCell.setCellStyle(estiloTitulo);
            hoja.addMergedRegion(new CellRangeAddress(fila, fila, columna, 3));
            fila++;

            for (DetalleDatosVentasDTO v : d.getVentas()) {
                columna = 0;

                row = hoja.createRow(fila++);
                Cell dateCell = row.createCell(columna++);
                dateCell.setCellValue(v.getFecha());
                dateCell.setCellStyle(estiloFecha);
                row.createCell(columna++).setCellValue(v.getTipo());
                row.createCell(columna++).setCellValue(v.getDocumento());

                Cell valueCell = row.createCell(columna++);
                valueCell.setCellValue(v.getTotal());
                valueCell.setCellStyle(estiloNumeros);
            }

            columna = 0;

            row = hoja.createRow(fila);
            Cell subTotalCell = row.createCell(columna);
            subTotalCell.setCellValue("Subtotal");
            subTotalCell.setCellStyle(estiloSubTotal);

            Cell subTotalValueCell = row.createCell(3);
            subTotalValueCell.setCellValue(d.getTotal());
            subTotalValueCell.setCellStyle(estiloSubTotal);
            hoja.addMergedRegion(new CellRangeAddress(fila, fila, 0, 2));
            fila++;
        }

        long total = 0L;

        for (DatosVentasDTO r : datos) {
            total += r.getTotal();
        }

        row = hoja.createRow(fila);
        Cell grandTotalCell = row.createCell(0);
        grandTotalCell.setCellValue("Total");
        grandTotalCell.setCellStyle(estiloSubTotal);

        grandTotalCell = row.createCell(3);
        grandTotalCell.setCellValue(total);
        grandTotalCell.setCellStyle(estiloSubTotal);
        hoja.addMergedRegion(new CellRangeAddress(fila, fila, 0, 2));

        hoja.autoSizeColumn((short) 0);
        hoja.autoSizeColumn((short) 1);
        hoja.autoSizeColumn((short) 2);
        hoja.autoSizeColumn((short) 3);

        try (FileOutputStream fileOut = new FileOutputStream(System.getProperty("jboss.server.temp.dir") + File.separator + "Ventas.xlsx")) {
            wb.write(fileOut);
            fileOut.close();
        }
    }

    public String obtenerNombreTienda(String codTienda) {
        String props = applicationMBean.obtenerValorPropiedad("consecutivoAlmacen");

        for (String prop : props.split(";")) {
            String valores[] = prop.split(",");
            if (valores[0].equals(codTienda)) {
                return valores[1];
            }
        }
        return "Otro(" + codTienda + ")";
    }
}
