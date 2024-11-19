package controllers;

import com.sun.tools.javac.Main;
import db.Oracle;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class VentasController {

    @FXML
    private DatePicker dpFechaDeVenta;

    @FXML
    private Button botonBuscarVenta;

    @FXML
    private Button btnDetalleVenta;

    @FXML
    private Button btnProducto;
    @FXML
    private Button btnEditarVenta;
    @FXML
    private Button btnCancelarVenta;

    @FXML
    private Button btnRegistrarVenta;

    @FXML
    private TableColumn<String[], String> colEstado;

    @FXML
    private TableColumn<String[], String> colComisionAfiliado;

    @FXML
    private TableColumn<String[], String> colFechaVenta;

    @FXML
    private TableColumn<String[],String> colMontoTotal;
    @FXML
    private TableColumn<String[],String> colIdVenta;
    @FXML
    private TableColumn<String[],String> colProductos;
    @FXML
    private TextField filterField;

    @FXML
    private TableView<String[]> tablaVentas;


    ModelFactoryController modelFactoryController;
    int idLogueado;
    String rol;
    String idSeleccion;
    ObservableList<String []> listaVentas = FXCollections.observableArrayList();
    ObservableList<String []> listaProductos = FXCollections.observableArrayList();
    ObservableList<String []> productos = FXCollections.observableArrayList();
    @FXML
    void initialize() throws SQLException {
        modelFactoryController = ModelFactoryController.getInstance();
        idLogueado = Integer.parseInt(modelFactoryController.getUsuarioLogeado()[0]);
        rol = modelFactoryController.getUsuarioLogeado()[1];
        idSeleccion = modelFactoryController.getIdSeleccion();
        dpFechaDeVenta.setValue(LocalDate.now());
        if (rol.equals("GERENTE_DESPACHOS")) {
            btnCancelarVenta.setDisable(true);
            btnEditarVenta.setDisable(true);
            btnRegistrarVenta.setDisable(true);
            listaVentas = Oracle.obtenerTodasVentas();
        }
        if (rol.equals("AFILIADO")) {
            btnCancelarVenta.setDisable(true);
            btnEditarVenta.setDisable(true);
            listaVentas =Oracle.obtenerVentasUsuario(idLogueado);

        }
        if (rol.equals("SOPORTE")) {
            btnCancelarVenta.setDisable(true);
            btnEditarVenta.setDisable(true);
            btnRegistrarVenta.setDisable(true);
            listaVentas = Oracle.obtenerTodasVentas();
        }
        if (listaVentas==null){
            listaVentas = Oracle.obtenerTodasVentas();
        }
        tablaVentas.setItems(listaVentas);
        colIdVenta.setCellValueFactory(listaVentas -> new SimpleStringProperty(listaVentas.getValue()[0]));
        colFechaVenta.setCellValueFactory(listaVentas -> new SimpleStringProperty(listaVentas.getValue()[1]));
        colMontoTotal.setCellValueFactory(listaVentas -> new SimpleStringProperty(listaVentas.getValue()[2]));
        colEstado.setCellValueFactory(listaVentas -> new SimpleStringProperty(listaVentas.getValue()[3]));
        colProductos.setCellValueFactory(listaVentas -> new SimpleStringProperty(listaVentas.getValue()[4]));
        tablaVentas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!tablaVentas.getSelectionModel().isEmpty()) {
                idSeleccion = newValue[0];
                modelFactoryController.setIdSeleccion(idSeleccion);
                dpFechaDeVenta.setValue(LocalDate.parse(newValue[1]));
            }
            tablaVentas.getSelectionModel().clearSelection();
        });
    }


    @FXML
    void onClickAgregarProductos(ActionEvent event) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(VentasController.class.getResource("/views/agregar-producto.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setTitle("Agregar producto");
        stage.setScene(scene);
        stage.initOwner(btnProducto.getScene().getWindow());
        stage.show();
        stage.setOnHiding(event1 -> {
//            try {
//                initialize();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
        });
    }

    @FXML
    void onClickCancelarVenta(ActionEvent event) {

    }

    @FXML
    void onClickDetalle(ActionEvent event) {

    }

    @FXML
    void onClickRegistrarVenta(ActionEvent event) throws SQLException {

        listaProductos = modelFactoryController.getListaAux();
        for (int i = 0; i < listaProductos.size(); i++) {
            System.out.println(Arrays.toString(listaProductos.get(i)));
        }
        if(validarDatos()) {
            try {

                Oracle.crearVenta(String.valueOf(idLogueado), listaProductos);
            } catch (SQLException e) {
                mostrarMensaje("Registro venta", "Error", "no se ha podido registrar la venta", Alert.AlertType.ERROR);
                throw new RuntimeException(e);
            }
            listaVentas = Oracle.obtenerVentasUsuario(idLogueado);
            tablaVentas.setItems(listaVentas);
            tablaVentas.refresh();
        }
    }

    private boolean validarDatos() {

        String mensaje = "";
        boolean valido = true;

        if (listaProductos.isEmpty()){
            mensaje+= "Debes de agregar productos a la venta para poder crearla";
            valido = false;
        }
        if (!valido){
            mostrarMensaje("Gestión venta","Error",mensaje, Alert.AlertType.ERROR);
        }
        return valido;
    }

    @FXML
    void onclickEditarVenta(ActionEvent event) {

    }

////////////////////////////////////////////efectos para botones///////////////////////////////////
    @FXML
    void mouseCancelar(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnCancelarVenta);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
        btnCancelarVenta.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: black;");
    }

    @FXML
    void mouseDetalle(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnDetalleVenta);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
        btnDetalleVenta.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: black;");
    }

    @FXML
    void mouseEditar(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnEditarVenta);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
        btnEditarVenta.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: black;");
    }

    @FXML
    void mouseProductos(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnProducto);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
        btnProducto.setStyle("-fx-background-color: #004aad; -fx-text-fill: black;-fx-border-color: black;");
    }

    @FXML
    void mouseRegistrar(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnRegistrarVenta);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.play();
        btnRegistrarVenta.setStyle("-fx-background-color: white; -fx-text-fill: black;-fx-border-color: black;");
    }


    @FXML
    void mouseExitDetalle(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnDetalleVenta);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
        btnDetalleVenta.setStyle("-fx-background-color: #004aad; -fx-text-fill: white; -fx-border-color: black;");
    }

    @FXML
    void mouseExitEditar(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnEditarVenta);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
        btnEditarVenta.setStyle("-fx-background-color: #004aad; -fx-text-fill: white; -fx-border-color: black;");
    }

    @FXML
    void mouseExitProductos(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnProducto);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
        btnProducto.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-border-color: black;");
    }

    @FXML
    void mouseExitRegistrar(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnRegistrarVenta);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
        btnRegistrarVenta.setStyle("-fx-background-color: #004aad; -fx-text-fill: white; -fx-border-color: black;");

    }
    @FXML
    void mouseExitCancelar(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(10), btnCancelarVenta);
        scaleTransition.setToX(1);
        scaleTransition.setToY(1);
        scaleTransition.play();
        btnCancelarVenta.setStyle("-fx-background-color: #004aad; -fx-text-fill: white; -fx-border-color: black;");
    }
    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }
}