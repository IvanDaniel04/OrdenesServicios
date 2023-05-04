package jin.com.edu.ordenesservicios;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jin.com.edu.ordenesservicios.EnlaceJazmin;
import jin.com.edu.ordenesservicios.EnlaceNB;
import jin.com.edu.ordenesservicios.HelloApplication;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    //INICIO DE SESIÓN

    @FXML private AnchorPane ap;
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
    @FXML private TextField txtcontraseña;

    @FXML private ImageView mostraContraseña;
    @FXML private ImageView ocultarContraseña;

    @FXML private Rectangle rectangle ;
    //FadeTransition fade = new FadeTransition(Duration.seconds(1));


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
            rectangle.setVisible(false);
        } else if (contrasena.equals("")) {
            labAlertaContrasena.setVisible(true);
            labAlertaCorreo.setVisible(false);
            labAlerta.setVisible(false);
            rectangle.setVisible(false);
        } else {
            try {
                connection = EnlaceIvan.getConexion();
                pst = connection.prepareStatement("select correo, contrasena from usuarios where correo='" + correo
                        + "' and contrasena ='" + contrasena + "'");
                rs = pst.executeQuery();

                if (rs.next()) {
                    System.out.println("Abrir ventana");
                   HelloApplication.setVista("ventanaServiciosGen");

                } else {
                    System.out.println("contraseña o correo incorrecto");
                    labAlerta.setVisible(true);
                    rectangle.setVisible(true);
                    labAlertaContrasena.setVisible(false);
                    labAlertaCorreo.setVisible(false);
                }
                

            } catch (Exception e) {
             e.printStackTrace();

            }
        }
    }
    public void contraseña (){
        if (ocultarContraseña.isVisible()){
            ocultarContraseña.setVisible(false);
            mostraContraseña.setVisible(true);
            txtcontraseña.setVisible(true);
            psFContrasena.setVisible(false);
            txtcontraseña.setText(psFContrasena.getText());
        }else if (mostraContraseña.isVisible()){
            mostraContraseña.setVisible(false);
            ocultarContraseña.setVisible(true);
            txtcontraseña.setVisible(false);
            psFContrasena.setVisible(true);
            psFContrasena.setText(txtcontraseña.getText());

        }

    }
}