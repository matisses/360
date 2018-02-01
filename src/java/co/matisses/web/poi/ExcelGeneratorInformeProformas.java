package co.matisses.web.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ygil
 */
public class ExcelGeneratorInformeProformas {

    private static final Logger log = Logger.getLogger(ExcelGeneratorInformeProformas.class.getSimpleName());
    private String nombreArchivo;
    private List<String> encabezado;
    private List<List<Object>> detalles;

    public ExcelGeneratorInformeProformas() {
    }

    public ExcelGeneratorInformeProformas(String nombreArchivo, List<String> encabezado, List<List<Object>> detalles) {
        this.nombreArchivo = nombreArchivo;
        this.encabezado = encabezado;
        this.detalles = detalles;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void generarArchivoExcel() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet hoja = wb.createSheet("DATOS");

        XSSFFont fuenteTitulos = wb.createFont();
        fuenteTitulos.setBold(true);
        XSSFFont fuenteDatos = wb.createFont();
        fuenteDatos.setBold(false);

        XSSFCellStyle estiloEncabezados = wb.createCellStyle();
        estiloEncabezados.setAlignment(CellStyle.ALIGN_CENTER);
        estiloEncabezados.setFont(fuenteTitulos);
        estiloEncabezados.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        XSSFCellStyle estiloFechas = wb.createCellStyle();
        DataFormat df = wb.createDataFormat();
        estiloFechas.setDataFormat(df.getFormat("yyyy-mm-dd"));
        estiloFechas.setFont(fuenteDatos);
        estiloFechas.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        XSSFCellStyle estiloDatos = wb.createCellStyle();
        estiloDatos.setAlignment(CellStyle.ALIGN_CENTER);
        estiloDatos.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        estiloDatos.setFont(fuenteDatos);
        estiloDatos.setWrapText(true);

        /**
         * **********************************************************************
         * AGREGAR DATOS AL EXCEL
         * **********************************************************************
         */
        XSSFRow informe = hoja.createRow(0);

        for (int i = 0; i < encabezado.size(); i++) {
            Cell celda = informe.createCell(i);
            celda.setCellStyle(estiloEncabezados);
            celda.setCellType(Cell.CELL_TYPE_STRING);
            celda.setCellValue(encabezado.get(i));
        }

        int pos = 2;
        for (List<Object> detalle : detalles) {
            XSSFRow filas = hoja.createRow(pos);
            for (int i = 0; i < detalle.size(); i++) {
                Cell celda = filas.createCell(i);
                celda.setCellStyle(estiloDatos);
                Object obj = detalle.get(i);
                if (obj instanceof java.lang.String) {
                    celda.setCellType(Cell.CELL_TYPE_STRING);
                    estiloDatos.setAlignment(CellStyle.ALIGN_LEFT);
                    celda.setCellStyle(estiloDatos);
                    celda.setCellValue((String) obj);
                } else if (obj instanceof java.lang.Integer) {
                    celda.setCellType(Cell.CELL_TYPE_NUMERIC);
                    estiloDatos.setAlignment(CellStyle.ALIGN_RIGHT);
                    celda.setCellStyle(estiloDatos);
                    celda.setCellValue((Integer) obj);
                } else if (obj instanceof java.lang.Boolean) {
                    celda.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    estiloDatos.setAlignment(CellStyle.ALIGN_LEFT);
                    celda.setCellStyle(estiloDatos);
                    celda.setCellValue((Boolean) obj);
                } else if (obj instanceof Date) {
                    celda.setCellStyle(estiloFechas);
                    celda.setCellValue((Date) obj);
                } else if (obj instanceof Calendar) {
                    celda.setCellType(Cell.CELL_TYPE_STRING);
                    estiloDatos.setAlignment(CellStyle.ALIGN_LEFT);
                    celda.setCellStyle(estiloDatos);
                    celda.setCellValue((Calendar) obj);
                } else if (obj instanceof java.lang.Double) {
                    celda.setCellType(Cell.CELL_TYPE_STRING);
                    estiloDatos.setAlignment(CellStyle.ALIGN_RIGHT);
                    celda.setCellStyle(estiloDatos);
                    celda.setCellValue((Double) obj);
                } else if (obj instanceof BigDecimal) {
                    celda.setCellType(Cell.CELL_TYPE_STRING);
                    estiloDatos.setAlignment(CellStyle.ALIGN_RIGHT);
                    celda.setCellStyle(estiloDatos);
                    celda.setCellValue(((BigDecimal) obj).doubleValue());
                } else {
                    celda.setCellType(Cell.CELL_TYPE_STRING);
                    estiloDatos.setAlignment(CellStyle.ALIGN_LEFT);
                    celda.setCellStyle(estiloDatos);
                    celda.setCellValue((String) obj);
                }
                hoja.autoSizeColumn(i);
            }
            pos++;
        }

        String file = nombreArchivo + ".xlsx";
        FileOutputStream out = new FileOutputStream(System.getProperty("jboss.server.temp.dir") + File.separator + file);
        wb.write(out);
        out.close();
    }
}
