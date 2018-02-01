package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class FiltrosInventarioDTO {

    private String valor;
    private boolean activo = false;

    public FiltrosInventarioDTO() {
    }

    public FiltrosInventarioDTO(String valor, boolean activo) {
        this.valor = valor;
        this.activo = activo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
