package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminViewController {

    @FXML
    private AnchorPane aDespachos;

    @FXML
    private AnchorPane aProductos;

    @FXML
    private AnchorPane aPromociones;

    @FXML
    private AnchorPane aUsuarios;

    @FXML
    private AnchorPane aVentas;

    @FXML
    void initialize() throws IOException {
        cargarVistaEnAnchorPane("crud-afiliados.fmxl",aUsuarios);
    }
    private void cargarVistaEnAnchorPane(String fxmlFile, AnchorPane anchorPane) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        AnchorPane vista = loader.load();  // Cargar la vista
        anchorPane.getChildren().setAll(vista);  // Colocar la vista en el AnchorPane
    }

}
