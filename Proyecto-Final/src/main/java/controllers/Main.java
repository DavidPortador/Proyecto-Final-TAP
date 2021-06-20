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
                    valiLogin();
                } catch (SQLException throwables) {
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
    void valiLogin() throws SQLException {
        String user, pass;
        user = txtUser.getText();
        pass = txtPass.getText();
        if(user.isEmpty() || pass.isEmpty()){
            alertMessage("Campos vacios",null,
                    "Revise que todos los campos esten llenos", Alert.AlertType.ERROR);
        }else{
            userDAO.getUsuario(user, pass);
        }
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
}
/*Stage open = (Stage) btnIniciar.getScene().getWindow();
open.setOnShowing(a -> {
    open.close();
});
open.setOnCloseRequest(a -> {
    primaryStage.show();
});*/