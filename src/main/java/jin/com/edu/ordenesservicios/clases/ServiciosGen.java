package jin.com.edu.ordenesservicios.clases;

public class ServiciosGen {

    private int id;
    private String area;
    private String descripcion;
    private String fecha;
    private String nombresolicitante;
    private String ubicacion;

    public ServiciosGen(int id, String area, String descripcion, String fecha, String nombresolicitante, String ubicacion) {
        this.id = id;
        this.area = area;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.nombresolicitante = nombresolicitante;
        this.ubicacion = ubicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombresolicitante() {
        return nombresolicitante;
    }

    public void setNombresolicitante(String nombresolicitante) {
        this.nombresolicitante = nombresolicitante;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
