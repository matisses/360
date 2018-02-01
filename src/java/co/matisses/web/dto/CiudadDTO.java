package co.matisses.web.dto;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
public class CiudadDTO implements Comparable<CiudadDTO> {

    private Integer idCiudad;
    private String codigo;
    private String nombre;
    private String codDepartamento;
    private String nomDepartamento;
    private EstadosDTO idEstado;

    public CiudadDTO() {
    }

    public CiudadDTO(Integer idCiudad, String nombre, EstadosDTO idEstado) {
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.idEstado = idEstado;
    }

    public CiudadDTO(String nombre) {
        this.nombre = nombre;
    }

    public CiudadDTO(Object[] datos) {
        if (datos == null || datos.length != 4) {
            return;
        }
        this.codigo = (String) datos[0];
        this.nombre = (String) datos[1];
        this.codDepartamento = (String) datos[2];
        this.nomDepartamento = (String) datos[3];
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNomDepartamento() {
        return nomDepartamento;
    }

    public void setNomDepartamento(String nomDepartamento) {
        this.nomDepartamento = nomDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadosDTO getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadosDTO idEstado) {
        this.idEstado = idEstado;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CiudadDTO other = (CiudadDTO) obj;
        return Objects.equals(this.nombre, other.nombre);
    }

    @Override
    public String toString() {
        return nombre;
        //return StringUtils.stripAccents("CiudadDTO{" + "codigo=" + codigo + ", nombre=" + nombre + ", codDepartamento=" + codDepartamento + ", nomDepartamento=" + nomDepartamento + '}');
    }

    @Override
    public int compareTo(CiudadDTO o) {
        if (this.nombre.equals(o.getNombre())) {
            return this.nomDepartamento.compareTo(o.getNomDepartamento());
        }
        return this.nombre.compareTo(o.getNombre());
    }

    public boolean similarTo(String text) {
        return this.nombre.toLowerCase().contains(text.toLowerCase());
    }

    public static CiudadDTO valueOf(String value) {
        String[] values = value.split("\\{|}|,");
        CiudadDTO dto = new CiudadDTO();
        String codigo = StringUtils.normalizeSpace(values[1]);
        String nombre = StringUtils.normalizeSpace(values[2]);
        String codDepartamento = StringUtils.normalizeSpace(values[3]);
        String nomDepartamento = StringUtils.normalizeSpace(values[4]);

        dto.setCodigo(codigo.substring(codigo.indexOf("=") + 1));
        dto.setNombre(nombre.substring(nombre.indexOf("=") + 1));
        dto.setCodDepartamento(codDepartamento.substring(codDepartamento.indexOf("=") + 1));
        dto.setNomDepartamento(nomDepartamento.substring(nomDepartamento.indexOf("=") + 1));
        return dto;
    }
}
