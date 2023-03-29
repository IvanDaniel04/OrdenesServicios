package jin.com.edu.ordenesservicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import jin.com.edu.ordenesservicios.clases.Personal;
import jin.com.edu.ordenesservicios.clases.user;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class usuario implements Initializable {
    @FXML private TextField txtCorreo;
    @FXML private TextField txtContraseña;
    @FXML private ComboBox<user> tipo;
    @FXML private Label labAlerta;
    @FXML private Pane paneUsr;
    ObservableList <user> lis;


    public void registar (){
        String cadena =txtCorreo.getText();
        String correo="^[\\w-]+(\\\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z0-9]{2,})$";
        Pattern patron = Pattern.compile(correo);
        Matcher mat = patron.matcher(cadena);
        if (mat.matches()){
            try {
                Connection c = EnlaceJazmin.getConexion();
                Statement stm = c.createStatement();
                String sql ="INSERT INTO usuarios VALUES (0,'"+cadena+"','"+txtContraseña.getText()+"','" +
                        tipo.getSelectionModel().getSelectedItem().toString().charAt(0)+"')";
                stm.execute(sql);
                HelloApplication.setVista("ventanaVisualizarUsuarios");
                stm.close();



            }catch (Exception e){
                e.printStackTrace();

            }

        }else{
            System.out.println("Ingresa un correo valido");
            labAlerta.setVisible(true);
        }

    }
    public void regresar (){
        HelloApplication.setVista("ventanaServiciosGen");
    }
    @FXML
    public void pane () {
        if (paneUsr.isVisible()) {//detectar doble click
            paneUsr.setVisible(false);
        }else {
            paneUsr.setVisible(true);
        }
    }
    @FXML
    public void cerrarSesión(){
        HelloApplication.setVista("hello-view");

    }
    @FXML
    public void Vusuarios(){
        HelloApplication.setVista("ventanaVisualizarUsuarios");

    }






            @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       lis= FXCollections.observableArrayList();
       lis.add(new user("Administrador"));
        lis.add(new user("Operador"));
       tipo.setItems(lis);

    }
}
