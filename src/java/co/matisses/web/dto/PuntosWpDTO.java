package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author jguisao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PuntosWpDTO {

    private Integer puntosAcumulados;
    private BigDecimal redimeEfectivo;
    private BigDecimal redimeBono;
    private List<Puntos> puntos;

    public PuntosWpDTO() {
        redimeEfectivo = new BigDecimal(0);
        redimeBono = new BigDecimal(0);
    }

    public PuntosWpDTO(Object[] parametros, List<Puntos> puntos) {
        this.puntosAcumulados = (Integer) parametros[0];
        this.redimeEfectivo = (BigDecimal) parametros[1];
        this.redimeBono = (BigDecimal) parametros[2];
        this.puntos = puntos;
    }

    public Integer getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(Integer puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public BigDecimal getRedimeEfectivo() {
        return redimeEfectivo;
    }

    public void setRedimeEfectivo(BigDecimal redimeEfectivo) {
        this.redimeEfectivo = redimeEfectivo;
    }

    public BigDecimal getRedimeBono() {
        return redimeBono;
    }

    public void setRedimeBono(BigDecimal redimeBono) {
        this.redimeBono = redimeBono;
    }

    public List<Puntos> getPuntos() {
        return puntos;
    }

    public void setPuntos(List<Puntos> puntos) {
        this.puntos = puntos;
    }

    public static class Puntos {

        private String porcentaje;
        private String concepto;

        public Puntos() {
        }

        public Puntos(String porcentaje, String concepto) {
            this.porcentaje = porcentaje;
            this.concepto = concepto;
        }

        public String getPorcentaje() {
            return porcentaje;
        }

        public void setPorcentaje(String porcentaje) {
            this.porcentaje = porcentaje;
        }

        public String getConcepto() {
            return concepto;
        }

        public void setConcepto(String concepto) {
            this.concepto = concepto;
        }
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
