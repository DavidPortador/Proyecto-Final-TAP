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
import modelos.Usuario;
import modelos.modeloAsignacion;
import modelos.modeloEstudiante;
import modelos.modeloPersonal;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
public class Register implements Initializable {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
                /*if(valiVacio()){
                    try {
                        if(userDAO.getUsuarioLogin(txtUsuario.getText(),psfContra.getText()).getUsuario() != null){
                            alertMessage("Error","El Usuario ya exite", null,Alert.AlertType.ERROR);
                        }
                        try{
                            Usuario newUsuario = new Usuario(0,
                                    txtUsuario.getText(),
                                    psfContra.getText(),
                                    txtNombres.getText(),
                                    txtApellidos.getText(),
                                    cbGenero.getSelectionModel().getSelectedItem().toString(),
                                    txtCorreo.getText(),
                                    Date.valueOf(dateFormatter.format(dpFecha.getValue()))
                            );
                            modeloAsignacion newAsignacion = new modeloAsignacion(
                                    txtcveAsignacion.getText(),
                                    cbAsignacion.getSelectionModel().getSelectedItem().toString()
                            );
                            modeloEstudiante newEstudiante = new modeloEstudiante(
                                    txtNo_PE.getText(),
                                    txtcveAsignacion.getText(),
                                    newUsuario.getNoUsuario(),
                                    cbAsignacion.getSelectionModel().getSelectedItem().toString()
                            );
                            System.out.println(newUsuario.getUsuario());
                            System.out.println(newAsignacion.getCveAsignacion());
                            System.out.println(newEstudiante.getCveCarrera());
                        }catch (NullPointerException e){
                            alertMessage("Error Dialog","Error in the consult", "Revisar fechas, error "+e.getMessage(),Alert.AlertType.ERROR);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }*/
                try{
                    Usuario valiUser = userDAO.getUsuarioLogin(txtUsuario.getText(),psfContra.getText());
                    if(valiVacio()){
                        if(valiUser != null){
                            alertMessage("Error","El Usuario ya exite", null,Alert.AlertType.ERROR);
                        }else{
                            Usuario newUsuario = new Usuario(0,
                                    txtUsuario.getText(),
                                    psfContra.getText(),
                                    txtNombres.getText(),
                                    txtApellidos.getText(),
                                    cbGenero.getSelectionModel().getSelectedItem().toString(),
                                    txtCorreo.getText(),
                                    Date.valueOf(dateFormatter.format(dpFecha.getValue()))
                            );
                            // Se crea el usuario
                            if(userDAO.insertNewUsuario(newUsuario)){
                                // Se reemplaza el modelo por el usuario creado
                                newUsuario = userDAO.getUsuarioLogin(txtUsuario.getText(), psfContra.getText());
                                System.out.println("-> "+newUsuario.getNoUsuario());
                                // Se crea la asignacion
                                modeloAsignacion newAsignacion = new modeloAsignacion(
                                        txtcveAsignacion.getText(),
                                        cbTipoUsuario.getSelectionModel().getSelectedItem().toString()
                                );
                                System.out.println(newAsignacion.getNo() + ", " + newUsuario.getNoUsuario() + ", " + newAsignacion.getCveAsignacion());
                                if(userDAO.insertNewAsignacion(newUsuario, newAsignacion)){




                                    if(cbTipoUsuario.getSelectionModel().getSelectedItem().toString().equals("Estudiante")){
                                        modeloEstudiante newEstudiante = new modeloEstudiante(
                                                txtNo_PE.getText(),
                                                txtcveAsignacion.getText(),
                                                newUsuario.getNoUsuario(),
                                                userDAO.getcveCarrera(cbAsignacion.getSelectionModel().getSelectedItem().toString())
                                        );
                                        if(userDAO.insertNewEstudiante(newEstudiante)){
                                            alertMessage("Operacion Exitosa","Estudiante agregado",
                                                    "Se agrego al estudiante " + newUsuario.getNombres(), Alert.AlertType.INFORMATION);
                                        }
                                    }else if(cbTipoUsuario.getSelectionModel().getSelectedItem().toString().equals("Personal")){
                                        modeloPersonal newPersonal = new modeloPersonal(
                                                txtNo_PE.getText(),
                                                txtcveAsignacion.getText(),
                                                newUsuario.getNoUsuario(),
                                                userDAO.getcveDepa(cbAsignacion.getSelectionModel().getSelectedItem().toString())
                                        );
                                        if(userDAO.insertNewPersonal(newPersonal)){
                                            alertMessage("Operacion Exitosa","Personal agregado",
                                                    "Se agrego al personal " + newUsuario.getNombres(), Alert.AlertType.INFORMATION);
                                        }
                                    }







                                }
                            }
                        }
                    }
                }catch (NullPointerException | SQLException e){
                    alertMessage("Error","createUsuario", e.getMessage(), Alert.AlertType.ERROR);
                }
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
        lblNCP.setText("noCont");
        cbAsignacion.setItems(userDAO.getCarreras());
        editMode();
    }
    private void personalMode() throws SQLException {
        cbTipoUsuario.setValue("Personal");
        cbAsignacion.getItems().clear();
        lblCD.setText("Departamento");
        lblNCP.setText("noPersonal");
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
        btnCrear.setDisable(true);
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
        btnCrear.setDisable(false);
    }
    private boolean valiVacio(){
        boolean bandera = false;
        if(cbTipoUsuario.getSelectionModel().getSelectedItem() == null){
            alertMessage("cbTipoUsuario",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(txtcveAsignacion.getText().isEmpty()){
            alertMessage("cveAsignacion",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(cbAsignacion.getSelectionModel().getSelectedItem() == null){
            alertMessage("cbAsignacion",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(txtNo_PE.getText().isEmpty()){
            alertMessage("noPE",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(txtUsuario.getText().isEmpty()) {
            alertMessage("usuario",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(psfContra.getText().isEmpty()) {
            alertMessage("contra",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(txtNombres.getText().isEmpty()) {
            alertMessage("nombres",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(txtApellidos.getText().isEmpty()) {
            alertMessage("apellidos",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(cbGenero.getSelectionModel().getSelectedItem() == null){
            alertMessage("cbGenero",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(txtCorreo.getText().isEmpty()) {
            alertMessage("correo",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        }else if(dpFecha.getEditor().getText().isEmpty()) {
            alertMessage("fecha",null,
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
