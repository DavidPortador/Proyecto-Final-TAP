package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class Main implements Initializable {
    ObservableList<String> personal;
    @FXML
    Button btnAcceder;
    @FXML
    ComboBox cbxPersonal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initGUI();
        initData();
    }

    void initGUI() {
        btnAcceder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showEncuesta();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void showEncuesta() throws IOException {
        String v_personal;
        v_personal = cbxPersonal.getSelectionModel().getSelectedItem().toString();
        System.out.println(v_personal);
        if(v_personal.equals("Personal")){
            sendMessage("No se selecciono un Personal","Por favor seleccione el tipo de personal");
        }else{
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Proyecto Final Papus");
            Parent v_root = FXMLLoader.load(getClass().getResource("/encuesta.fxml"));
            Scene v_sc = new Scene(v_root);
            v_sc.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            primaryStage.setResizable(false);
            primaryStage.setScene(v_sc);
            primaryStage.show();
        }
    }

    void initData(){
        personal = FXCollections.observableArrayList();
        personal.add("xdxdxdxd");
        personal.add(":000");
        cbxPersonal.setItems(personal);
    }

    void sendMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}