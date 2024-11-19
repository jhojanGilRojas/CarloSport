package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PromocionesController {

    @FXML
    private Button botonBuscar;

    @FXML
    private Button botonCrear;

    @FXML
    private Button botonEditar;

    @FXML
    private Button botonEliminar;

    @FXML
    private TableColumn<?, ?> colDescripcion;

    @FXML
    private TableColumn<?, ?> colDescuento;

    @FXML
    private TableColumn<?, ?> colFechaFin;

    @FXML
    private TableColumn<?, ?> colFechaInicio;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<?> tablaPromociones;

    @FXML
    private TextField textDescripcion;

    @FXML
    private TextField textDescuento;

    @FXML
    private TextField textNombre;

}
