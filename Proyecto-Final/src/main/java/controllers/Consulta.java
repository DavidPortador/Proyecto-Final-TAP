package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelos.Usuario;
import modelos.modeloSolicitud;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
public class Consulta implements Initializable {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Stage anterior;
    Usuario medico;
    modeloSolicitud paciente;
    @FXML Label lblUsuario;
    @FXML TextField txtSintomas;
    @FXML Button btnCancelar, btnCrear;
    @FXML ComboBox cbMedicos, cbTipos;
    @FXML DatePicker dpFecha;
    @Override public void initialize(URL location, ResourceBundle resources) {
            llenarTipos();
            initButtons();
    }
    private void initButtons() {
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
            }
        });
        btnCrear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                valiVacio();
            }
        });
    }
    private void valiVacio(){
        try{
            String tipo, sintomas;
            Date fecha;
            sintomas = txtSintomas.getText();
            fecha = Date.valueOf(dateFormatter.format(dpFecha.getValue()));
            tipo = (String) cbTipos.getSelectionModel().getSelectedItem();
            if(tipo == null){
                alertMessage("Error", null, "Selecccione el tipo de consulta", Alert.AlertType.ERROR);
            }else{
                System.out.println(sintomas);
                System.out.println();
            }
        } catch (NullPointerException e){
        alertMessage("Error","createConsulta", e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    private void llenarTipos(){
        ObservableList<String> tipos = FXCollections.observableArrayList();
        tipos.add("Presencial");
        tipos.add("Virtual");
        cbTipos.setItems(tipos);
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setMedico(Usuario usuario){
        medico = usuario;
    }
    public void setSolicitud(modeloSolicitud solicitud){
        paciente = solicitud;
    }
}
