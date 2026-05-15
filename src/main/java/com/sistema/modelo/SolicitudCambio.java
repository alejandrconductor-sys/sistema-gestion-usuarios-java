package com.sistema.modelo;
import java.sql.Timestamp;

public class SolicitudCambio {

    private int idSolicitud;
    private int idUsuario;
    private String modulo;
    private String tipoEntidad;
    private String accion;
    private int referenciaId;
    private String datosAnteriores;
    private String datosNuevos;
    private String estado;
    private Timestamp fecha;

    public int getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getModulo() {
        return modulo;
    }
    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
    public String getTipoEntidad() {
        return tipoEntidad;
    }
    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
    }
    public String getAccion() {
        return accion;
    }
    public void setAccion(String accion) {
        this.accion = accion;
    }
    public int getReferenciaId() {
        return referenciaId;
    }
    public void setReferenciaId(int referenciaId) {
        this.referenciaId = referenciaId;
    }
    public String getDatosAnteriores() {
        return datosAnteriores;
    }
    public void setDatosAnteriores(String datosAnteriores) {
        this.datosAnteriores = datosAnteriores;
    }
    public String getDatosNuevos() {
        return datosNuevos;
    }
    public void setDatosNuevos(String datosNuevos) {
        this.datosNuevos = datosNuevos;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}