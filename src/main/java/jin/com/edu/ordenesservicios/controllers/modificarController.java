package jin.com.edu.ordenesservicios.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jin.com.edu.ordenesservicios.EnlaceIvan;
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

    //Varibles de Programación
    private ObservableList<Personal> personalLista;
    private ObservableList<Tarea> estado;
    private int id;
    public void setId(int id) {
        this.id = id;
    }
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
        //System.out.println(cbestatus.getSelectionModel().getSelectedItem().toString().charAt(0));

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

    }

}