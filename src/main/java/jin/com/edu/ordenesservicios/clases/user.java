package jin.com.edu.ordenesservicios.clases;

public class user {
    String correo1;
    String contrasena1;
    String tipo1;
    String nombre;
    String areaConocimiento;
    String sexo;
    int edad;

    public user(String correo1, String contrasena1, String tipo1) {
        this.correo1 = correo1;
        this.contrasena1 = contrasena1;
        this.tipo1 = tipo1;
    }

    public user(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getCorreo1() {
        return correo1;
    }

    public void setCorreo1(String correo1) {
        this.correo1 = correo1;
    }

    public String getContrasena1() {
        return contrasena1;
    }

    public void setContrasena1(String contrasena1) {
        this.contrasena1 = contrasena1;
    }

    public String getTipo1() {
        return tipo1;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAreaConocimiento() {
        return areaConocimiento;
    }

    public void setAreaConocimiento(String areaConocimiento) {
        this.areaConocimiento = areaConocimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String toString() {
        return this.tipo1;

    }


}
