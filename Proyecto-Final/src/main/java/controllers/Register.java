package controllers;
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
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Register implements Initializable {
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Stage anterior;
    @FXML ComboBox cbTipoUsuario, cbAsignacion, cbGenero;
    @FXML TextField txtNo_PE, txtcveAsignacion, txtUsuario, txtNombres, txtApellidos, txtCorreo;
    @FXML Button btnCrear, btnCancelar;
    @FXML Label lblCD, lblNCP;
    @FXML PasswordField psfContra;
    @FXML DatePicker dpFecha;
    @Override public void initialize(URL location, ResourceBundle resources) {
        llenarTipoUsuario();
        llenarGenero();
        defaultMode();
        initCombo();
        initButtons();
        //cbTipoUsuario.setOnAction(e -> System.out.println("Action Nueva Selección: " + cbTipoUsuario.getValue()));
    }
    private void initButtons() {
        btnCrear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                valiVacio();
            }
        });
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
    }
    private void initCombo() {
        cbTipoUsuario.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try {
                    String tipoUsuario;
                    tipoUsuario = cbTipoUsuario.getValue().toString();
                    System.out.println("Action Nueva Selección: " + tipoUsuario);
                    if(tipoUsuario.equals("Estudiante")){
                        estudianteMode();
                    }else if(tipoUsuario.equals("Personal")){
                        personalMode();
                    }
                } catch (SQLException e) {
                    alertMessage("Error",null, e.getMessage(), Alert.AlertType.ERROR);
                }

            }
        });
    }
    private void llenarTipoUsuario() {
        ObservableList<String> generos = FXCollections.observableArrayList();
        generos.add("Estudiante");
        generos.add("Personal");
        cbTipoUsuario.setItems(generos);
    }
    private void llenarGenero() {
        ObservableList<String> generos = FXCollections.observableArrayList();
        generos.add("M");
        generos.add("F");
        cbGenero.setItems(generos);
    }
    private void estudianteMode() throws SQLException {
        cbTipoUsuario.setValue("Estudiante");
        cbAsignacion.getItems().clear();
        lblCD.setText("Carrera");
        cbAsignacion.setItems(userDAO.getCarreras());
        editMode();
    }
    private void personalMode() throws SQLException {
        cbTipoUsuario.setValue("Personal");
        cbAsignacion.getItems().clear();
        lblCD.setText("Departamento");
        cbAsignacion.setItems(userDAO.getDepas());
        editMode();
    }
    private void defaultMode(){
        cbTipoUsuario.setDisable(false);
        cbAsignacion.setDisable(true);
        txtNo_PE.setDisable(true);
        txtcveAsignacion.setDisable(true);
        txtUsuario.setDisable(true);
        psfContra.setDisable(true);
        txtNombres.setDisable(true);
        txtApellidos.setDisable(true);
        cbGenero.setDisable(true);
        txtCorreo.setDisable(true);
        dpFecha.setDisable(true);
    }
    private void editMode(){
        cbTipoUsuario.setDisable(false);
        cbAsignacion.setDisable(false);
        txtNo_PE.setDisable(false);
        txtcveAsignacion.setDisable(false);
        txtUsuario.setDisable(false);
        psfContra.setDisable(false);
        txtNombres.setDisable(false);
        txtApellidos.setDisable(false);
        cbGenero.setDisable(false);
        txtCorreo.setDisable(false);
        dpFecha.setDisable(false);

    }
    private boolean valiVacio(){
        boolean bandera = false;
        if(txtNo_PE.getText().isEmpty()){
            alertMessage("noPE",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else{
            bandera = true;
        }
        return bandera;
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
}
