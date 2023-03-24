package jin.com.edu.ordenesservicios;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
   static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
         scene = new Scene(fxmlLoader.load());
        stage.setTitle("LOGIN!");
        stage.setScene(scene);
        stage.show();
    }
    public static void setVista (String nombre){
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource( nombre + ".fxml"));
        try {
            scene.setRoot(loader.load());
            scene.getWindow().sizeToScene();
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        launch();
    }
}