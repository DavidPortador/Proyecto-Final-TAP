package controllers;
import database.MySQLConnection;
import database.UserDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelos.Usuario;
import usuarios.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Main implements Initializable {
    /*
    Hay comentarios en el package de usuarios donde dice que les falta a cada tipo de usuario
    Agregar iconos a los botones de TODAS las interfaces (si es necesario)
     */
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    @FXML TextField txtUser;
    @FXML PasswordField txtPass;
    @FXML Button btnIniciar, btnCrear;
    @Override public void initialize(URL location, ResourceBundle resources) {
        initButtons();
    }
    void initButtons() {
        btnIniciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try {
                    valiLogin(event);
                } catch (SQLException | IOException e) {
                    alertMessage("Error","valiLogin", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
        btnCrear.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try {
                    showRegistro(event);
                } catch (IOException e) {
                    alertMessage("Error","crear cuenta", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }
    void valiLogin(ActionEvent event) throws SQLException, IOException {
        String user, pass, tipoUsuario = null;
        Usuario usuario;
        user = txtUser.getText();
        pass = txtPass.getText();
        if(user.isEmpty() || pass.isEmpty())
            alertMessage("Campos vacios",null,
                    "Revise que todos los campos esten llenos", Alert.AlertType.ERROR);
        else{
            tipoUsuario = userDAO.getAsignacion(user, pass);
            usuario = userDAO.getUsuarioLogin(user, pass);
            if(tipoUsuario == null){
                alertMessage("Usuario Incorrecto",null,
                        "Revise que los datos sean correctos", Alert.AlertType.ERROR);
                txtPass.setText("");
            }else{
                // System.out.println(tipoUsuario);
                if(tipoUsuario.equals("Estudiante")){
                    // System.out.println("*Interfaz de estudiante*");
                    vaciar();
                    showEstudiante(event, usuario);
                }else if(tipoUsuario.equals("Personal")){
                    // System.out.println("*Interfaz de personal*");
                    vaciar();
                    showPersonal(event, usuario);
                }else if(tipoUsuario.equals("Medico")){
                    // System.out.println("*Interfaz de medico*");
                    vaciar();
                    showMedico(event, usuario);
                }else if(tipoUsuario.equals("Administrador")){
                    // System.out.println("*Interfaz de admin*");
                    vaciar();
                    showAdministrador(event, usuario);
                }else if(tipoUsuario.equals("Monitoreo")){
                    // System.out.println("*Interfaz de monitoreo*");
                    vaciar();
                    showMonitoreo(event, usuario);
                }else if(tipoUsuario.equals("Directivo")){
                    // System.out.println("*Interfaz de directivo*");
                    vaciar();
                    showDirectivo(event, usuario);
                }
            }
        }
    }
    void showRegistro(ActionEvent event) throws IOException {
        Stage resgistro = new Stage();
        resgistro.setTitle("Registro");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/crearCuenta.fxml"));
        Register register = new Register();
        loader.setController(register);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        resgistro.setMaximized(true);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        register.setStageAnterior(actual);
        actual.close();
        // Muestra el nuevo stage
        resgistro.setScene(scene);
        resgistro.show();
    }
    void showEstudiante(ActionEvent event, Usuario usuario) throws IOException {
        Stage estudiantes = new Stage();
        estudiantes.setTitle("Interfaz de Estudiante");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/estudiantes.fxml"));
        Estudiantes estudiante = new Estudiantes();
        estudiante.setUsuario(usuario);
        loader.setController(estudiante);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        estudiantes.setMaximized(true);
        estudiantes.setScene(scene);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        estudiante.setStageAnterior(actual);
        // Muestra el nuevo stage
        actual.close();
        estudiantes.show();
    }
    void showPersonal(ActionEvent event, Usuario usuario) throws IOException {
        Stage SPersonal = new Stage();
        SPersonal.setTitle("Interfaz de Personal");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/personal.fxml"));
        Personal personal = new Personal();
        personal.setUsuario(usuario);
        loader.setController(personal);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        SPersonal.setMaximized(true);
        SPersonal.setScene(scene);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        personal.setStageAnterior(actual);
        // Muestra el nuevo stage
        actual.close();
        SPersonal.show();
    }
    void showMedico(ActionEvent event, Usuario usuario) throws IOException {
        Stage medicos = new Stage();
        medicos.setTitle("Interfaz de Medicos");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/medicos.fxml"));
        Medicos medico = new Medicos();
        medico.setUsuario(usuario);
        loader.setController(medico);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        medicos.setMaximized(true);
        medicos.setScene(scene);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        medico.setStageAnterior(actual);
        // Muestra el nuevo stage
        actual.close();
        medicos.show();
    }
    void showAdministrador(ActionEvent event, Usuario usuario) throws IOException {
        Stage admin = new Stage();
        admin.setTitle("Interfaz de Administrador");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/administrador.fxml"));
        // Controlador de la interfaz y usuario
        Administradores administrador = new Administradores();
        administrador.setUsuario(usuario);
        loader.setController(administrador);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        admin.setMaximized(true);
        admin.setScene(scene);
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        // Le pasa como parametro el stage actual y nueva
        administrador.setStageAnterior(actual);
        // Muestra el nuevo stage
        actual.close();
        admin.show();
    }
    void showMonitoreo(ActionEvent event, Usuario usuario) throws IOException {
        Stage monitoreo = new Stage();
        monitoreo.setTitle("Interfaz de Monitoreo");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/monitoreo.fxml"));
        Monitoreadores monitoreadores = new Monitoreadores();
        monitoreadores.setUsuario(usuario);
        loader.setController(monitoreadores);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        monitoreo.setMaximized(true);
        monitoreo.setScene(scene);
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        // Le pasa como parametro el stage actual y nueva
        monitoreadores.setStageAnterior(actual);
        // Muestra el nuevo stage
        actual.close();
        monitoreo.show();
    }
    void showDirectivo(ActionEvent event, Usuario usuario) throws IOException {
        Stage directivo = new Stage();
        directivo.setTitle("Interfaz de Directivo");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/directivo.fxml"));
        Directivos directivos = new Directivos();
        directivos.setUsuario(usuario);
        loader.setController(directivos);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        directivo.setMaximized(true);
        directivo.setScene(scene);
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        // Le pasa como parametro el stage actual y nueva
        directivos.setStageAnterior(actual);
        // Muestra el nuevo stage
        actual.close();
        directivo.show();
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    void vaciar(){
        txtUser.setText("");
        txtPass.setText("");
    }
}
