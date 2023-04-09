package jin.com.edu.ordenesservicios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jin.com.edu.ordenesservicios.EnlaceIvan;
import jin.com.edu.ordenesservicios.clases.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class visualizarUsuariosController{
    @FXML TableView<user> tbvUsuarios;
    @FXML TableColumn<user, String> tbcCorreo;
    @FXML TableColumn<user, String> tbcContrasena;
    @FXML TableColumn<user, String> tbcTipo;
    @FXML Button btnRegresar;

    @FXML Pane paneVU;


    ObservableList<user> usuariosTabla;

    public void Actualizar(){
        try {
            Connection c = EnlaceIvan.getConexion();
            Statement stm = c.createStatement();
            String sql = "SELECT * FROM usuarios";
            ResultSet r = stm.executeQuery(sql);
            usuariosTabla.clear();
            while (r.next()) {
                String tipoO = "";
                if (r.getString("tipo").charAt(0) == 'A'){
                    tipoO = "Administrador";
                }else {
                    tipoO = "Operador";
                }
                tbvUsuarios.setItems(usuariosTabla);
                usuariosTabla.add(new user(r.getString("correo"), r.getString("contrasena"), tipoO));
                tbcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo1"));
                tbcContrasena.setCellValueFactory(new PropertyValueFactory<>("contrasena1"));
                tbcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo1"));
            }
            stm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbvUsuarios.refresh();
    }

    public void Regresar(){
    HelloApplication.setVista("ventanaServiciosGen");
    }

    public void cerrarSesion(){
        HelloApplication.setVista("hello-view");
    }

    public void pane () {
        if (paneVU.isVisible()) {//detectar doble click
            paneVU.setVisible(false);
        }else {
            paneVU.setVisible(true);
        }
    }

    public void initialize (){
        usuariosTabla = FXCollections.observableArrayList();
       Actualizar();
        DropShadow sombra = new DropShadow();
        btnRegresar.setOnMouseEntered(e -> btnRegresar.setEffect(sombra));
        btnRegresar.setOnMouseExited(e -> btnRegresar.setEffect(null));

    }


}
