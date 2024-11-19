package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ModelFactoryController {

    static String [] usuarioLogeado = new String[1] ;
    static String idSeleccion;
    static ObservableList<String[]> listaAux = FXCollections.observableArrayList();

    public void usuarioLogiado(String [] usuarioIniciado) {
        setUsuarioLogeado(usuarioIniciado);
    }
    public void idSeleccionado(String objectoSeleccionado){
        setIdSeleccion(objectoSeleccionado);
    }

    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        // El constructor de Singleton puede ser llamado desde aquí al ser protected
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    public static String[] getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public static String getIdSeleccion() {
        return idSeleccion;
    }

    public static ObservableList<String[]> getListaAux() {
        return listaAux;
    }

    public static void setListaAux(ObservableList<String[]> listaAux) {
        ModelFactoryController.listaAux = listaAux;
    }

    public static void setUsuarioLogeado(String [] usuarioLogeado) {
        ModelFactoryController.usuarioLogeado = usuarioLogeado;
    }

    public static void setIdSeleccion(String idSeleccion) {
        ModelFactoryController.idSeleccion = idSeleccion;
    }


    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
    }
}
