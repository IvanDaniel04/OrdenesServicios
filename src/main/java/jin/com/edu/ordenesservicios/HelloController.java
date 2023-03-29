package jin.com.edu.ordenesservicios;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jin.com.edu.ordenesservicios.EnlaceJazmin;
import jin.com.edu.ordenesservicios.EnlaceNB;
import jin.com.edu.ordenesservicios.HelloApplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    //INICIO DE SESIÓN
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField psFContrasena;
    @FXML
    private Label labAlertaCorreo;
    @FXML
    private Label labAlertaContrasena;
    @FXML
    private Label labAlerta;
    @FXML
    private Button btnInciciar;

    public void login() {
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String correo = txtCorreo.getText().trim();
        String contrasena = psFContrasena.getText().trim();
        if (correo.equals("")) {
            System.out.println("Un campo esta vacio");
            labAlertaCorreo.setVisible(true);
            labAlertaContrasena.setVisible(false);
            labAlerta.setVisible(false);
        } else if (contrasena.equals("")) {
            labAlertaContrasena.setVisible(true);
            labAlertaCorreo.setVisible(false);
            labAlerta.setVisible(false);
        } else {
            try {
                connection = EnlaceJazmin.getConexion();
                pst = connection.prepareStatement("select correo, contraseña from usuarios where correo='" + correo
                        + "' and contraseña ='" + contrasena + "'");
                rs = pst.executeQuery();

                if (rs.next()) {
                    System.out.println("Abrir ventana");
                    HelloApplication.setVista("ventanaServiciosGen");
                } else {
                    System.out.println("contraseña o correo incorrecto");
                    labAlerta.setVisible(true);
                    labAlertaContrasena.setVisible(false);
                    labAlertaCorreo.setVisible(false);
                }
            } catch (Exception e) {
                System.out.println("nada");

            }
        }
    }
}