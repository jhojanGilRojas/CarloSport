package controllers;

import db.Oracle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnSingUp;

    @FXML
    private PasswordField confirmNewPassword;

    @FXML
    private TextField emailField;

    @FXML
    private TextField newName;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField passwordField;
    private ModelFactoryController modelFactoryController = new ModelFactoryController();


    @FXML
    void onLoginButtonClick(ActionEvent event) throws IOException, SQLException {

    if (validarCampos())  {
    String user = emailField.getText();
    String password = passwordField.getText();
    String[] userData = Oracle.Login(user,password);
    String rol = userData[1];
        if (rol.equals("AFILIADO")){
            modelFactoryController.usuarioLogiado(userData);
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/views/crud-ventas.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.initOwner(btnLogin.getScene().getWindow());
            btnLogin.getScene().getWindow().hide();
            stage.show();
        }
        if (rol.equals("GERENTE_DESPACHOS")){
            modelFactoryController.usuarioLogiado(userData);
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/views/crud-despachos.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.initOwner(btnLogin.getScene().getWindow());
            btnLogin.getScene().getWindow().hide();
            stage.show();
        }
        if (rol.equals("SOPORTE")){
            modelFactoryController.usuarioLogiado(userData);
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/views/crud-ventas.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.initOwner(btnLogin.getScene().getWindow());
            btnLogin.getScene().getWindow().hide();
            stage.show();
        }
        if (rol.equals("ADMIN")){
            modelFactoryController.usuarioLogiado(userData);
            FXMLLoader fxmlLoader = new FXMLLoader(LoginController.class.getResource("/views/admin-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
            Stage stage = new Stage();
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.initOwner(btnLogin.getScene().getWindow());
            btnLogin.getScene().getWindow().hide();
            stage.show();
        }
        else {
            mostrarMensaje("LOGIN","Error al iniciar sesion","El usuario no se encuentra registrado.", Alert.AlertType.ERROR);
        }
    }
    }


    @FXML
    void onSignUpButtonClick(ActionEvent event) {

    }

    private boolean validarCampos() {
        String mensaje = "";
        boolean verificado = true;
        if (emailField.getText() == null|| emailField.getText().equals("")){
            mensaje+= "Debes de llenar el campo del usuario "+'\n';
            verificado = false;
        }
        if (passwordField.getText() == null || passwordField.getText().equals("")){
            mensaje+= "Debes de llenar el campo de contraseña";
            verificado = false;
        }
        if (!verificado){
            mostrarMensaje ("LOGIN","Error al iniciar sesion",mensaje, Alert.AlertType.ERROR);
        }
        return verificado;
    }

    private void limpiarCampos() {
        emailField.setText("");
        passwordField.setText("");
        newName.setText("");
        newPassword.setText("");
        confirmNewPassword.setText("");
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {

        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }

    private boolean mostrarMensajeConfirmacion(String mensaje) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("ConfirmaciÃ³n");
        alert.setContentText(mensaje);
        Optional<ButtonType> action = alert.showAndWait();

        return action.isPresent() && action.get() == ButtonType.OK;

    }

}
