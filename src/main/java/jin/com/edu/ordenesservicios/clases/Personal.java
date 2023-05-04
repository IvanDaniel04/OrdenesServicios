package jin.com.edu.ordenesservicios.clases;

public class Personal{
    private int id;
    private String area;
    private int edad;
    private String nombre;
    private char sexo;

    public Personal(int id, String area, int edad, String nombre, char sexo) {
        this.id = id;
        this.area = area;
        this.edad = edad;
        this.nombre = nombre;
        this.sexo = sexo;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    public int toString2(){
        return this.id;
    }

}