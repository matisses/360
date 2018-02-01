package co.matisses.web.poi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ygil
 */
public class ExcelReaderProforma {

    private static final Logger log = Logger.getLogger(ExcelReaderProforma.class.getSimpleName());
    private String nombre;
    private InputStream inputStream;
    private List<Object[]> objects;

    public ExcelReaderProforma() {
    }

    public ExcelReaderProforma(String nombre, InputStream inputStream, List<Object[]> objects) {
        this.nombre = nombre;
        this.inputStream = inputStream;
        this.objects = objects;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<Object[]> getObjects() {
        return objects;
    }

    public void setObjects(List<Object[]> objects) {
        this.objects = objects;
    }

    public List<Object[]> interpretarExcelRecibido() {
        objects = new ArrayList<>();
        if (nombre != null && !nombre.isEmpty()) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                XSSFSheet sheet = workbook.getSheetAt(0);
                int contadorFila = sheet.getLastRowNum();
                for (int a = 0; a <= contadorFila; a++) {
                    XSSFRow row = sheet.getRow(a);
                    if (row != null) {
                        if (row.getLastCellNum() > 0) {
                            Object[] object = new Object[row.getLastCellNum()];
                            for (int i = 0; i < row.getLastCellNum(); i++) {
                                XSSFCell celda = row.getCell(i);
                                if (celda != null && celda.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
                                    object[i] = celda.getBooleanCellValue();
                                } else if (celda != null && celda.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                    object[i] = celda.getNumericCellValue();
                                } else if (celda != null && celda.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                                    object[i] = celda.getStringCellValue();
                                }
                            }
                            if (object != null && object.length > 0) {
                                objects.add(object);
                            }
                        }
                    }
                }
                if (objects != null && !objects.isEmpty() && objects.size() > 11) {
                    objects.remove(0);
                    objects.remove(0);
                } else {
                    objects.remove(0);
                }
            } catch (FileNotFoundException e) {
                log.log(Level.SEVERE, e.getMessage());
            } catch (IOException e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        System.out.println("Cantidad de datos recibidos en la lectura del excel = " + objects.size());
        return objects;
    }
}
