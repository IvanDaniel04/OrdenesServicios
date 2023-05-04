package jin.com.edu.ordenesservicios;

import javafx.scene.control.TableView;
import jin.com.edu.ordenesservicios.clases.ServiciosGen;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TablaObserver implements Observer {
    private TableView<ServiciosGen> tabla;

    public TablaObserver(TableView<ServiciosGen> tabla) {
        this.tabla = tabla;
    }

    @Override
    public void update(Observable o, Object arg) {
        List<ServiciosGen> servicios = (List<ServiciosGen>) arg;
        tabla.getItems().setAll(servicios);
    }
}


