package jin.com.edu.ordenesservicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jin.com.edu.ordenesservicios.clases.Personal;
import jin.com.edu.ordenesservicios.clases.Tarea;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class modificarController {
    //Variables Gráficas

    @FXML
    Label labNoServicio;
    @FXML
    Label labFregistro;
    @FXML
    TextField txtTE;
    @FXML
    ComboBox<Personal> cbOperadores;
    @FXML
    ComboBox cbestatus;
    @FXML
    DatePicker dpFinalizacion;
    @FXML
    TextArea txaDescripcion;
    @FXML
    TextArea txaObservaciones;
    @FXML
    Button btnRegresar;

    //Varibles de Programación
    sinConexionController sc = new sinConexionController();
    private ObservableList<Personal> personalLista;
    private ObservableList<Tarea> estado;
    private int id;

    public void setID(int id) {
        this.id = id;
        labNoServicio.setText(String.valueOf(id));
    }

    public int getId() {
        return id;
    }

    @FXML
    public void datosCombo() {
        if (EnlaceIvan.isConnectedToInternet()) {
            try {
                Connection c = EnlaceIvan.getConexion();
                Statement stm = c.createStatement();
                String sql = "SELECT * FROM personal WHERE id IN (SELECT idPersonal FROM usuarios WHERE tipo != 'A')";
                ResultSet r = stm.executeQuery(sql);
                personalLista.clear();
                while (r.next()) {
                    personalLista.add(new Personal(r.getInt("id"), r.getString("areaConocimiento"),
                            r.getInt("edad"), r.getString("nombre"), r.getString("sexo").charAt(0)));
                }
                stm.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            sc.sinConexion();
        }

    }

    public void seleccionarPersonalEnComboBox(String nombrePersonal) {
        for (Personal p : personalLista) {
            if (p.getNombre().equals(nombrePersonal)) {
                cbOperadores.setValue(p);
                break;
            }
        }
    }

    public void seleccionarEstado(char letra) {
        for (Tarea t : estado) {
            if (t.toString().charAt(0) == letra) {
                cbestatus.setValue(t);
            }
        }
    }

    @FXML
    public void modifcarOrden() {
        if (EnlaceIvan.isConnectedToInternet()) {
            try {
                Connection c = EnlaceIvan.getConexion();
                Statement stm = c.createStatement();
                LocalDate fecha = dpFinalizacion.getValue();
                String observacion = txaObservaciones.getText();
                String tiempoEstimado = txtTE.getText();
                String solicitud = labNoServicio.getText();


                // Verificar si alguno de los campos está vacío o sin valor
                if (cbestatus.getSelectionModel().isEmpty() || fecha == null || observacion.isEmpty() || tiempoEstimado.isEmpty() || solicitud.isEmpty() || cbOperadores.getSelectionModel().isEmpty()) {

                    try {
                        Stage stage = new Stage();//Crear una nueva ventana
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("camposVacios.fxml"));
                        Scene escena = new Scene(loader.load());
                        stage.setScene(escena);//agregar la escena de la ventana
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setResizable(false);
                        stage.show();
                    } catch (Exception exception) {

                    }
                    return;
                }
                String sql = "UPDATE tareaservico SET status = '" + cbestatus.getSelectionModel().getSelectedItem().toString().charAt(0) + "', fecha = '" + fecha + "', observacion = '" + observacion + "', tiempoestimado = '" + tiempoEstimado + "', solicitud = '" + solicitud + "', personal = '" + cbOperadores.getSelectionModel().getSelectedItem().toString2() + "' WHERE id = '" + solicitud + "';";
                System.out.println(stm.executeUpdate(sql));
                stm.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            regresar();
        } else {
            sc.sinConexion();
        }

    }

    public void initialize() {
        personalLista = FXCollections.observableArrayList();
        estado = FXCollections.observableArrayList();
        estado.add(new Tarea("En Espera"));
        estado.add(new Tarea("Atendido"));
        estado.add(new Tarea("No Atendido"));
        estado.add(new Tarea("Terminado"));
        datosCombo();
        cbOperadores.setItems(personalLista);
        cbestatus.setItems(estado);
        DropShadow sombra = new DropShadow();
        btnRegresar.setOnMouseEntered(e -> btnRegresar.setEffect(sombra));
        btnRegresar.setOnMouseExited(e -> btnRegresar.setEffect(null));
        txaObservaciones.setWrapText(true);
        txaDescripcion.setWrapText(true);
    }

    public void regresar() {
        Stage stage = (Stage) btnRegresar.getScene().getWindow();
        stage.close();

    }

}