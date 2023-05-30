package jin.com.edu.ordenesservicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class serviciosGenController {
    sinConexionController sc = new sinConexionController();

    TablaObservable tablaObservable = new TablaObservable();
    Thread hiloThread = new Thread();
    @FXML
    private TextField txtBuscar;
    @FXML
    private TableColumn clmId;
    @FXML
    private TableColumn clmFecha;
    @FXML
    private TableColumn clmDescripcion;
    @FXML
    private TableColumn clmArea;
    @FXML
    private TableColumn clmNombreS;
    @FXML
    private TableColumn clmUbicacion;
    @FXML
    private TableColumn clmEstado;
    @FXML
    private ImageView ivUsuarios;
    @FXML
    private Pane paneSerGen;
    @FXML
    private TableView<ServiciosGen> tblServcios;
    private ObservableList<ServiciosGen> servciosGen = FXCollections.observableArrayList();
    private boolean buscando = false;
    Runnable hilo = new Runnable() {
        List<ServiciosGen> serviciosAntiguos = new ArrayList<>();

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(20000);
                    if (buscando) {
                        continue;
                    }
                    if (EnlaceIvan.isConnectedToInternet()) {
                        try {
                            Connection c = EnlaceIvan.getConexion();
                            Statement stm = c.createStatement();
                            String sql = "SELECT s.*, t.status FROM solicitudes s " +
                                    "JOIN tareaservico t ON s.id = t.solicitud";
                            ResultSet r = stm.executeQuery(sql);

                            List<ServiciosGen> serviciosNuevos = new ArrayList<>();
                            while (r.next()) {
                                String estado = ""; // Variable para almacenar el estado en forma de texto
                                switch (r.getString("status").charAt(0)) {
                                    case 'T':
                                        estado = "Terminado";
                                        break;
                                    case 'N':
                                        estado = "No Atendido";
                                        break;
                                    case 'E':
                                        estado = "En Espera";
                                        break;
                                    case 'A':
                                        estado = "Atendido";
                                        break;
                                }

                                ServiciosGen servicio = new ServiciosGen(
                                        r.getInt("id"),
                                        r.getString("area"),
                                        r.getString("descripcion"),
                                        r.getString("fecha"),
                                        r.getString("nombresolicitante"),
                                        r.getString("ubicacion"),
                                        estado // Asignar el estado en forma de texto
                                );
                                serviciosNuevos.add(servicio);
                            }
                            stm.close();

                            List<ServiciosGen> serviciosEliminar = new ArrayList<>(serviciosAntiguos);
                            serviciosEliminar.removeAll(serviciosNuevos);
                            tablaObservable.eliminarServicios(serviciosEliminar);
                            for (ServiciosGen servicio : serviciosNuevos) {
                                tablaObservable.addServicio(servicio);
                            }
                            serviciosAntiguos = serviciosNuevos;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        sc.sinConexion();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void initialize() {
        if (EnlaceIvan.isConnectedToInternet()) {
            actualizarServcios();
            tablaObservable.addObserver(new TablaObserver(tblServcios));
            hiloThread = new Thread(hilo);
            hiloThread.start();
        } else {
            sc.sinConexion();
        }
    }

    public void DobleClick(MouseEvent mevt) {
        if (mevt.getClickCount() > 1) {
            if (tblServcios.getSelectionModel().getSelectedItem() != null) {
                if (EnlaceIvan.isConnectedToInternet()) {
                    try {
                        Stage stage = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("ventanaModificar.fxml"));
                        Scene escena = new Scene(loader.load());
                        stage.setScene(escena);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setResizable(false);
                        modificarController mc = loader.getController();
                        ServiciosGen sg = (ServiciosGen) tblServcios.getSelectionModel().getSelectedItem();
                        try {
                            mc.setID(sg.getId());
                            int id = sg.getId();

                            try {
                                Connection c = EnlaceIvan.getConexion();
                                Statement stm = c.createStatement();
                                Statement stm2 = c.createStatement();
                                Statement stm3 = c.createStatement();
                                String sql = "SELECT * FROM solicitudes WHERE id = '" + id + "';";
                                String sql2 = "SELECT * FROM tareaservico WHERE id = '" + id + "';";
                                String sql3 = "SELECT p.nombre,ts.status,ts.fecha "
                                        + "FROM personal p "
                                        + "JOIN tareaservico ts ON ts.personal = p.id "
                                        + "JOIN solicitudes s ON ts.solicitud = s.id "
                                        + "WHERE s.id = " + id;
                                ResultSet r3 = stm3.executeQuery(sql3);
                                while (r3.next()) {
                                    String nombre = r3.getString("nombre");
                                    mc.seleccionarPersonalEnComboBox(nombre);
                                    mc.seleccionarEstado(r3.getString("status").charAt(0));
                                    java.sql.Date fecha = r3.getDate("fecha");
                                    LocalDate localDate = fecha.toLocalDate();
                                    mc.dpFinalizacion.setValue(localDate);
                                }
                                ResultSet r2 = stm2.executeQuery(sql2);
                                ResultSet r = stm.executeQuery(sql);
                                while (r2.next()) {
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
                        } catch (Exception e) {
                            try {
                                Stage stage1 = new Stage();//Crear una nueva ventana
                                FXMLLoader loader1 = new FXMLLoader(getClass().getResource("actualizandoDatos.fxml"));
                                Scene escena1 = new Scene(loader1.load());
                                stage1.setScene(escena1);//agregar la escena de la ventana
                                stage1.initModality(Modality.APPLICATION_MODAL);
                                stage1.setResizable(false);
                                stage1.show();
                            } catch (Exception exception) {

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    sc.sinConexion();
                }

            }
            tblServcios.refresh();
        }

    }

    private void actualizarServcios() {
        if (EnlaceIvan.isConnectedToInternet()) {
            try {
                Connection c = EnlaceIvan.getConexion();
                Statement stm = c.createStatement();
                String sql = "SELECT s.*, t.status FROM solicitudes s " +
                        "JOIN tareaservico t ON s.id = t.solicitud";
                ResultSet r = stm.executeQuery(sql);
                servciosGen.clear();
                while (r.next()) {
                    tblServcios.setItems(servciosGen);

                    String estado = ""; // Variable para almacenar el estado en forma de texto
                    switch (r.getString("status").charAt(0)) {
                        case 'T':
                            estado = "Terminado";
                            break;
                        case 'N':
                            estado = "No Atendido";
                            break;
                        case 'E':
                            estado = "En Espera";
                            break;
                        case 'A':
                            estado = "Atendido";
                            break;
                    }

                    servciosGen.add(new ServiciosGen(
                            r.getInt("id"),
                            r.getString("area"),
                            r.getString("descripcion"),
                            r.getString("fecha"),
                            r.getString("nombresolicitante"),
                            r.getString("ubicacion"), estado)); // Asignar el estado en forma de texto
                    clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    clmArea.setCellValueFactory(new PropertyValueFactory<>("area"));
                    clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                    clmFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
                    clmNombreS.setCellValueFactory(new PropertyValueFactory<>("nombresolicitante"));
                    clmUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
                    clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado")); // Agregar columna de estado
                }
                stm.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            tblServcios.refresh();
        } else {
            sc.sinConexion();
        }

    }

    @FXML
    private void Buscar(KeyEvent event) throws InterruptedException {
        if (EnlaceIvan.isConnectedToInternet()) {
            buscando = true;
            try {
                Connection c = EnlaceIvan.getConexion();
                Statement stm = c.createStatement();
                String sql = "SELECT s.*, t.status FROM solicitudes s " +
                        "JOIN tareaservico t ON s.id = t.solicitud " +
                        "WHERE s.nombresolicitante LIKE '" + txtBuscar.getText() + "%'";
                ResultSet r = stm.executeQuery(sql);
                servciosGen.clear();
                while (r.next()) {
                    tblServcios.setItems(servciosGen);

                    String estado = "";
                    switch (r.getString("status").charAt(0)) {
                        case 'T':
                            estado = "Terminado";
                            break;
                        case 'N':
                            estado = "No Atendido";
                            break;
                        case 'E':
                            estado = "En Espera";
                            break;
                        case 'A':
                            estado = "Atendido";
                            break;
                    }

                    servciosGen.add(new ServiciosGen(
                            r.getInt("id"),
                            r.getString("area"),
                            r.getString("descripcion"),
                            r.getString("fecha"),
                            r.getString("nombresolicitante"),
                            r.getString("ubicacion"), estado)); // Asignar el estado en forma de texto
                    clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    clmArea.setCellValueFactory(new PropertyValueFactory<>("area"));
                    clmDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                    clmFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
                    clmNombreS.setCellValueFactory(new PropertyValueFactory<>("nombresolicitante"));
                    clmUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
                    clmEstado.setCellValueFactory(new PropertyValueFactory<>("estado")); // Agregar columna de estado
                }
                stm.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            tblServcios.refresh();
            buscando = false;

        } else {
            sc.sinConexion();
        }


    }

    @FXML
    public void pane() {
        if (paneSerGen.isVisible()) {
            paneSerGen.setVisible(false);
        } else {
            paneSerGen.setVisible(true);
        }
    }

    @FXML
    public void cerrarSesión() {
        HelloApplication.setVista("hello-view");

    }

    @FXML
    public void registarUsuario() {
        HelloApplication.setVista("ventanaNuevoUs");

    }

    @FXML
    public void Vusuarios() {
        HelloApplication.setVista("ventanaVisualizarUsuarios");
    }


}
