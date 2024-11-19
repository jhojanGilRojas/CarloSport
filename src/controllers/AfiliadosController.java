package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AfiliadosController {

    @FXML
    private ComboBox<?> CbNivel;

    @FXML
    private Button botonBuscarAfiliado;

    @FXML
    private Button botonCrearAfiliado;

    @FXML
    private Button botonEditarAfiliado;

    @FXML
    private Button botonEliminarAfiliado;

    @FXML
    private TableColumn<?, ?> colAcciones;

    @FXML
    private TableColumn<?, ?> colApellido;

    @FXML
    private TableColumn<?, ?> colNivel;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TableColumn<?, ?> colTelefono;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<?> tablaAfiliados;

    @FXML
    private TextField textApellido;

    @FXML
    private TextField textNombre;

    @FXML
    private TextField textTelefono;

}
