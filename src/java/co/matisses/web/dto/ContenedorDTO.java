package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ContenedorDTO {

    private Integer idContenedor;
    private Integer CBMMinimo;
    private Integer CBMMaximo;
    private String nombre;
    private String nombreCorto;
    private String cargaMaxima;
    private String capacidadCubica;

    public ContenedorDTO() {
    }

    public ContenedorDTO(Integer idContenedor, Integer CBMMinimo, Integer CBMMaximo, String nombre, String nombreCorto, String cargaMaxima, String capacidadCubica) {
        this.idContenedor = idContenedor;
        this.nombre = nombre;
        this.nombreCorto = nombreCorto;
        this.cargaMaxima = cargaMaxima;
        this.capacidadCubica = capacidadCubica;
        this.CBMMinimo = CBMMinimo;
        this.CBMMaximo = CBMMaximo;
    }

    public ContenedorDTO(Integer idContenedor, String nombre, String nombreCorto, String cargaMaxima, String capacidadCubica) {
        this.idContenedor = idContenedor;
        this.nombre = nombre;
        this.nombreCorto = nombreCorto;
        this.cargaMaxima = cargaMaxima;
        this.capacidadCubica = capacidadCubica;
    }

    public Integer getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(Integer idContenedor) {
        this.idContenedor = idContenedor;
    }

    public Integer getCBMMinimo() {
        return CBMMinimo;
    }

    public void setCBMMinimo(Integer CBMMinimo) {
        this.CBMMinimo = CBMMinimo;
    }

    public Integer getCBMMaximo() {
        return CBMMaximo;
    }

    public void setCBMMaximo(Integer CBMMaximo) {
        this.CBMMaximo = CBMMaximo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargaMaxima() {
        return cargaMaxima;
    }

    public void setCargaMaxima(String cargaMaxima) {
        this.cargaMaxima = cargaMaxima;
    }

    public String getCapacidadCubica() {
        return capacidadCubica;
    }

    public void setCapacidadCubica(String capacidadCubica) {
        this.capacidadCubica = capacidadCubica;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }
}
