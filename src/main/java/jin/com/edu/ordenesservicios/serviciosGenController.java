package jin.com.edu.ordenesservicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jin.com.edu.ordenesservicios.clases.ServiciosGen;
import jin.com.edu.ordenesservicios.clases.Tarea;

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
    public void DobleClick(MouseEvent mevt){
        if (mevt.getClickCount()>1){
            if (tblServcios.getSelectionModel().getSelectedItem()!=null){
                try {
                    Stage stage = new Stage();//Crear una nueva ventana
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ventanaModificar.fxml"));
                    Scene escena = new Scene(loader.load());
                    stage.setScene(escena);//agregar la escena de la ventana
                    stage.initModality(Modality.APPLICATION_MODAL);
                    modificarController mc =  loader.getController();
                    ServiciosGen sg = (ServiciosGen) tblServcios.getSelectionModel().getSelectedItem();
                    int id1 = sg.getId();
                    mc.setId(sg.getId());
                    mc.setId(id1);
                    mc.labNoServicio.setText(String.valueOf(sg.getId()));
                    stage.show();
                    actualizarServcios();
                    //ch200111036@chapala.tecmm.edu.mx
                    //stage.show();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }
    }


    private void actualizarServcios() {
        try{
            Connection c = EnlaceIvan.getConexion();
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
            Connection c = EnlaceIvan.getConexion();
            Statement stm = c.createStatement();
            String sql = "SELECT * FROM solicitudes WHERE nombresolicitante LIKE '"+txtBuscar.getText()+"%'";
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
