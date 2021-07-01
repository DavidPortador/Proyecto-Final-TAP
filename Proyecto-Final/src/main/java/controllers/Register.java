package controllers;
import database.MySQLConnection;
import database.UserDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelos.Usuario;
import modelos.modeloAsignacion;
import modelos.modeloEstudiante;
import modelos.modeloPersonal;
import usuarios.Estudiantes;
import usuarios.Personal;
import java.io.IOException;
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
    }
    private void initButtons() {
        btnCrear.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try{
                    Usuario valiUser = userDAO.getUsuarioLogin(txtUsuario.getText(),psfContra.getText());
                    if(valiVacio()){
                        if(valiUser != null)
                            alertMessage("Error","El Usuario ya exite", null,Alert.AlertType.ERROR);
                        else{
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
                                // Se crea la asignacion
                                modeloAsignacion newAsignacion = new modeloAsignacion(
                                        txtcveAsignacion.getText(),
                                        cbTipoUsuario.getSelectionModel().getSelectedItem().toString()
                                );
                                if(userDAO.insertNewAsignacion(newUsuario, newAsignacion)){
                                    // Momento en el que se decide si se registra un Estudiante o un personal
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
                                            showEstudiante(event, newUsuario);
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
                                            showPersonal(event, newUsuario);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (NullPointerException | SQLException | IOException e){
                    alertMessage("Error","createUsuario", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
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
        estudiante.setStageAnterior(anterior);
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
        personal.setStageAnterior(anterior);
        // Muestra el nuevo stage
        actual.close();
        SPersonal.show();
    }
    private void initCombo() {
        cbTipoUsuario.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try {
                    String tipoUsuario;
                    tipoUsuario = cbTipoUsuario.getValue().toString();
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
        // Se limita el tamaño de las cajas de texto para evitar errores de longitudes
        fijarTamanoMaximo(txtcveAsignacion, 5);
        fijarTamanoMaximo(txtNo_PE, 5);
        fijarTamanoMaximo(txtUsuario, 30);
        fijarTamanoMaximo(psfContra, 30);
        fijarTamanoMaximo(txtNombres, 50);
        fijarTamanoMaximo(txtApellidos, 50);
        fijarTamanoMaximo(txtCorreo, 50);
        // Deshabilita las opciones forzando al usuario elegir un tipo de usuario
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
    public void fijarTamanoMaximo(TextField campoTexto, int tamanoMaximo) {
        campoTexto.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observable, Number valorAnterior, Number valorActual) {
                if (valorActual.intValue() > valorAnterior.intValue())
                    // Revisa que la longitud del texto no sea mayor a la variable definida.
                    if (campoTexto.getText().length() >= tamanoMaximo)
                        campoTexto.setText(campoTexto.getText().substring(0, tamanoMaximo));
            }
        });
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
