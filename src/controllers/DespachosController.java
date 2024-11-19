package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DespachosController {

    @FXML
    private ComboBox<?> CbEstado;

    @FXML
    private ComboBox<?> CbProducto;

    @FXML
    private Button botonActualizarDespacho;

    @FXML
    private Button botonBuscarDespacho;

    @FXML
    private Button botonCrearDespacho;

    @FXML
    private Button botonEliminarDespacho;

    @FXML
    private TableColumn<?, ?> colAcciones;

    @FXML
    private TableColumn<?, ?> colCantidad;

    @FXML
    private TableColumn<?, ?> colDestino;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colProducto;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<?> tablaDespachos;

    @FXML
    private TextField textCantidad;

    @FXML
    private TextField textDestino;

}
