package jin.com.edu.ordenesservicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import jin.com.edu.ordenesservicios.EnlaceNB;
import jin.com.edu.ordenesservicios.clases.ServiciosGen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class serviciosGenController {

    @FXML
    private TextField txtBuscar;
    @FXML private TableColumn clmId;
    @FXML private TableColumn clmFecha;
    @FXML private TableColumn clmDescripcion;
    @FXML private TableColumn clmArea;
    @FXML private TableColumn clmNombreS;
    @FXML private TableColumn clmUbicacion;
    @FXML
    private ImageView ivUsuarios;
    @FXML
    private TableView tblServcios;
    private ObservableList<ServiciosGen> servciosGen = FXCollections.observableArrayList();


    public void initialize (){
        System.out.println("Iniciando...");
        actualizarServcios();

    }


    private void actualizarServcios() {

        try{
            Connection c = EnlaceNB.getConexion();
            Statement stm = c.createStatement();
            String sql = "SELECT * FROM solicitudes";
            ResultSet r = stm.executeQuery(sql);
            servciosGen.clear();
            while (r.next()){
                tblServcios.setItems(servciosGen);
                servciosGen.add(new ServiciosGen(
                        r.getInt("id"),
                        r.getString("area"),
                        r.getString("descripcion"),
                        r.getString("fecha"),
                        r.getString("nombresolicitante"),
                        r.getString("ubicacion")));
                clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
                clmArea.setCellValueFactory(new PropertyValueFactory<>("area"));
                clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                clmFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
                clmNombreS.setCellValueFactory(new PropertyValueFactory<>("nombresolicitante"));
                clmUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

            }
            stm.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        tblServcios.refresh();

    }
    @FXML
    private void Buscar(KeyEvent event) {

        try{
            Connection c = EnlaceNB.getConexion();
            Statement stm = c.createStatement();
            String sql = "SELECT * FROM Libro WHERE TITULO LIKE '"+txtBuscar.getText()+"%'";
            ResultSet r = stm.executeQuery(sql);
            servciosGen.clear();
            while (r.next()){
                tblServcios.setItems(servciosGen);
                servciosGen.add(new ServiciosGen(
                        r.getInt("id"),
                        r.getString("area"),
                        r.getString("descripcion"),
                        r.getString("fecha"),
                        r.getString("nombresolicitante"),
                        r.getString("ubicacion")));
                clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
                clmArea.setCellValueFactory(new PropertyValueFactory<>("area"));
                clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                clmFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
                clmNombreS.setCellValueFactory(new PropertyValueFactory<>("nombresolicitante"));
                clmUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

            }
            stm.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        tblServcios.refresh();

    }


}
