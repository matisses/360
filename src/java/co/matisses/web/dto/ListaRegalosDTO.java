package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ListaRegalosDTO {

    private Long idLista;
    private String codigo;
    private String nombre;
    private String cedulaCreador;
    private String nombreCreador;
    private String apellidoCreador;
    private String cedulaCocreador;
    private String nombreCocreador;
    private String apellidoCocreador;
    private Date fechaCreacion;
    private Date fechaEvento;
    private EstadoListaRegalosDTO estado;
    private TipoEventoDTO tipoEvento;
    private Boolean listaPrivada;
    private String rutaImagenPerfil;
    private String rutaImagenPortada;
    private String mensajeBienvenida;
    private String mensajeAgradecimiento;
    private Integer invitados;
    private Integer valorMinimoBono;
    private Boolean aceptaBonos;
    private String notificacionInmediataCreador;
    private String notificacionDiariaCreador;
    private String notificacionSemanalCreador;
    private String notificacionCambioCategoriaCreador;
    private String notificacionInmediataCocreador;
    private String notificacionDiariaCocreador;
    private String notificacionSemanalCocreador;
    private String notificacionCambioCategoriaCocreador;
    private Boolean permitirEntregaPersonal;
    private Boolean activa;
    private CategoriaListaRegalosDTO categoria;
    private ArrayList<ProductoListaRegalosDTO> productos;

    public ListaRegalosDTO() {
        productos = new ArrayList<>();
    }

    public ListaRegalosDTO(Long idLista, String codigo, String nombre, String nombreCreador, String apellidoCreador, String nombreCocreador, String apellidoCocreador) {
        this.idLista = idLista;
        this.codigo = codigo;
        this.nombre = nombre;
        this.nombreCreador = nombreCreador;
        this.apellidoCreador = apellidoCreador;
        this.nombreCocreador = nombreCocreador;
        this.apellidoCocreador = apellidoCocreador;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoCocreador() {
        return apellidoCocreador;
    }

    public void setApellidoCocreador(String apellidoCocreador) {
        this.apellidoCocreador = apellidoCocreador;
    }

    public String getApellidoCreador() {
        return apellidoCreador;
    }

    public void setApellidoCreador(String apellidoCreador) {
        this.apellidoCreador = apellidoCreador;
    }

    public String getNombreCocreador() {
        return nombreCocreador;
    }

    public void setNombreCocreador(String nombreCocreador) {
        this.nombreCocreador = nombreCocreador;
    }

    public String getNombreCreador() {
        return nombreCreador;
    }

    public void setNombreCreador(String nombreCreador) {
        this.nombreCreador = nombreCreador;
    }

    public String getCedulaCreador() {
        return cedulaCreador;
    }

    public void setCedulaCreador(String cedulaCreador) {
        this.cedulaCreador = cedulaCreador;
    }

    public String getCedulaCocreador() {
        return cedulaCocreador;
    }

    public void setCedulaCocreador(String cedulaCocreador) {
        this.cedulaCocreador = cedulaCocreador;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getFechaEventoUTC() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        return sdf.format(fechaEvento);
    }

    public EstadoListaRegalosDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoListaRegalosDTO estado) {
        this.estado = estado;
    }

    public TipoEventoDTO getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEventoDTO tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Boolean getListaPrivada() {
        return listaPrivada;
    }

    public void setListaPrivada(Boolean listaPrivada) {
        this.listaPrivada = listaPrivada;
    }

    public String getRutaImagenPerfil() {
        return rutaImagenPerfil;
    }

    public void setRutaImagenPerfil(String rutaImagenPerfil) {
        this.rutaImagenPerfil = rutaImagenPerfil;
    }

    public String getRutaImagenPortada() {
        return rutaImagenPortada;
    }

    public void setRutaImagenPortada(String rutaImagenPortada) {
        this.rutaImagenPortada = rutaImagenPortada;
    }

    public String getMensajeBienvenida() {
        return mensajeBienvenida;
    }

    public void setMensajeBienvenida(String mensajeBienvenida) {
        this.mensajeBienvenida = mensajeBienvenida;
    }

    public String getMensajeAgradecimiento() {
        return mensajeAgradecimiento;
    }

    public void setMensajeAgradecimiento(String mensajeAgradecimiento) {
        this.mensajeAgradecimiento = mensajeAgradecimiento;
    }

    public Integer getInvitados() {
        return invitados;
    }

    public void setInvitados(Integer invitados) {
        this.invitados = invitados;
    }

    public Integer getValorMinimoBono() {
        return valorMinimoBono;
    }

    public void setValorMinimoBono(Integer valorMinimoBono) {
        this.valorMinimoBono = valorMinimoBono;
    }

    public Boolean getAceptaBonos() {
        return aceptaBonos;
    }

    public void setAceptaBonos(Boolean aceptaBonos) {
        this.aceptaBonos = aceptaBonos;
    }

    public String getNotificacionInmediataCreador() {
        return notificacionInmediataCreador;
    }

    public void setNotificacionInmediataCreador(String notificacionInmediataCreador) {
        this.notificacionInmediataCreador = notificacionInmediataCreador;
    }

    public String getNotificacionDiariaCreador() {
        return notificacionDiariaCreador;
    }

    public void setNotificacionDiariaCreador(String notificacionDiariaCreador) {
        this.notificacionDiariaCreador = notificacionDiariaCreador;
    }

    public String getNotificacionSemanalCreador() {
        return notificacionSemanalCreador;
    }

    public void setNotificacionSemanalCreador(String notificacionSemanalCreador) {
        this.notificacionSemanalCreador = notificacionSemanalCreador;
    }

    public String getNotificacionCambioCategoriaCreador() {
        return notificacionCambioCategoriaCreador;
    }

    public void setNotificacionCambioCategoriaCreador(String notificacionCambioCategoriaCreador) {
        this.notificacionCambioCategoriaCreador = notificacionCambioCategoriaCreador;
    }

    public String getNotificacionInmediataCocreador() {
        return notificacionInmediataCocreador;
    }

    public void setNotificacionInmediataCocreador(String notificacionInmediataCocreador) {
        this.notificacionInmediataCocreador = notificacionInmediataCocreador;
    }

    public String getNotificacionDiariaCocreador() {
        return notificacionDiariaCocreador;
    }

    public void setNotificacionDiariaCocreador(String notificacionDiariaCocreador) {
        this.notificacionDiariaCocreador = notificacionDiariaCocreador;
    }

    public String getNotificacionSemanalCocreador() {
        return notificacionSemanalCocreador;
    }

    public void setNotificacionSemanalCocreador(String notificacionSemanalCocreador) {
        this.notificacionSemanalCocreador = notificacionSemanalCocreador;
    }

    public String getNotificacionCambioCategoriaCocreador() {
        return notificacionCambioCategoriaCocreador;
    }

    public void setNotificacionCambioCategoriaCocreador(String notificacionCambioCategoriaCocreador) {
        this.notificacionCambioCategoriaCocreador = notificacionCambioCategoriaCocreador;
    }

    public Boolean getPermitirEntregaPersonal() {
        return permitirEntregaPersonal;
    }

    public void setPermitirEntregaPersonal(Boolean permitirEntregaPersonal) {
        this.permitirEntregaPersonal = permitirEntregaPersonal;
    }

    public CategoriaListaRegalosDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaListaRegalosDTO categoria) {
        this.categoria = categoria;
    }

    public ArrayList<ProductoListaRegalosDTO> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<ProductoListaRegalosDTO> productos) {
        this.productos = productos;
    }

    public void agregarProducto(ProductoListaRegalosDTO producto) {
        this.productos.add(producto);
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idLista);
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
        final ListaRegalosDTO other = (ListaRegalosDTO) obj;
        return Objects.equals(this.idLista, other.idLista);
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
