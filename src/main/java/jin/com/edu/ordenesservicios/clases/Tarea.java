package jin.com.edu.ordenesservicios.clases;

import java.util.Date;

public class Tarea {
    private int id;
    private String tiempoEstimado;
    private String descripción;
    private String observacion;
    private Date fechaFinalización;
    private Date fechaRegistro;
    private String estado;

    public Tarea(String estado) {
        this.estado = estado;
    }

    public Tarea(int id, String tiempoEstimado, String operador, String descripción, String observacion, Date fechaFinalización, Date fechaRegistro) {
        this.id = id;
        this.tiempoEstimado = tiempoEstimado;
        this.descripción = descripción;
        this.observacion = observacion;
        this.fechaFinalización = fechaFinalización;
        this.fechaRegistro = fechaRegistro;
    }

    public char getEstado() {
        return estado.charAt(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(String tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaFinalización() {
        return fechaFinalización;
    }

    public void setFechaFinalización(Date fechaFinalización) {
        this.fechaFinalización = fechaFinalización;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return estado;
    }
}
