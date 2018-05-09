package co.matisses.web.dto;

import java.util.Date;
import javax.servlet.http.Part;

/**
 *
 * @author ygil
 */
public class AdjuntoLlamadaServicioDTO {

    private Long id;
    private String nombre;
    private String nombreOriginal;
    private boolean existe = false;
    private boolean eliminar = false;
    private Date fecha;
    private Part adjunto;
    private byte[] archivo;

    public AdjuntoLlamadaServicioDTO() {
    }

    public AdjuntoLlamadaServicioDTO(Long id, String nombre, String nombreOriginal, boolean existe, boolean eliminar, Date fecha, Part adjunto) {
        this.id = id;
        this.nombre = nombre;
        this.nombreOriginal = nombreOriginal;
        this.existe = existe;
        this.eliminar = eliminar;
        this.fecha = fecha;
        this.adjunto = adjunto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }

    public boolean isEliminar() {
        return eliminar;
    }

    public void setEliminar(boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Part getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(Part adjunto) {
        this.adjunto = adjunto;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}
