package controllers;
import Database.MySQLConnection;
import Database.UserDAO;
import encuesta.Encuesta;
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
import org.kordamp.bootstrapfx.BootstrapFX;
import usuarios.Medicos;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Main implements Initializable {
    String a_modalidad;
    @FXML TextField txtUser;
    @FXML PasswordField txtPass;
    @FXML Button btnIniciar, btnCrear;
    /*
    Cada que entre aun estudiante/personal revisar si tiene alertas
    */
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    @Override public void initialize(URL location, ResourceBundle resources) {
        initData();
        initGUI();
    }
    void initData(){

    }
    void initGUI() {
        btnIniciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                /*try {
                    showEncuesta(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                try {
                    valiLogin(event);
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        btnCrear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(getRandom());
                try {
                    showRegistro(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void showEncuesta(ActionEvent event) throws IOException {
        String v_personal;
        v_personal = "'aqui va el personal'";
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Encuesta "+v_personal);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/encuesta.fxml"));
        Encuesta encuesta = new Encuesta();
        encuesta.setPersonal(v_personal);
        loader.setController(encuesta);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setResizable(false);
        // Le pasa como parametro el stage actual
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        encuesta.setStageAnterior(actual);
        actual.close();
        // Muestra el nuevo stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    void showRegistro(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Registro");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/registro.fxml"));
        Register register = new Register();
        loader.setController(register);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        // Le pasa como parametro el stage actual
        Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        stage.close();
        // Muestra el nuevo stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    void valiLogin(ActionEvent event) throws SQLException, IOException {
        String user, pass, tipoUsuario = null;
        user = txtUser.getText();
        pass = txtPass.getText();
        if(user.isEmpty() || pass.isEmpty()){
            alertMessage("Campos vacios",null,
                    "Revise que todos los campos esten llenos", Alert.AlertType.ERROR);
        }else{
            tipoUsuario = userDAO.getUsuario(user, pass);
            if(tipoUsuario == null){
                alertMessage("Usuario Incorrecto",null,
                        "Revise que los datos sean correctos", Alert.AlertType.ERROR);
                txtPass.setText("");
            }else{
                System.out.println(tipoUsuario);
                if(tipoUsuario.equals("Medico")){
                    System.out.println("*Interfaz de medico*");
                    vaciar();
                    showMedico(event);
                }else if(tipoUsuario.equals("Administrador")){
                    System.out.println("*Interfaz de admin*");
                    vaciar();
                }else if(tipoUsuario.equals("Monitoreo")){
                    System.out.println("*Interfaz de monitoreo*");
                    vaciar();
                }else if(tipoUsuario.equals("Directivo")){
                    System.out.println("*Interfaz de directivo*");
                    vaciar();
                }
            }
        }
    }
    void showMedico(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Medico");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/medicos.fxml"));
        Medicos medico = new Medicos();
        loader.setController(medico);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setResizable(false);
        // Le pasa como parametro el stage actual
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        medico.setStageAnterior(actual);
        actual.close();
        // Muestra el nuevo stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    int getRandom(){
        int v_random;
        v_random = (int) (Math.floor(Math.random() * (3 - 1 + 1)) + 1);
        return  v_random;
    }
    void vaciar(){
        txtUser.setText("");
        txtPass.setText("");
    }
}
/*Stage open = (Stage) btnIniciar.getScene().getWindow();
open.setOnShowing(a -> {
    open.close();
});
open.setOnCloseRequest(a -> {
    primaryStage.show();
});*/