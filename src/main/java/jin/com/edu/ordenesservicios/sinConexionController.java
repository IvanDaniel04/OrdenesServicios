package jin.com.edu.ordenesservicios;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class sinConexionController {

    public void sinConexion(){
        try {
            Stage stage = new Stage();//Crear una nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sinConexion.fxml"));
            Scene escena = new Scene(loader.load());
            stage.setScene(escena);//agregar la escena de la ventana
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
        } catch (Exception exception) {

        }
    }
}
