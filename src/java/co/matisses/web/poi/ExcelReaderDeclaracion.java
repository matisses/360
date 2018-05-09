package co.matisses.web.poi;

import java.io.FileNotFoundException;
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
public class ExcelReaderDeclaracion {

    private static final Logger CONSOLE = Logger.getLogger(ExcelReaderDeclaracion.class.getSimpleName());
    private String nombre;
    private InputStream inputStream;
    private List<Object[]> objects;

    public ExcelReaderDeclaracion() {
        objects = new ArrayList<>();
    }

    public ExcelReaderDeclaracion(String nombre, InputStream inputStream, List<Object[]> objects) {
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

    public List<Object[]> interpretarExcel() {
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
                if (objects != null && !objects.isEmpty()) {
                    objects.remove(0);
                }
            } catch (FileNotFoundException e) {
                CONSOLE.log(Level.SEVERE, e.getMessage());
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, e.getMessage());
            }
        }
        CONSOLE.log(Level.INFO, "Cantidad de datos recibidos en la lectura de excel = {0}", objects.size());
        return objects;
    }
}
