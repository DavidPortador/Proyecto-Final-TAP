package controllers;
import database.MySQLConnection;
import database.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import modelos.Usuario;
import modelos.modeloSolicitud;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Solicitud implements Initializable {
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Stage anterior;
    Usuario user;
    @FXML Button btnCrear, btnCancelar;
    @FXML ComboBox cbTipos, cbMedicos;
    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            llenarTipos();
            llenarMedicos();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        initButtons();
    }
    private void initButtons(){
        btnCrear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    valiVacio();
                } catch (SQLException e) {
                    alertMessage("Error", "valiVacio",
                            e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
            }
        });
    }
    private void valiVacio() throws SQLException {
        String estado, tipo, noCedula;
        estado = "Espera";
        tipo = cbTipos.getSelectionModel().getSelectedItem().toString();
        noCedula = cbMedicos.getSelectionModel().getSelectedItem().toString();

        if(tipo != null){
            if(noCedula != null){
                modeloSolicitud solicitud = new modeloSolicitud(
                        0,
                        estado,
                        tipo,
                        userDAO.getcveAsignacion(user.getNoUsuario()),
                        user.getNoUsuario(),
                        noCedula
                        );
                System.out.println();
                if(userDAO.insertNewSolicitud(solicitud)){
                    alertMessage(
                            "Solicitud Exitosa", "Se solicito la consulta de manera exitosa",
                            "Se genero una solicitud de consulta para "+user.getNombres(),
                            Alert.AlertType.INFORMATION);
                }
            }else{
                alertMessage("Error", "Datos incompletos",
                        "No se selecciono ningun medico", Alert.AlertType.ERROR);
            }
        }else{
            alertMessage("Error", "Datos incompletos",
                    "No se selecciono el tipo de consulta", Alert.AlertType.ERROR);
        }



    }
    private void llenarTipos(){
        ObservableList <String> tipos = FXCollections.observableArrayList();
        tipos.add("Presencial");
        tipos.add("Virtual");
        cbTipos.setItems(tipos);
    }
    private void llenarMedicos() throws SQLException {
        cbMedicos.setItems(userDAO.getMedicos());
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
