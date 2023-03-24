module jin.com.edu.ordenesservicios {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens jin.com.edu.ordenesservicios to javafx.fxml;
    exports jin.com.edu.ordenesservicios;
    opens jin.com.edu.ordenesservicios.controllers to javafx.fxml;
    exports jin.com.edu.ordenesservicios.controllers;

    opens jin.com.edu.ordenesservicios.clases;
    exports jin.com.edu.ordenesservicios.clases;

}