package jin.com.edu.ordenesservicios.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import jin.com.edu.ordenesservicios.EnlaceNB;
import jin.com.edu.ordenesservicios.clases.ServiciosGen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class serviciosGenController {

    @FXML
    private TextField txtBuscarS;
    @FXML
    private Button btnBuscar;
    @FXML private TableColumn columnId;
    @FXML private TableColumn columnFecha;
    @FXML private TableColumn columnDescripcion;
    @FXML private TableColumn columnArea;
    @FXML private TableColumn columnNombreS;
    @FXML private TableColumn columnUbicacion;
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
                        r.getInt("N. Registro"),
                        r.getString("Area solicitante"),
                        r.getString("Descripción"),
                        r.getString("Fecha"),
                        r.getString("Nombre solicitante"),
                        r.getString("Ubicacion")));
                columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
                columnArea.setCellValueFactory(new PropertyValueFactory<>("area"));
                columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
                columnNombreS.setCellValueFactory(new PropertyValueFactory<>("nombresolicitante"));
                columnUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));

            }
            stm.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        tblServcios.refresh();

    }


}
