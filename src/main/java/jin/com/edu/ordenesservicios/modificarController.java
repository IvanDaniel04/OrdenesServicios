package jin.com.edu.ordenesservicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import jin.com.edu.ordenesservicios.clases.Personal;
import jin.com.edu.ordenesservicios.clases.Tarea;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class modificarController {
    //Variables Gráficas

    @FXML Label labNoServicio;
    @FXML Label labFregistro;
    @FXML TextField txtTE;
    @FXML ComboBox<Personal> cbOperadores;
    @FXML ComboBox cbestatus;
    @FXML DatePicker dpFinalizacion;
    @FXML TextArea txaDescripcion;
    @FXML TextArea txaObservaciones;
    @FXML Button btnRegresar;

    //Varibles de Programación
    private ObservableList<Personal> personalLista;
    private ObservableList<Tarea> estado;

    public void setId(int id) {
        this.id = id;
    }
    private int id;
    @FXML
    public void datosCombo(){
        try {
            Connection c = EnlaceIvan.getConexion();
            Statement stm = c.createStatement();
            String sql = "SELECT * FROM personal";
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
    }
    @FXML
    public void modifcarOrden(){
        try{
            Connection c = EnlaceIvan.getConexion();
            Statement stm = c.createStatement();
            String sql = "UPDATE tareaservico SET status = '"+cbestatus.getSelectionModel().getSelectedItem().toString().charAt(0)+"',fecha = '"+dpFinalizacion.getValue()+"',observacion = '"+txaObservaciones.getText()+"',tiempoestimado = '"+txtTE.getText()+"',solicitud = '"+labNoServicio.getText()+"', personal = '"+cbOperadores.getSelectionModel().getSelectedItem().toString2()+"' WHERE id = '"+labNoServicio.getText()+"';";
            System.out.println(stm.executeUpdate(sql));
            stm.close();
        }catch (Exception e){
            System.out.println(e);
            System.out.println("NO FUNCIONA LA CONSULTA");
        }
    }
    public void initialize() {
        personalLista = FXCollections.observableArrayList();
        estado = FXCollections.observableArrayList();
        estado.add(new Tarea("En Espera"));
        estado.add(new Tarea("Atendido"));
        estado.add(new Tarea("No Atendido"));
        datosCombo();
        cbOperadores.setItems(personalLista);
        cbestatus.setItems(estado);
        System.out.println(id);
        DropShadow sombra = new DropShadow();
        btnRegresar.setOnMouseEntered(e -> btnRegresar.setEffect(sombra));
        btnRegresar.setOnMouseExited(e -> btnRegresar.setEffect(null));
        txaObservaciones.setWrapText(true);
        txaDescripcion.setWrapText(true);
    }

    public void regresar(){
        HelloApplication.setVista("ventanaServiciosGen");
    }

}