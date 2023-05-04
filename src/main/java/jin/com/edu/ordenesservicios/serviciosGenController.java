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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jin.com.edu.ordenesservicios.clases.ServiciosGen;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    private Pane paneSerGen;
    @FXML
    private TableView<ServiciosGen> tblServcios;

    private ObservableList<ServiciosGen> servciosGen = FXCollections.observableArrayList();
    TablaObservable tablaObservable = new TablaObservable();
    Thread hiloThread = new Thread();
    private boolean buscando = false;

    public void initialize (){
        System.out.println("Iniciando...");

        actualizarServcios();
        tablaObservable.addObserver(new TablaObserver(tblServcios));
        hiloThread = new Thread(hilo);
        hiloThread.start();
        //actualizarServcios();

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
                    stage.setResizable(false);
                    modificarController mc = loader.getController();
                    ServiciosGen sg = (ServiciosGen) tblServcios.getSelectionModel().getSelectedItem();
                    try {
                        mc.setID(sg.getId());
                        int id = sg.getId();
                        System.out.println("ID POTENTE" + sg.getId());
                        try {
                            Connection c = EnlaceIvan.getConexion();
                            Statement stm = c.createStatement();
                            Statement stm2 = c.createStatement();
                            Statement stm3 = c.createStatement();
                            String sql = "SELECT * FROM solicitudes WHERE id = '" + id + "';";
                            String sql2 = "SELECT * FROM tareaservico WHERE id = '" + id + "';";


                            String sql3 = "SELECT p.nombre,ts.status "
                                    + "FROM personal p "
                                    + "JOIN tareaservico ts ON ts.personal = p.id "
                                    + "JOIN solicitudes s ON ts.solicitud = s.id "
                                    + "WHERE s.id = " + id;
                            ResultSet r3 = stm3.executeQuery(sql3);
                            while (r3.next()){
                                String nombre  = r3.getString("nombre");
                                mc.seleccionarPersonalEnComboBox(nombre);
                                mc.seleccionarEstado(r3.getString("status").charAt(0));


                            }
                            ResultSet r2 = stm2.executeQuery(sql2);
                            ResultSet r = stm.executeQuery(sql);
                            while (r2.next()){
                                mc.txtTE.setText(r2.getString("tiempoestimado"));
                                mc.txaObservaciones.setText(r2.getString("observacion"));

                            }
                            while (r.next()) {
                                mc.txaDescripcion.setText(r.getString("descripcion"));
                                mc.labFregistro.setText(String.valueOf(r.getDate("fecha")));
                            }
                            stm.close();
                            stm2.close();
                            stm3.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stage.show();
                    }catch (Exception e){
                        try {
                            Stage stage1 = new Stage();//Crear una nueva ventana
                            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("actualizandoDatos.fxml"));
                            Scene escena1 = new Scene(loader1.load());
                            stage1.setScene(escena1);//agregar la escena de la ventana
                            stage1.initModality(Modality.APPLICATION_MODAL);
                            stage1.setResizable(false);
                            stage1.show();
                        }catch (Exception exception){

                        }

                        System.out.println("Espere se está actualizando");
                    }

                    //ch200111036@chapala.tecmm.edu.mx
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            tblServcios.refresh();
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
    private void Buscar(KeyEvent event) throws InterruptedException {
        buscando = true;
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
        buscando = false;
    }

    @FXML
    public void pane () {
        if (paneSerGen.isVisible()) {//detectar doble click
            paneSerGen.setVisible(false);
        }else {
            paneSerGen.setVisible(true);
        }
    }


    @FXML
    public void cerrarSesión(){
        HelloApplication.setVista("hello-view");

    }
    @FXML
    public void registarUsuario(){
        HelloApplication.setVista("ventanaNuevoUs");

    }
    @FXML
    public void Vusuarios(){
        HelloApplication.setVista("ventanaVisualizarUsuarios");
    }
    Runnable hilo = new Runnable() {
        List<ServiciosGen> serviciosAntiguos = new ArrayList<>();

        @Override
        public void run() {
            while (true) {
                try {
                    //Thread.sleep(60000);
                    Thread.sleep(60000);
                    if (buscando) { // Verifica si se está realizando una búsqueda
                        continue; // Si es así, espera y vuelve a verificar la variable
                    }
                    try {
                        Connection c = EnlaceIvan.getConexion();
                        Statement stm = c.createStatement();
                        String sql = "SELECT * FROM solicitudes";
                        ResultSet r = stm.executeQuery(sql);

                        // Copiar la lista actual de servicios antes de actualizarla
                        List<ServiciosGen> serviciosNuevos = new ArrayList<>();
                        while (r.next()) {
                            ServiciosGen servicio = new ServiciosGen(
                                    r.getInt("id"),
                                    r.getString("area"),
                                    r.getString("descripcion"),
                                    r.getString("fecha"),
                                    r.getString("nombresolicitante"),
                                    r.getString("ubicacion")
                            );
                            serviciosNuevos.add(servicio);
                        }
                        stm.close();

                        // Eliminar los servicios que ya no están presentes en la base de datos
                        List<ServiciosGen> serviciosEliminar = new ArrayList<>(serviciosAntiguos);
                        serviciosEliminar.removeAll(serviciosNuevos);
                        tablaObservable.eliminarServicios(serviciosEliminar);

                        // Agregar los servicios nuevos a la lista y a la tabla
                        for (ServiciosGen servicio : serviciosNuevos) {
                            tablaObservable.addServicio(servicio);
                        }
                        serviciosAntiguos = serviciosNuevos;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };




}
