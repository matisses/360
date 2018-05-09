package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class LlamadaAdjuntoSolucionDTO {

    private Integer idAdjuntoLlamadaSolucion;
    private Integer idLlamadaSolucion;
    private String nombre;

    public LlamadaAdjuntoSolucionDTO() {
    }

    public LlamadaAdjuntoSolucionDTO(Integer idAdjuntoLlamadaSolucion, Integer idLlamadaSolucion, String nombre) {
        this.idAdjuntoLlamadaSolucion = idAdjuntoLlamadaSolucion;
        this.idLlamadaSolucion = idLlamadaSolucion;
        this.nombre = nombre;
    }

    public Integer getIdAdjuntoLlamadaSolucion() {
        return idAdjuntoLlamadaSolucion;
    }

    public void setIdAdjuntoLlamadaSolucion(Integer idAdjuntoLlamadaSolucion) {
        this.idAdjuntoLlamadaSolucion = idAdjuntoLlamadaSolucion;
    }

    public Integer getIdLlamadaSolucion() {
        return idLlamadaSolucion;
    }

    public void setIdLlamadaSolucion(Integer idLlamadaSolucion) {
        this.idLlamadaSolucion = idLlamadaSolucion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
