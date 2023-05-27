package jin.com.edu.ordenesservicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jin.com.edu.ordenesservicios.clases.user;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class usuario implements Initializable {
    ObservableList<user> lis;
    ObservableList<user> lis2;
    ObservableList<user> lis3;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtContraseña;
    @FXML
    private TextField txtContraseñaConf;
    @FXML
    private ComboBox<user> tipo;
    @FXML
    private Pane paneUsr;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtAreaConocimiento;
    @FXML
    private TextField txtEdad;
    @FXML
    private ComboBox<user> sexo;
    @FXML
    private PasswordField psfContraseña;
    @FXML
    private PasswordField psfContraseñaConf;
    @FXML
    private CheckBox mostrarContraseña;
    @FXML
    private ComboBox<user> Cobcorreo;
    @FXML
    private Label labAlconfin;
    @FXML
    private Label labAlEdad;
    @FXML
    private Label labAlNombre;
    @FXML
    private Label labAlApellido;
    @FXML
    private Label labAlAreaCon;
    @FXML
    private Label labAlsexo;
    @FXML
    private Label labAltipo;

    sinConexionController sc = new sinConexionController();
    public void registar() {
        if (EnlaceIvan.isConnectedToInternet()) {
            Connection c = EnlaceIvan.getConexion();
            Boolean bandera = false;
            String cadena = txtCorreo.getText() + Cobcorreo.getSelectionModel().getSelectedItem().toString();
            String correo = "^[\\w-]+(\\\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z0-9]{2,})$";
            String cadena2 = psfContraseña.getText();

            String contrasena = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%&_]).{8,}$";
            String nombre = txtNombre.getText().trim() +" "+ txtApellido.getText().trim();

            Pattern patron = Pattern.compile(correo);
            Matcher mat = patron.matcher(cadena);
            Pattern patron2 = Pattern.compile(contrasena);
            Matcher mat2 = patron2.matcher(cadena2);
            if (txtNombre.getText().isEmpty()) {
                System.out.println("Este campo es obligatorio");
                labAlNombre.setVisible(true);

            } else if (txtAreaConocimiento.getText().isEmpty()) {
                System.out.println("Este campo es obligatorio");
                labAlAreaCon.setVisible(true);

            } else if (txtApellido.getText().isEmpty()) {
                labAltipo.setVisible(true);

            } else if (txtEdad.getText().isEmpty()) {
                System.out.println("Este campo es obligatorio");
                labAlEdad.setVisible(true);

            } else if (tipo.getSelectionModel().isEmpty()) {
                System.out.println("Este campo es obligatorio");
                labAltipo.setVisible(true);

            } else if (sexo.getSelectionModel().isEmpty()) {
                labAlsexo.setVisible(true);

            } else if (mat.matches() && mat2.matches()) {
                try {
                    Statement stme = c.createStatement();
                    String existe = "SELECT * FROM usuarios WHERE correo = '" + cadena + "'";

                    ResultSet r1 = stme.executeQuery(existe);

                    if (r1.next()) {

                        Stage stage1 = new Stage();//Crear una nueva ventana
                        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("usuarioExistente.fxml"));
                        Scene escena1 = new Scene(loader1.load());
                        stage1.setScene(escena1);//agregar la escena de la ventana
                        stage1.initModality(Modality.APPLICATION_MODAL);
                        stage1.setResizable(false);
                        stage1.show();
                    } else if (psfContraseña.getText().equals(txtContraseñaConf.getText()) || psfContraseña.getText().equals(psfContraseñaConf.getText())) {

                        Statement stm = c.createStatement();
                        String sql2 = "INSERT INTO personal VALUES (0,'" + txtAreaConocimiento.getText() + "','" + txtEdad.getText() + "'," +
                                "'" + nombre + "','" + sexo.getSelectionModel().getSelectedItem().toString().charAt(0) + "')";
                        stm.execute(sql2);

                        String sql3 = "SELECT LAST_INSERT_ID()";
                        ResultSet rs = stm.executeQuery(sql3);
                        if (rs.next()) {
                            int idPersonal = rs.getInt(1);
                            String sql4 = "INSERT INTO usuarios VALUES (0,'" + cadena + "','" + cadena2 + "','" +
                                    tipo.getSelectionModel().getSelectedItem().toString().charAt(0) + "'," + idPersonal + ")";
                            stm.execute(sql4);
                        }

                        //HelloApplication.setVista("ventanaVisualizarUsuarios");
                        stm.close();
                        bandera = true;

                        if (bandera) {
                            txtContraseñaConf.setText("");
                            txtContraseña.setText("");
                            txtApellido.setText("");
                            txtCorreo.setText("");
                            txtAreaConocimiento.setText("");
                            txtNombre.setText("");
                            psfContraseñaConf.setText("");
                            psfContraseña.setText("");
                            txtEdad.setText("");
                            tipo.getSelectionModel().select(-1);
                            sexo.getSelectionModel().select(-1);
                            Cobcorreo.getSelectionModel().select(-1);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("REGISTRO EXITOSO!!!!!!");
                            alert.setContentText("¡¡¡¡¡¡ REGISTRO EXITOSO !!!!!!");
                            alert.showAndWait();

                        }
                        r1.close();
                        stme.close();
                    } else {
                        System.out.println("La contraseña no coincide");
                        labAlconfin.setVisible(true);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                }

            } else if (!mat2.find()) {
                System.out.println("contraseña no valida ");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Contraseña no valida");
                alert.setContentText("La contraseña de contener \n * Letras Mayúsulas. \n * Letras minúsculas" +
                        "\n * Números. \n * Símbolos (! @ # $ % & _)\n * Minímo 8 caracteres\n EJEMPLO:  \n HolaMundo123!");
                alert.showAndWait();


            } else if (!mat.find()) {
                System.out.println("ingresa un correo valido");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Correo no valido");
                alert.setContentText("El correo no cumple el formato\n FORMATO: \n example@");
                alert.showAndWait();


            }
        } else {
            sc.sinConexion();
        }
    }

    public void contraseña() {
        if (mostrarContraseña.isSelected()) {
            txtContraseña.setText(psfContraseña.getText());
            txtContraseñaConf.setText(psfContraseñaConf.getText());
            txtContraseñaConf.setVisible(true);
            txtContraseña.setVisible(true);
            psfContraseña.setVisible(false);
            psfContraseñaConf.setVisible(false);
            return;
        }
        psfContraseña.setText(txtContraseña.getText());
        psfContraseñaConf.setText(txtContraseñaConf.getText());
        psfContraseñaConf.setVisible(true);
        psfContraseña.setVisible(true);
        txtContraseña.setVisible(false);
        txtContraseñaConf.setVisible(false);

    }


    public void regresar() {
        HelloApplication.setVista("ventanaServiciosGen");
    }

    @FXML
    public void pane() {
        if (paneUsr.isVisible()) {//detectar doble click
            paneUsr.setVisible(false);
        } else {
            paneUsr.setVisible(true);
        }
    }

    @FXML
    public void cerrarSesión() {
        HelloApplication.setVista("hello-view");

    }

    @FXML
    public void Vusuarios() {
        HelloApplication.setVista("ventanaVisualizarUsuarios");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lis = FXCollections.observableArrayList();
        lis2 = FXCollections.observableArrayList();
        lis3 = FXCollections.observableArrayList();
        lis.add(new user("Administrador"));
        lis.add(new user("Operador"));
        lis2.add(new user("Masculino"));
        lis2.add(new user("Femenino"));
        lis3.add(new user("@gmail.com"));
        lis3.add(new user("@hotmail.com"));
        lis3.add(new user("@chapala.tecmm.edu.mx"));
        lis3.add(new user("@outlook.com"));
        lis3.add(new user("@outlook.exe"));
        tipo.setItems(lis);
        sexo.setItems(lis2);
        Cobcorreo.setItems(lis3);

    }
}
