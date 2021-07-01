package controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modelos.Usuario;
import java.net.URL;
import java.util.ResourceBundle;
public class Solicitud implements Initializable {
    Stage anterior;
    Usuario user;

    @Override public void initialize(URL location, ResourceBundle resources) {
        /*btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });*/
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setUsuario(Usuario usuario){
        user = usuario;
    }
}
