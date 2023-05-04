package jin.com.edu.ordenesservicios;

import jin.com.edu.ordenesservicios.clases.ServiciosGen;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TablaObservable extends Observable {
    private List<ServiciosGen> servicios = new ArrayList<>();

    public void addServicio(ServiciosGen servicio) {
        servicios.add(servicio);
        setChanged();
        notifyObservers(servicios);
    }

    public List<ServiciosGen> getServicios() {
        return servicios;
    }

    public void eliminarServicios(List<ServiciosGen> servicios) {
        this.servicios.removeAll(servicios);
        notifyObservers();
    }

    public void setServicios(List<ServiciosGen> servicios) {
        this.servicios = servicios;
        setChanged();
        notifyObservers(servicios);
    }
}



