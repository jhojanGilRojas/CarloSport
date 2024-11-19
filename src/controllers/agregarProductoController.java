package controllers;

import db.Oracle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

public class agregarProductoController {

    @FXML
    private Button btnAgregarProducto;

    @FXML
    private Button btnAgregarVenta;

    @FXML
    private Button btnCancelar;

    @FXML
    private AnchorPane apAgregarProductos;
    @FXML
    private ComboBox<String[]> cbProductos;

    @FXML
    private TableColumn<String[], String> colDescripcion;

    @FXML
    private TableColumn<String[], String> colNombre;

    @FXML
    private TableColumn<String[], String> colPrecio;

    @FXML
    private TableColumn<String[], String> colCantidad;
    @FXML
    private TableColumn<String[], String> colId;

    @FXML
    private TableColumn<String[], String> colStock;

    @FXML
    private Spinner<Integer> spCantidad;

    @FXML
    private TableView<String[]> tablaProductos;

    ModelFactoryController modelFactoryController;
    ObservableList<String[]> listaProductos = FXCollections.observableArrayList();
    ObservableList<String[]> comboBoxProductos = FXCollections.observableArrayList();
    String idSelecionado;
    @FXML
    void initialize(){
    modelFactoryController = ModelFactoryController.getInstance();
    idSelecionado = modelFactoryController.getIdSeleccion();
    modelFactoryController.setListaAux(listaProductos);
    comboBoxProductos = Oracle.obtenerAllProductos();
    cbProductos.setItems(comboBoxProductos);
        cbProductos.setConverter(new StringConverter<String[]>() {
            @Override
            public String toString(String[] producto) {
                return producto != null ? producto[1] : "";
            }
            @Override
            public String[] fromString(String string) {
                return null;
            }
        });

    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0, 1);
    spCantidad.setValueFactory(valueFactory);
    if (idSelecionado == null || idSelecionado.equals("")){
    }
    else {
        listaProductos = Oracle.obtenerProductoId(Integer.parseInt(idSelecionado));
    }
        tablaProductos.setItems(listaProductos);
        colId.setCellValueFactory(listaProductos -> new SimpleStringProperty(listaProductos.getValue()[0]));
        colNombre.setCellValueFactory(listaProductos -> new SimpleStringProperty(listaProductos.getValue()[1]));
        colDescripcion.setCellValueFactory(listaProductos -> new SimpleStringProperty(listaProductos.getValue()[2]));
        colPrecio.setCellValueFactory(listaProductos -> new SimpleStringProperty(listaProductos.getValue()[3]));
        colStock.setCellValueFactory(listaProductos -> new SimpleStringProperty(listaProductos.getValue()[4]));
        colCantidad.setCellValueFactory(listaProductos -> new SimpleStringProperty(listaProductos.getValue()[5]));
        tablaProductos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            idSelecionado = newValue[0];
            modelFactoryController.setIdSeleccion(idSelecionado);
        });

    }

    @FXML
    void onClickAgregarProducto(ActionEvent event) {
        if(validarDatos()){
            String[] nuevoProducto = null;
            try {
                nuevoProducto = new String[]{
                        cbProductos.getValue()[0],
                        cbProductos.getValue()[1],
                        cbProductos.getValue()[2],
                        cbProductos.getValue()[3],
                        cbProductos.getValue()[4],
                        spCantidad.getValue().toString(),
                };

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            listaProductos.add(nuevoProducto);
//            listaProductos = modelFactoryController.getListaAux();
            modelFactoryController.setListaAux(listaProductos);

            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0, 1);
            spCantidad.setValueFactory(valueFactory);
            tablaProductos.refresh();
        }
    }

    @FXML
    void onClickAgregarVenta(ActionEvent event) throws SQLException {
    if(tablaProductos!=null){
        btnAgregarVenta.getScene().getWindow().hide();
    }else {
        mostrarMensaje("Agregar producto","Error","Debes agregar al menos un producto para poder registrarlo", Alert.AlertType.ERROR);
    }
    }

    @FXML
    void onClickCancelar(ActionEvent event) {

    }

    private boolean validarDatos() {
        String mensaje = "";
        boolean valido = true;
        if (cbProductos.getValue()== null){
            mensaje+= "Selecione un producto para poder agregarlo a la venta"+"\n";
            valido = false;
        }
        if (spCantidad.getValue()==0){
            mensaje+= "La cantidad debe ser mayor a 0";
            valido = false;
        }
        if (!valido){
            mostrarMensaje("Agregar Productos","Error",mensaje, Alert.AlertType.ERROR);
        }
        return valido;
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }


///////////////////////Efectos botones////////////////////////////
    @FXML
    void mouseAgregarProducto(MouseEvent event) {

    }

    @FXML
    void mouseAgregarVenta(MouseEvent event) {

    }

    @FXML
    void mouseCancelar(MouseEvent event) {

    }

    @FXML
    void mouseExitAgregarVenta(MouseEvent event) {

    }

    @FXML
    void mouseExitAgregrarProducto(MouseEvent event) {

    }

    @FXML
    void mouseExitCancelar(MouseEvent event) {

    }


}
