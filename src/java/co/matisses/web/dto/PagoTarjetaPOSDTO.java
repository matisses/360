package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class PagoTarjetaPOSDTO {

    private String valor;
    private String voucher;
    private String franquicia;
    private String digitos;

    public PagoTarjetaPOSDTO() {
    }

    public PagoTarjetaPOSDTO(String valor, String voucher, String franquicia, String digitos) {
        this.valor = valor;
        this.voucher = voucher;
        this.franquicia = franquicia;
        this.digitos = digitos;
    }

    public String getDigitos() {
        return digitos;
    }

    public void setDigitos(String digitos) {
        this.digitos = digitos;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    @Override
    public String toString() {
        return "PagoTarjetaPOSDTO{" + "valor=" + valor + ", voucher=" + voucher + ", franquicia=" + franquicia + ", digitos=" + digitos + '}';
    }

    public String toJSON(){
        return "{" + "\"valor\":\"" + valor + "\", \"voucher\":\"" + voucher + "\", \"franquicia\":\"" + franquicia + "\", \"digitos\":\"" + digitos + "}";
    }
}
