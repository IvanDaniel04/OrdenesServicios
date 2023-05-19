package jin.com.edu.ordenesservicios;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class creditosController {
    @FXML
    private Button labCerrar;

    public void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) this.labCerrar.getScene().getWindow();
        stage.close();

    }
}
