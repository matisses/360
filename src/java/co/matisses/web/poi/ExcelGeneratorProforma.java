package co.matisses.web.poi;

import co.matisses.persistence.web.entity.CuentaBancariaProveedor;
import co.matisses.persistence.web.entity.DetalleProforma;
import co.matisses.persistence.web.facade.CuentaBancariaProveedorFacade;
import co.matisses.persistence.web.facade.DatosProveedorFacade;
import co.matisses.persistence.web.facade.DetalleProformaFacade;
import co.matisses.web.dto.ColumnaProformaDTO;
import co.matisses.web.dto.ColumnaDatosProformaDTO;
import co.matisses.web.dto.DatosProformaDTO;
import co.matisses.web.dto.DetalleProformaDTO;
import co.matisses.web.dto.EncabezadoExcelDTO;
import co.matisses.web.dto.ProformaInvoiceDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ygil
 */
public class ExcelGeneratorProforma implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(ExcelGeneratorProforma.class.getSimpleName());
    private String nombreArchivo;
    private String proveedor;
    private String rutaLogo;
    private File[] files;
    private ProformaInvoiceDTO proforma;
    private List<ColumnaDatosProformaDTO> prepararDatosProformas;
    private List<EncabezadoExcelDTO> encabezadoExcel;
    private List<DatosProformaDTO> datosExcel;
    @EJB
    private DetalleProformaFacade detalleProformaFacade;
    @EJB
    private CuentaBancariaProveedorFacade cuentaBancariaProveedorFacade;
    @EJB
    private DatosProveedorFacade datosProveedorFacade;

    public ExcelGeneratorProforma() {
    }

    public ExcelGeneratorProforma(String nombreArchivo, List<ColumnaDatosProformaDTO> prepararDatosProformas, List<EncabezadoExcelDTO> encabezadoExcel,
            String proveedor, String rutaLogo) {
        this.nombreArchivo = nombreArchivo;
        this.prepararDatosProformas = prepararDatosProformas;
        this.encabezadoExcel = encabezadoExcel;
        this.proveedor = proveedor;
        this.rutaLogo = rutaLogo;
    }

    public ExcelGeneratorProforma(String nombreArchivo, List<ColumnaDatosProformaDTO> prepararDatosProformas, List<EncabezadoExcelDTO> encabezadoExcel,
            String proveedor, String rutaLogo, List<DatosProformaDTO> datosExcel, ProformaInvoiceDTO proforma, BaruApplicationMBean applicationMBean, DetalleProformaFacade detalleProformaFacade,
            CuentaBancariaProveedorFacade cuentaBancariaProveedorFacade, DatosProveedorFacade datosProveedorFacade) {
        this.nombreArchivo = nombreArchivo;
        this.prepararDatosProformas = prepararDatosProformas;
        this.encabezadoExcel = encabezadoExcel;
        this.proveedor = proveedor;
        this.rutaLogo = rutaLogo;
        this.datosExcel = datosExcel;
        this.proforma = proforma;
        this.applicationMBean = applicationMBean;
        this.detalleProformaFacade = detalleProformaFacade;
        this.cuentaBancariaProveedorFacade = cuentaBancariaProveedorFacade;
        this.datosProveedorFacade = datosProveedorFacade;
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public List<ColumnaDatosProformaDTO> getPrepararDatosProformas() {
        return prepararDatosProformas;
    }

    public void setPrepararDatosProformas(List<ColumnaDatosProformaDTO> prepararDatosProformas) {
        this.prepararDatosProformas = prepararDatosProformas;
    }

    public List<EncabezadoExcelDTO> getEncabezadoSuperiorProformas() {
        return encabezadoExcel;
    }

    public void setEncabezadoSuperiorProformas(List<EncabezadoExcelDTO> encabezadoExcel) {
        this.encabezadoExcel = encabezadoExcel;
    }

    public void generarArchivoExcel() throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet hoja = wb.createSheet("PROFORMA INVOICE");

        XSSFFont fuenteTitulos = wb.createFont();
        fuenteTitulos.setBold(true);

        XSSFFont fuenteDatos = wb.createFont();
        fuenteDatos.setBold(false);

        XSSFCellStyle estiloEncabezados = wb.createCellStyle();
        estiloEncabezados.setAlignment(CellStyle.ALIGN_CENTER);
        estiloEncabezados.setFont(fuenteTitulos);
        estiloEncabezados.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        XSSFCellStyle estiloDatos = wb.createCellStyle();
        estiloDatos.setAlignment(CellStyle.ALIGN_CENTER);
        estiloDatos.setFont(fuenteDatos);
        estiloDatos.setWrapText(true);
        estiloDatos.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        /**
         * **********************************************************************
         * AGREGAR DATOS AL EXCEL
         * **********************************************************************
         */
        int anterior = -1;
        XSSFRow columna = null;
        int diferenciaColumna = 0;
        /*Contar total columnas*/
        int dif = 0;
        for (ColumnaDatosProformaDTO d : prepararDatosProformas) {
            if (d.getColumnas() != null && d.getColumnas().size() > 0) {
                dif += d.getColumnas().size();
            } else {
                dif++;
            }
        }

        if (dif > 17) {
            diferenciaColumna = dif - 17;
        } else {
            diferenciaColumna = 0;
        }
        for (EncabezadoExcelDTO dto : encabezadoExcel) {
            if (dto.getFilaInicial() > anterior) {
                anterior = dto.getFilaInicial();
                columna = hoja.createRow(dto.getFilaInicial() - 1);
            }
            Cell celda = columna.createCell(dto.getColumnaInicial() >= 8 ? diferenciaColumna + dto.getColumnaInicial() - 1 : dto.getColumnaInicial() - 1);
            celda.setCellType(Cell.CELL_TYPE_STRING);
            celda.setCellStyle(estiloEncabezados);
            if (dto.getFilaInicial() == 3 && dto.getColumnaInicial() == 7 && dto.getColumnaFinal() == 11) {
                if (!obtenerLogoProveedor(wb, hoja, dto.getColumnaInicial(), diferenciaColumna + dto.getColumnaFinal(), dto.getFilaInicial(), dto.getColumnaFinal())) {
                    celda.setCellValue(dto.getColumnaInicial());
                    hoja.autoSizeColumn(anterior);
                }
            } else {
                celda.setCellValue(dto.getValor());
                hoja.autoSizeColumn(anterior);
            }
            hoja.addMergedRegion(new CellRangeAddress(dto.getFilaInicial() - 1, dto.getFilaFinal() - 1,
                    dto.getColumnaInicial() >= 8 ? diferenciaColumna + dto.getColumnaInicial() - 1 : dto.getColumnaInicial() - 1,
                    dto.getColumnaInicial() >= 7 ? diferenciaColumna + dto.getColumnaFinal() - 1 : dto.getColumnaFinal() - 1));
        }

        XSSFRow encabezado = hoja.createRow(10);
        XSSFRow subEncabezado = hoja.createRow(11);
        int contador = 0;
        for (ColumnaDatosProformaDTO dato : prepararDatosProformas) {
            Cell celda = encabezado.createCell(contador);
            celda.setCellStyle(estiloEncabezados);
            celda.setCellType(Cell.CELL_TYPE_STRING);
            celda.setCellValue(dato.getNombreIngles());
            if (dato.getColumnas() != null && dato.getColumnas().size() > 0 && !dato.getColumnas().isEmpty()) {
                for (ColumnaProformaDTO pro : dato.getColumnas()) {
                    if (pro.getNombre2() != null && !pro.getNombre2().isEmpty()) {
                        hoja.addMergedRegion(new CellRangeAddress(10, 10, contador, contador + dato.getColumnas().size() - 1));
                        Cell subCelda = subEncabezado.createCell(contador);
                        subCelda.setCellStyle(estiloEncabezados);
                        subCelda.setCellType(Cell.CELL_TYPE_STRING);
                        subCelda.setCellValue(pro.getNombre2Ingles());
                        contador++;
                    }
                }
            } else {
                hoja.addMergedRegion(new CellRangeAddress(10, 11, contador, contador));
                contador++;
            }
            hoja.autoSizeColumn(contador);
        }

        if (datosExcel != null && !datosExcel.isEmpty()) {
            //Inicia bloque de inserccion de datos de la PI
            int filas = 12;
            for (DatosProformaDTO d : datosExcel) {
                if (!d.isLineaIncluida()) {
                    continue;
                }
                XSSFRow dato = hoja.createRow(filas);
                dato.setHeight(Short.valueOf("1500"));
                int cont = 0;
                for (DetalleProformaDTO dp : d.getDetalleProforma()) {
                    Cell celda = dato.createCell(cont);
                    celda.setCellStyle(estiloDatos);
                    if (dp.getColumna() != null && dp.getColumna().isTipoImagen()) {
                        if (files == null) {
                            obtenerFiles();
                        }
                        hoja.setColumnWidth(cont, 4000);
                        obtenerImagenProducto(wb, hoja, cont, cont, filas, filas, dp.getLineNum());
                    } else {
                        if (dp.getColumna() != null && dp.getColumna().getIdTipoDato() != null && dp.getColumna().getIdTipoDato().getIdTipoDato() != null && dp.getColumna().getIdTipoDato().getIdTipoDato() != 0) {
                            if (dp.getColumna().getIdTipoDato().getTipoDato().equals("Integer")) {
                                celda.setCellType(Cell.CELL_TYPE_NUMERIC);
                            } else if (dp.getColumna().getIdTipoDato().getTipoDato().equals("Double")) {
                                celda.setCellType(Cell.CELL_TYPE_NUMERIC);
                            } else {
                                celda.setCellType(Cell.CELL_TYPE_STRING);
                            }
                        } else {
                            celda.setCellType(Cell.CELL_TYPE_STRING);
                        }
                        celda.setCellValue(dp.getValor());
                    }
                    cont++;
                }
                hoja.autoSizeColumn(cont);
                filas++;
            }

            int posicion = 0;
            if (posicion == 0) {
                XSSFRow info = hoja.createRow(filas + 1);
                Cell celda = info.createCell(1);
                celda.setCellStyle(estiloEncabezados);
                celda.setCellValue("1. TOTALS:");
                celda.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 3, 1, 2));
                hoja.autoSizeColumn(1);

                Cell celda2 = info.createCell(3);
                celda2.setCellStyle(estiloEncabezados);
                celda2.setCellValue("Valor total:");
                celda2.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 4));

                Cell subCelda2 = info.createCell(5);
                subCelda2.setCellValue(proforma.getIdTipoMoneda().getSimbolo() + " " + proforma.getValorTotal());
                subCelda2.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 5, 9));
                filas++;

                XSSFRow info2 = hoja.createRow(filas + 1);
                Cell celda3 = info2.createCell(3);
                celda3.setCellStyle(estiloEncabezados);
                celda3.setCellValue("Valor total descuento:");
                celda3.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 4));

                Cell subCelda3 = info2.createCell(5);
                subCelda3.setCellValue(proforma.getIdTipoMoneda().getSimbolo() + " " + proforma.getValorTotalDescuento());
                subCelda3.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 5, 9));
                filas++;

                XSSFRow info3 = hoja.createRow(filas + 1);
                Cell celda4 = info3.createCell(3);
                celda4.setCellStyle(estiloEncabezados);
                celda4.setCellValue("Valor CBM:");
                celda4.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 4));

                Cell subCelda4 = info3.createCell(5);
                subCelda4.setCellValue(proforma.getCbmTotal());
                subCelda4.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 5, 9));
                filas++;
                posicion++;
            }
            if (posicion == 1) {
                XSSFRow info = hoja.createRow(filas + 1);
                Cell celda = info.createCell(1);
                celda.setCellStyle(estiloEncabezados);
                celda.setCellValue("2. TERMS OF PAYMENT:");
                celda.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 1, 2));
                hoja.autoSizeColumn(1);

                Cell celda2 = info.createCell(3);
                celda2.setCellValue(proforma.getTerminosPago());
                celda2.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 9));
                hoja.autoSizeColumn(2);
                posicion++;
                filas++;
            }
            if (posicion == 2) {
                XSSFRow info = hoja.createRow(filas + 1);
                Cell celda = info.createCell(1);
                celda.setCellStyle(estiloEncabezados);
                celda.setCellValue("3. TERMS OF DELIVERY:");
                celda.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 1, 2));
                hoja.autoSizeColumn(1);

                Cell celda2 = info.createCell(3);
                celda2.setCellValue(proforma.getTerminosEntrega());
                celda2.setCellType(Cell.CELL_TYPE_STRING);

                hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 9));
                hoja.autoSizeColumn(2);
                posicion++;
                filas++;
            }
            if (posicion == 3) {
                List<CuentaBancariaProveedor> cuentas = cuentaBancariaProveedorFacade.obtenerCuentasUsadasProforma(proforma.getIdProforma());

                if (cuentas != null && !cuentas.isEmpty()) {
                    XSSFRow info = hoja.createRow(filas + 1);
                    Cell celda = info.createCell(1);
                    celda.setCellStyle(estiloEncabezados);
                    celda.setCellValue("4. BANKS INFORMATION:");
                    celda.setCellType(Cell.CELL_TYPE_STRING);

                    hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + (cuentas.size() * 5), 1, 2));
                    hoja.autoSizeColumn(1);

                    for (CuentaBancariaProveedor c : cuentas) {
                        if (posicion == 3) {
                            Cell subCelda = info.createCell(3);
                            subCelda.setCellStyle(estiloEncabezados);
                            subCelda.setCellValue("Beneficiary:");
                            subCelda.setCellType(Cell.CELL_TYPE_STRING);

                            hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 4));

                            Cell subCelda2 = info.createCell(5);
                            subCelda2.setCellValue(datosProveedorFacade.find(proveedor).getRazonSocial());
                            subCelda2.setCellType(Cell.CELL_TYPE_STRING);

                            hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 5, 9));
                            posicion++;
                            filas++;
                        }

                        XSSFRow info2 = hoja.createRow(filas + 1);
                        Cell celda2 = info2.createCell(3);
                        celda2.setCellStyle(estiloEncabezados);
                        celda2.setCellValue("Bank:");
                        celda2.setCellType(Cell.CELL_TYPE_STRING);

                        hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 4));

                        Cell subCelda2 = info2.createCell(5);
                        subCelda2.setCellValue(c.getIdSucursalBanco().getIdBanco().getRazonSocial());
                        subCelda2.setCellType(Cell.CELL_TYPE_STRING);

                        hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 5, 9));
                        filas++;

                        XSSFRow info3 = hoja.createRow(filas + 1);
                        Cell celda3 = info3.createCell(3);
                        celda3.setCellStyle(estiloEncabezados);
                        celda3.setCellValue("Bank address:");
                        celda3.setCellType(Cell.CELL_TYPE_STRING);

                        hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 4));

                        Cell subCelda3 = info3.createCell(5);
                        subCelda3.setCellValue(c.getIdSucursalBanco().getDireccion());
                        subCelda3.setCellType(Cell.CELL_TYPE_STRING);

                        hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 5, 9));
                        filas++;

                        XSSFRow info4 = hoja.createRow(filas + 1);
                        Cell celda4 = info4.createCell(3);
                        celda4.setCellStyle(estiloEncabezados);
                        celda4.setCellValue("Account:");
                        celda4.setCellType(Cell.CELL_TYPE_STRING);

                        hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 4));

                        Cell subCelda4 = info4.createCell(5);
                        subCelda4.setCellValue(c.getCuentaBancaria());
                        subCelda4.setCellType(Cell.CELL_TYPE_STRING);

                        hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 5, 9));
                        filas++;

                        XSSFRow info5 = hoja.createRow(filas + 1);
                        Cell celda5 = info5.createCell(3);
                        celda5.setCellStyle(estiloEncabezados);
                        celda5.setCellValue("Swift Code:");
                        celda5.setCellType(Cell.CELL_TYPE_STRING);

                        hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 3, 4));

                        Cell subCelda5 = info5.createCell(5);
                        subCelda5.setCellValue(c.getIdSucursalBanco().getSwift());
                        subCelda5.setCellType(Cell.CELL_TYPE_STRING);

                        hoja.addMergedRegion(new CellRangeAddress(filas + 1, filas + 1, 5, 9));
                        filas++;
                    }
                }
            }
        }

        String file = nombreArchivo + ".xlsx";
        FileOutputStream out = new FileOutputStream(System.getProperty("jboss.server.temp.dir") + File.separator + file);
        wb.write(out);
        out.close();
    }

    private boolean obtenerLogoProveedor(XSSFWorkbook wb, XSSFSheet hoja, int columnaInicial, int columnaFinal, int filaInicial, int filaFinal) {
        boolean resultado = false;
        if (verificarImagen()) {
            try {
                InputStream is = new FileInputStream(rutaLogo + File.separator + proveedor + ".jpg");
                byte[] bytes = IOUtils.toByteArray(is);
                int pictureIdx = wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
                is.close();
                CreationHelper helper = wb.getCreationHelper();
                if (hoja != null) {
                    XSSFDrawing drawing = hoja.createDrawingPatriarch();
                    ClientAnchor anchor = helper.createClientAnchor();
                    anchor.setCol1(columnaInicial);
                    anchor.setCol2(columnaFinal);
                    anchor.setRow1(filaInicial);
                    anchor.setRow2(filaFinal);
                    anchor.setAnchorType(0);
                    XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
                    pict.resize();
                    resultado = true;
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return resultado;
    }

    private boolean verificarImagen() {
        File url = new File(rutaLogo + File.separator + proveedor + ".jpg");
        if (url.exists()) {
            return true;
        } else {
            return false;
        }
    }

    private void obtenerFiles() {
        File file = new File(applicationMBean.obtenerValorPropiedad("url.local.imagesProductosProforma") + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo());

        if (file.exists()) {
            files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (!pathname.isHidden() && pathname.length() < 102400) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    private void obtenerImagenProducto(XSSFWorkbook wb, XSSFSheet hoja, int columnaInicial, int columnaFinal, int filaInicial, int filaFinal, final int linea) {
        final String referenciaItem = obtenerReferenciaItem(linea);

        if (files.length > 0) {
            for (File f : files) {
                if (f.getName().contains(referenciaItem)) {
                    try {
                        InputStream is = new FileInputStream(applicationMBean.obtenerValorPropiedad("url.local.imagesProductosProforma")
                                + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo() + File.separator + f.getName());
                        byte[] bytes = IOUtils.toByteArray(is);
                        int pictureIdx = wb.addPicture(bytes, XSSFWorkbook.PICTURE_TYPE_JPEG);
                        is.close();
                        CreationHelper helper = wb.getCreationHelper();
                        if (hoja != null) {
                            XSSFDrawing drawing = hoja.createDrawingPatriarch();
                            ClientAnchor anchor = helper.createClientAnchor();
                            anchor.setCol1(columnaInicial);
                            anchor.setCol2(columnaFinal);
                            anchor.setRow1(filaInicial);
                            anchor.setRow2(filaFinal);
                            anchor.setAnchorType(0);
                            XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
                            pict.resize(0.9);
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, e.getMessage());
                    }
                    break;
                }
            }
        }
    }

    private String obtenerReferenciaItem(int linea) {
        DetalleProforma det = detalleProformaFacade.obtenerDetalleEspecifico(proforma.getIdProforma(), linea, true);

        if (det != null && det.getIdDetalleProforma() != null && det.getIdDetalleProforma() != 0) {
            return det.getValor();
        }
        return "";
    }
}
