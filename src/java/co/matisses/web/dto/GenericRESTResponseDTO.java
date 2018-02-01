package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class GenericRESTResponseDTO {

    private int estado;
    private Integer valor;
    private String mensaje;

    public GenericRESTResponseDTO() {
    }

    public GenericRESTResponseDTO(int codigo, String mensaje) {
        this.estado = codigo;
        this.mensaje = mensaje;
    }

    public GenericRESTResponseDTO(int estado, Integer valor) {
        this.estado = estado;
        this.valor = valor;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "GenericRESTResponseDTO{" + "estado=" + estado + ", mensaje=" + mensaje + '}';
    }

}
