package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductosController {

    @FXML
    private Button botonActualizarProducto;

    @FXML
    private Button botonBuscarProducto;

    @FXML
    private Button botonCrearProducto;

    @FXML
    private Button botonEliminarProducto;

    @FXML
    private Button botonVerDetallesProducto;

    @FXML
    private TableColumn<?, ?> colAcciones;

    @FXML
    private TableColumn<?, ?> colNombreProducto;

    @FXML
    private TableColumn<?, ?> colPrecio;

    @FXML
    private TableColumn<?, ?> colStock;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<?> tablaProductos;

    @FXML
    private TextField textNombreProducto;

    @FXML
    private TextField textPrecio;

    @FXML
    private TextField textStock;

}
