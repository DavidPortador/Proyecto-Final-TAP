package controllers;
import database.ConsultaDAO;
import database.MySQLConnection;
import database.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelos.Usuario;
import modelos.modeloConsulta;
import modelos.modeloSolicitud;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
public class Consulta implements Initializable {
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    Stage anterior;
    Usuario medico;
    modeloSolicitud paciente;
    @FXML Label lblUsuario;
    @FXML TextField txtSintomas, txtHora;
    @FXML Button btnCancelar, btnCrear;
    @FXML ComboBox cbTipos;
    @FXML DatePicker dpFecha;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(medico.getNombres()+" "+medico.getApellidos());
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
                valiVacio(event);
            }
        });
    }
    private void valiVacio(ActionEvent event){
        try{
            String tipo, sintomas ,hora;
            Date fecha;
            sintomas = txtSintomas.getText();
            hora = txtHora.getText();
            fecha = Date.valueOf(dateFormatter.format(dpFecha.getValue()));
            tipo = (String) cbTipos.getSelectionModel().getSelectedItem();
            if(tipo == null){
                alertMessage("Error", null, "Selecccione el tipo de consulta", Alert.AlertType.ERROR);
            }else{
                if(sintomas.isEmpty() || hora.isEmpty()){
                    alertMessage("Error", null, "Campos vacios", Alert.AlertType.ERROR);
                }else{
                    modeloConsulta consulta = new modeloConsulta(
                            0, sintomas, fecha, hora, tipo, paciente.getCveAsignacion(),
                            paciente.getNoUsuario(), userDAO.getnoCedula(medico.getNoUsuario()));
                    if(consultaDAO.insertNewConsulta(consulta)){
                        if(consultaDAO.setEstadoSolicitud(paciente.getNoSolicitud())){
                            alertMessage("Exitoso", null, "Consulta agregada", Alert.AlertType.INFORMATION);
                            Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                            stage.close();
                        }
                    }
                }
            }
        } catch (NullPointerException | SQLException e){
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
