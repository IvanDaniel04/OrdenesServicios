module jin.com.edu.ordenesservicios {
    requires javafx.controls;
    requires javafx.fxml;


    opens jin.com.edu.ordenesservicios to javafx.fxml;
    exports jin.com.edu.ordenesservicios;
}