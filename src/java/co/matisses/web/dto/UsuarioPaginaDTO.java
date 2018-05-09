package co.matisses.web.dto;

import co.matisses.persistence.web.entity.UsuarioPagina;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

/**
 *
 * @author ygil
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioPaginaDTO {

    private Integer id;
    private Long usuarioId;
    private String nombreUsuario;
    private String password;
    private String nombre;
    private String documento;
    private boolean esLista;
    private boolean estado;
    private boolean esNuevo;
    private boolean esDecorador;
    private boolean esPlanificador;
    private boolean pendienteAprobacionDecorador;
    private boolean pendienteAprobacionPlanificador;
    private boolean aceptaTerminos;
    private boolean suscripcionNotificaciones;
    private Date fechaRegistro;
    private PuntosWpDTO puntos;

    public UsuarioPaginaDTO() {
    }

    public UsuarioPaginaDTO(Long usuarioId, String nombreUsuario, String password, String nombre, String documento, boolean esLista, boolean estado,
            boolean esNuevo, boolean esDecorador, boolean esPlanificador, boolean pendienteAprobacionDecorador, boolean pendienteAprobacionPlanificador,
            boolean aceptaTerminos, boolean suscripcionNotificaciones, Date fechaRegistro) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.nombre = nombre;
        this.documento = documento;
        this.esLista = esLista;
        this.estado = estado;
        this.esNuevo = esNuevo;
        this.esDecorador = esDecorador;
        this.esPlanificador = esPlanificador;
        this.pendienteAprobacionDecorador = pendienteAprobacionDecorador;
        this.pendienteAprobacionPlanificador = pendienteAprobacionPlanificador;
        this.aceptaTerminos = aceptaTerminos;
        this.suscripcionNotificaciones = suscripcionNotificaciones;
        this.fechaRegistro = fechaRegistro;
    }

    public UsuarioPaginaDTO(UsuarioPagina usuario) {
        this.usuarioId = usuario.getUsuarioId();
        this.nombreUsuario = usuario.getNombreUsuario();
        this.password = usuario.getPassword();
        this.nombre = usuario.getNombre();
        this.documento = usuario.getDocumento();
        this.esLista = usuario.getEsLista() == null ? false : usuario.getEsLista();
        this.estado = usuario.getEstado() == null ? false : usuario.getEstado();
        this.esNuevo = usuario.getEsNuevo() == null ? false : usuario.getEsNuevo();
        this.esDecorador = usuario.getEsDecorador() == null ? false : usuario.getEsDecorador();
        this.esPlanificador = usuario.getEsPlanificador() == null ? false : usuario.getEsPlanificador();
        this.pendienteAprobacionDecorador = usuario.getPendienteAprobacionDecorador() == null ? false : usuario.getPendienteAprobacionDecorador();
        this.pendienteAprobacionPlanificador = usuario.getPendienteAprobacionPlanificador() == null ? false : usuario.getPendienteAprobacionPlanificador();
        this.aceptaTerminos = usuario.getAceptaTerminos() == null ? false : usuario.getAceptaTerminos();
        this.suscripcionNotificaciones = usuario.getSuscripcionNotificaciones() == null ? false : usuario.getSuscripcionNotificaciones();
        this.fechaRegistro = usuario.getFechaRegistro();
        this.puntos = new PuntosWpDTO();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public boolean isEsLista() {
        return esLista;
    }

    public void setEsLista(boolean esLista) {
        this.esLista = esLista;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public boolean isEsDecorador() {
        return esDecorador;
    }

    public void setEsDecorador(boolean esDecorador) {
        this.esDecorador = esDecorador;
    }

    public boolean isEsPlanificador() {
        return esPlanificador;
    }

    public void setEsPlanificador(boolean esPlanificador) {
        this.esPlanificador = esPlanificador;
    }

    public boolean isPendienteAprobacionDecorador() {
        return pendienteAprobacionDecorador;
    }

    public void setPendienteAprobacionDecorador(boolean pendienteAprobacionDecorador) {
        this.pendienteAprobacionDecorador = pendienteAprobacionDecorador;
    }

    public boolean isPendienteAprobacionPlanificador() {
        return pendienteAprobacionPlanificador;
    }

    public void setPendienteAprobacionPlanificador(boolean pendienteAprobacionPlanificador) {
        this.pendienteAprobacionPlanificador = pendienteAprobacionPlanificador;
    }

    public boolean isAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public boolean isSuscripcionNotificaciones() {
        return suscripcionNotificaciones;
    }

    public void setSuscripcionNotificaciones(boolean suscripcionNotificaciones) {
        this.suscripcionNotificaciones = suscripcionNotificaciones;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public PuntosWpDTO getPuntos() {
        return puntos;
    }

    public void setPuntos(PuntosWpDTO puntos) {
        this.puntos = puntos;
    }
}
