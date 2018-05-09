package co.matisses.web.poi;

import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ComparisonOperator;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFontFormatting;
import org.apache.poi.xssf.usermodel.XSSFPatternFormatting;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFSheetConditionalFormatting;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ygil
 */
@Stateless
public class ExcelGeneratorRotacion implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    private static final Logger CONSOLE = Logger.getLogger(ExcelGeneratorRotacion.class.getSimpleName());
    private long MAXIMO_IMAGENES = 102400;
    private String nombreArchivo;
    private List<String> columnasReporte;
    private List<String> columnasReportePI;
    private List<String> columnasDetalleReporte;
    private List<String> columnasVentas;
    private List<String> columnasSaldoVentas;
    private List<String> columnasSaldos;
    private List<List<Object>> datosReportes;
    private List<List<Object>> datosReportesPI;
    private List<List<Object>> datosDetalleReporte;
    private List<List<Object>> datosVentas;
    private List<List<Object>> datosSaldoVentas;
    private List<List<Object>> datosSaldos;

    public ExcelGeneratorRotacion() {
    }

    public ExcelGeneratorRotacion(BaruApplicationMBean applicationMBean, ImagenProductoMBean imagenProductoMBean, BaruGenericMBean genericMBean, String nombreArchivo, List<String> columnasReporte,
            List<String> columnasReportePI, List<String> columnasDetalleReporte, List<String> columnasVentas, List<String> columnasSaldoVentas, List<String> columnasSaldos,
            List<List<Object>> datosReportes, List<List<Object>> datosReportesPI, List<List<Object>> datosDetalleReporte, List<List<Object>> datosVentas, List<List<Object>> datosSaldoVentas,
            List<List<Object>> datosSaldos) {
        this.applicationMBean = applicationMBean;
        this.imagenProductoMBean = imagenProductoMBean;
        this.genericMBean = genericMBean;
        this.nombreArchivo = nombreArchivo;
        this.columnasReporte = columnasReporte;
        this.columnasReportePI = columnasReportePI;
        this.columnasDetalleReporte = columnasDetalleReporte;
        this.columnasVentas = columnasVentas;
        this.columnasSaldoVentas = columnasSaldoVentas;
        this.columnasSaldos = columnasSaldos;
        this.datosReportes = datosReportes;
        this.datosReportesPI = datosReportesPI;
        this.datosDetalleReporte = datosDetalleReporte;
        this.datosVentas = datosVentas;
        this.datosSaldoVentas = datosSaldoVentas;
        this.datosSaldos = datosSaldos;
    }

    public void generar() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFSheet hojaReportePI = wb.createSheet("Reporte Rotación (PI)");
        XSSFSheet hojaReporte = wb.createSheet("Reporte Rotación");
        XSSFSheet hojaDetalleReporte = wb.createSheet("Detalle Rotación");
        XSSFSheet hojaVentasNetas = wb.createSheet("Ventas Netas");
        XSSFSheet hojaSaldoVentas = wb.createSheet("Saldos Ventas");
        XSSFSheet hojaSaldos = wb.createSheet("Saldos");

        XSSFFont fuenteTitulos = wb.createFont();
        fuenteTitulos.setBold(true);

        XSSFCellStyle estiloFechas = wb.createCellStyle();
        DataFormat df = wb.createDataFormat();
        estiloFechas.setDataFormat(df.getFormat("yyyy-mm-dd"));
        estiloFechas.setFont(fuenteTitulos);
        estiloFechas.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        XSSFCellStyle estiloEncabezados = wb.createCellStyle();
        estiloEncabezados.setAlignment(CellStyle.ALIGN_CENTER);
        estiloEncabezados.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        estiloEncabezados.setFont(fuenteTitulos);

        XSSFFont fuenteDatos = wb.createFont();
        fuenteDatos.setBold(false);

        XSSFCellStyle estiloDatos = wb.createCellStyle();
        estiloDatos.setAlignment(CellStyle.ALIGN_CENTER);
        estiloDatos.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        estiloDatos.setFont(fuenteTitulos);

        /**
         * **********************************************************************\
         * AGREGANDO DATOS DE TODO EL REPORTE DE ROTACION PI
         * \***********************************************************************
         */
        XSSFRow reportePI = hojaReportePI.createRow(0);
        reportePI.setHeight(Short.valueOf("1500"));
        hojaReportePI.createFreezePane(7, 1);
        for (int i = 0; i < columnasReportePI.size(); i++) {
            Cell celdaReportePI = reportePI.createCell(i);

            celdaReportePI.setCellStyle(estiloEncabezados);
            celdaReportePI.setCellType(Cell.CELL_TYPE_STRING);
            celdaReportePI.setCellStyle(estiloEncabezados);
            celdaReportePI.setCellValue(columnasReportePI.get(i));

            if (i == 5 || i == 7 || i == 8 || (i >= 10 && i <= 14) || i == 18 || i == 19 || i == 22 || i == 23 || i == 26 || (i >= 28 && i <= 30)) {
                hojaReportePI.setColumnHidden(i, true);
            }

            if (i == 9) {
                XSSFSheetConditionalFormatting format = hojaReportePI.getSheetConditionalFormatting();
                /* Crear una regla cuando sea diferente de 0 */
                XSSFConditionalFormattingRule rule = format.createConditionalFormattingRule(ComparisonOperator.NOT_EQUAL, "0");

                /* Definir el formato de fuente si se cumple la regla. El color amarillo para las celdas que cumplan con el formato */
                XSSFFontFormatting rule_pattern = rule.createFontFormatting();
                rule_pattern.setFontColorIndex(IndexedColors.BLACK.index);

                /* Set relleno de fondo de amarillo para los registros que coincidan */
                XSSFPatternFormatting fill_pattern = rule.createPatternFormatting();
                fill_pattern.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.index);

                /* Crear rango de celdas a las que se les aplicara el formato */
                CellRangeAddress[] data_range = {CellRangeAddress.valueOf("J1:J" + String.valueOf(datosReportesPI.size() + 1))};

                /* Adjuntar regla a rango de celdas */
                format.addConditionalFormatting(data_range, rule);
            }
            if (i >= 33) {
                XSSFSheetConditionalFormatting format = hojaReportePI.getSheetConditionalFormatting();
                /* Crear una regla cuando sea diferente de 0 */
                XSSFConditionalFormattingRule rule = format.createConditionalFormattingRule(ComparisonOperator.NOT_EQUAL, "0");

                /* Definir el formato de fuente si se cumple la regla. El color amarillo para las celdas que cumplan con el formato */
                XSSFFontFormatting rule_pattern = rule.createFontFormatting();
                rule_pattern.setFontColorIndex(IndexedColors.BLACK.index);

                /* Set relleno de fondo de amarillo para los registros que coincidan */
                XSSFPatternFormatting fill_pattern = rule.createPatternFormatting();
                fill_pattern.setFillBackgroundColor(IndexedColors.LIGHT_ORANGE.index);

                /* Crear rango de celdas a las que se les aplicara el formato */
                CellRangeAddress[] data_range = {CellRangeAddress.valueOf("AH1:AL" + String.valueOf(datosReportesPI.size() + 1))};

                /* Adjuntar regla a rango de celdas */
                format.addConditionalFormatting(data_range, rule);
            }
        }

        int posReportePI = 1;
        int posRowPI = 1;
        for (List<Object> drPI : datosReportesPI) {
            XSSFRow filaReportePI = hojaReportePI.createRow(posReportePI);

            for (int a = 0; a < drPI.size(); a++) {
                Cell celda = filaReportePI.createCell(a);
                celda.setCellStyle(estiloDatos);
                Object obj = drPI.get(a);

                if (a == 0) {
                    celda.setCellType(Cell.CELL_TYPE_NUMERIC);
                    celda.setCellValue((Integer) posRowPI);
                    continue;
                }

                if (a == 1) {
                    hojaReportePI.setColumnWidth(a, 180 * 38);
                    filaReportePI.setHeight(Short.valueOf("1500"));
                    //Añadir datos de la imagen al libro
                    try {
                        String referencia = genericMBean.completarReferencia(drPI.get(2).toString());

                        if (!insertarImagen(referencia, wb, hojaReportePI, 1, posRowPI)) {
                            celda.setCellType(Cell.CELL_TYPE_STRING);
                            celda.setCellStyle(estiloDatos);
                            celda.setCellValue((String) "");
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }

                if (a == 9) {
                    /* Fondo para las celdas que no cumplan con el requisito */
                    XSSFCellStyle style = wb.createCellStyle();

                    style.setFillBackgroundColor(new XSSFColor(new java.awt.Color(217, 217, 217)));
                    style.setFillPattern(CellStyle.SOLID_FOREGROUND);
                    filaReportePI.getCell(a).setCellStyle(style);
                }

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

                hojaReportePI.autoSizeColumn(a);
            }

            hojaReportePI.setColumnWidth(1, 180 * 38);
            posReportePI += 1;
            posRowPI++;
        }

        /**
         * **********************************************************************\
         * AGREGANDO DATOS DE TODO EL REPORTE DE ROTACION
         * \***********************************************************************
         */
        XSSFRow reporte = hojaReporte.createRow(0);
        for (int i = 0; i < columnasReporte.size(); i++) {
            Cell celdaReporte = reporte.createCell(i);

            celdaReporte.setCellType(Cell.CELL_TYPE_STRING);
            celdaReporte.setCellStyle(estiloEncabezados);
            celdaReporte.setCellValue(columnasReporte.get(i));
        }

        int posReporte = 1;
        int posRow = 1;
        for (List<Object> dr : datosReportes) {
            XSSFRow filaReporte = hojaReporte.createRow(posReporte);

            for (int a = 0; a < dr.size(); a++) {
                Cell celdaD = filaReporte.createCell(a);
                celdaD.setCellStyle(estiloDatos);
                Object obj = dr.get(a);
                if (a == 0) {
                    hojaReporte.setColumnWidth(a, 180 * 38);
                    filaReporte.setHeight(Short.valueOf("1500"));

                    try {
                        String referencia = dr.get(1).toString();

                        if (!insertarImagen(referencia, wb, hojaReporte, 0, posRow)) {
                            celdaD.setCellType(Cell.CELL_TYPE_STRING);
                            celdaD.setCellStyle(estiloDatos);
                            celdaD.setCellValue((String) "");
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                if (obj instanceof java.lang.String) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                } else if (obj instanceof java.lang.Integer) {
                    celdaD.setCellType(Cell.CELL_TYPE_NUMERIC);
                    celdaD.setCellValue((Integer) obj);
                } else if (obj instanceof java.lang.Boolean) {
                    celdaD.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    celdaD.setCellValue((Boolean) obj);
                } else if (obj instanceof Date) {
                    celdaD.setCellStyle(estiloFechas);
                    celdaD.setCellValue((Date) obj);
                } else if (obj instanceof Calendar) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Calendar) obj);
                } else if (obj instanceof java.lang.Double) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Double) obj);
                } else if (obj instanceof BigDecimal) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue(((BigDecimal) obj).doubleValue());
                } else {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                }
            }

            posReporte += 1;
            posRow++;
        }

        /**
         * **********************************************************************\
         * AGREGANDO DATOS DEL DETALLE DE ROTACIÓN
         * \***********************************************************************
         */
        XSSFRow detallereporte = hojaDetalleReporte.createRow(0);
        for (int i = 0; i < columnasDetalleReporte.size(); i++) {
            Cell celdaDetalleReporte = detallereporte.createCell(i);

            celdaDetalleReporte.setCellType(Cell.CELL_TYPE_STRING);
            celdaDetalleReporte.setCellStyle(estiloEncabezados);
            celdaDetalleReporte.setCellValue(columnasDetalleReporte.get(i));
        }

        int posDetalleReporte = 1;
        for (List<Object> dr : datosDetalleReporte) {
            XSSFRow filaDetalleReporte = hojaDetalleReporte.createRow(posDetalleReporte);

            for (int a = 0; a < dr.size(); a++) {
                Cell celdaD = filaDetalleReporte.createCell(a);

                celdaD.setCellStyle(estiloDatos);
                Object obj = dr.get(a);
                if (obj instanceof java.lang.String) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                } else if (obj instanceof java.lang.Integer) {
                    celdaD.setCellType(Cell.CELL_TYPE_NUMERIC);
                    celdaD.setCellValue((Integer) obj);
                } else if (obj instanceof java.lang.Boolean) {
                    celdaD.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    celdaD.setCellValue((Boolean) obj);
                } else if (obj instanceof Date) {
                    celdaD.setCellStyle(estiloFechas);
                    celdaD.setCellValue((Date) obj);
                } else if (obj instanceof Calendar) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Calendar) obj);
                } else if (obj instanceof java.lang.Double) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Double) obj);
                } else if (obj instanceof BigDecimal) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue(((BigDecimal) obj).doubleValue());
                } else {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                }
            }

            posDetalleReporte += 1;
        }

        /**
         * **********************************************************************\
         * AGREGAR DATOS DE VENTAS
         * \***********************************************************************
         */
        XSSFRow ventasNetas = hojaVentasNetas.createRow(0);
        for (int i = 0; i < columnasVentas.size(); i++) {
            Cell celdaventas = ventasNetas.createCell(i);

            celdaventas.setCellType(Cell.CELL_TYPE_STRING);
            celdaventas.setCellStyle(estiloEncabezados);
            celdaventas.setCellValue(columnasVentas.get(i));
        }

        int posVentasNetas = 1;
        for (List<Object> dr : datosVentas) {
            XSSFRow filaVentasNetas = hojaVentasNetas.createRow(posVentasNetas);

            for (int a = 0; a < dr.size(); a++) {
                Cell celdaD = filaVentasNetas.createCell(a);

                celdaD.setCellStyle(estiloDatos);
                Object obj = dr.get(a);
                if (obj instanceof java.lang.String) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                } else if (obj instanceof java.lang.Integer) {
                    celdaD.setCellType(Cell.CELL_TYPE_NUMERIC);
                    celdaD.setCellValue((Integer) obj);
                } else if (obj instanceof java.lang.Boolean) {
                    celdaD.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    celdaD.setCellValue((Boolean) obj);
                } else if (obj instanceof Date) {
                    celdaD.setCellStyle(estiloFechas);
                    celdaD.setCellValue((Date) obj);
                } else if (obj instanceof Calendar) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Calendar) obj);
                } else if (obj instanceof java.lang.Double) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Double) obj);
                } else if (obj instanceof BigDecimal) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue(((BigDecimal) obj).doubleValue());
                } else {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                }
            }

            posVentasNetas += 1;
        }

        /**
         * **********************************************************************\
         * AGREGAR DATOS DE SALDOS PARA LA VENTA
         * \***********************************************************************
         */
        XSSFRow saldosVenta = hojaSaldoVentas.createRow(0);
        for (int i = 0; i < columnasSaldoVentas.size(); i++) {
            Cell celdaSaldosVentas = saldosVenta.createCell(i);

            celdaSaldosVentas.setCellType(Cell.CELL_TYPE_STRING);
            celdaSaldosVentas.setCellStyle(estiloEncabezados);
            celdaSaldosVentas.setCellValue(columnasSaldoVentas.get(i));
        }

        int posSaldosVentas = 1;
        for (List<Object> dr : datosSaldoVentas) {
            XSSFRow filaSaldosVentas = hojaSaldoVentas.createRow(posSaldosVentas);

            for (int a = 0; a < dr.size(); a++) {
                Cell celdaD = filaSaldosVentas.createCell(a);

                celdaD.setCellStyle(estiloDatos);
                Object obj = dr.get(a);
                if (obj instanceof java.lang.String) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                } else if (obj instanceof java.lang.Integer) {
                    celdaD.setCellType(Cell.CELL_TYPE_NUMERIC);
                    celdaD.setCellValue((Integer) obj);
                } else if (obj instanceof java.lang.Boolean) {
                    celdaD.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    celdaD.setCellValue((Boolean) obj);
                } else if (obj instanceof Date) {
                    celdaD.setCellStyle(estiloFechas);
                    celdaD.setCellValue((Date) obj);
                } else if (obj instanceof Calendar) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Calendar) obj);
                } else if (obj instanceof java.lang.Double) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Double) obj);
                } else if (obj instanceof BigDecimal) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue(((BigDecimal) obj).doubleValue());
                } else {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                }
            }

            posSaldosVentas += 1;
        }

        /**
         * **********************************************************************\
         * AGREGAR DATOS DE SALDOS
         * \***********************************************************************
         */
        XSSFRow saldos = hojaSaldos.createRow(0);
        for (int i = 0; i < columnasSaldos.size(); i++) {
            Cell celdaSaldos = saldos.createCell(i);

            celdaSaldos.setCellType(Cell.CELL_TYPE_STRING);
            celdaSaldos.setCellStyle(estiloEncabezados);
            celdaSaldos.setCellValue(columnasSaldos.get(i));
        }

        int posSaldos = 1;
        for (List<Object> dr : datosSaldos) {
            XSSFRow filaSaldos = hojaSaldos.createRow(posSaldos);

            for (int a = 0; a < dr.size(); a++) {
                Cell celdaD = filaSaldos.createCell(a);

                celdaD.setCellStyle(estiloDatos);
                Object obj = dr.get(a);
                if (obj instanceof java.lang.String) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                } else if (obj instanceof java.lang.Integer) {
                    celdaD.setCellType(Cell.CELL_TYPE_NUMERIC);
                    celdaD.setCellValue((Integer) obj);
                } else if (obj instanceof java.lang.Boolean) {
                    celdaD.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    celdaD.setCellValue((Boolean) obj);
                } else if (obj instanceof Date) {
                    celdaD.setCellStyle(estiloFechas);
                    celdaD.setCellValue((Date) obj);
                } else if (obj instanceof Calendar) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Calendar) obj);
                } else if (obj instanceof java.lang.Double) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((Double) obj);
                } else if (obj instanceof BigDecimal) {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue(((BigDecimal) obj).doubleValue());
                } else {
                    celdaD.setCellType(Cell.CELL_TYPE_STRING);
                    celdaD.setCellValue((String) obj);
                }
            }

            posSaldos += 1;
        }
        // Escriba el resultado en un archivo
        try (FileOutputStream out = new FileOutputStream(applicationMBean.obtenerValorPropiedad("url.local.reporte") + nombreArchivo + ".xlsx")) {
            wb.write(out);
            out.close();
        }

        CONSOLE.log(Level.INFO, "Archivo: {0} generado correctamente", nombreArchivo + ".xlsx");
    }

    private boolean insertarImagen(String referencia, XSSFWorkbook wb, XSSFSheet hoja, int column, int posRowPI) {
        boolean retorno = false;
        if (!imagenProductoMBean.obtenerUrlLocalProducto(referencia, true).contains("no_image")) {
            try {
                InputStream is = new FileInputStream(imagenProductoMBean.obtenerUrlLocalProducto(referencia, true));

                byte[] bytes = IOUtils.toByteArray(is);
                if (bytes.length > MAXIMO_IMAGENES) {
                    CONSOLE.log(Level.SEVERE, "No se a agregado imagen de la referencia: {0}, porque la imagen supera el tamaño permitido de la imagen. Peso de la imagen [{1}]", new Object[]{referencia, bytes});
                    return false;
                } else {
                    int pictureIdx = wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
                    is.close();
                    CreationHelper helper = wb.getCreationHelper();
                    if (hoja != null) {
                        try {
                            XSSFDrawing drawing = hoja.createDrawingPatriarch();
                            //Agregar la imagen
                            ClientAnchor anchor = helper.createClientAnchor();
                            anchor.setCol1(column);
                            anchor.setRow1(posRowPI);
                            anchor.setAnchorType(0);
                            XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
                            pict.resize();
                            retorno = true;
                        } catch (Exception e) {
                            retorno = false;
                        }
                    }
                }
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "", e);
                retorno = false;
            }
        }
        return retorno;
    }
}
