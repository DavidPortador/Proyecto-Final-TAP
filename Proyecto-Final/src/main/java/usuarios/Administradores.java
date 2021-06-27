package usuarios;
import database.MySQLConnection;
import database.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelos.Usuario;
import modelos.modeloUsers;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Administradores implements Initializable {
    /*
    Agregar un boton de cancelar a la izquierda de reportes

     */
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Stage anterior;
    @FXML TextField txtnoUsuario, txtUsuario, txtContra, txtNombres, txtApellidos, txtCorreo;
    @FXML Button btnEditar, btnEliminar, btnReportes, btnSalir;
    @FXML ComboBox cbGenero, cbAux;
    @FXML DatePicker dpNacimiento;
    @FXML TableView tblFiltrar;
    @FXML Label lblAux;
    @Override public void initialize(URL location, ResourceBundle resources) {
        defaultMode();
        initData();
        initButtons();
    }
    void initData(){
        createTable();
        llenarGenero();
        tblFiltrar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent event) {
                if(event.getClickCount() == 1){
                    modeloUsers modeloUsers = (modeloUsers) tblFiltrar.getSelectionModel().getSelectedItem();
                    if (modeloUsers == null) {
                        alertMessage("Error", "Error al cargar usuario",
                                "No se selecciono ningun Usuario", Alert.AlertType.ERROR);
                    }else {
                        String asignacion;
                        try {
                            editMode();
                            Usuario usuario = userDAO.getUsuario(modeloUsers);
                            System.out.println(usuario.getNoUsuario() + " " + usuario.getUsuario() + " " + usuario.getContra());
                            txtnoUsuario.setText(usuario.getNoUsuario() + "");
                            txtUsuario.setText(usuario.getUsuario());
                            txtContra.setText(usuario.getContra());
                            txtNombres.setText(usuario.getNombres());
                            txtApellidos.setText(usuario.getApellidos());
                            cbGenero.setValue(usuario.getGenero());
                            txtCorreo.setText(usuario.getCorreo());
                            dpNacimiento.setValue(usuario.getFechaNac().toLocalDate());
                            asignacion = userDAO.getAsignacion(usuario.getUsuario(), usuario.getContra());
                            if (asignacion.equals("Estudiante")) {
                                lblAux.setText("Carrera");
                                llenarSelecCarrera(usuario.getNoUsuario());
                                editMode();
                            } else if (asignacion.equals("Personal")) {
                                lblAux.setText("Departamento");
                                llenarSelecDepartamento(usuario.getNoUsuario());
                                editMode();
                            }
                        } catch (SQLException e) {
                            alertMessage("Error", "Error al cargar usuario",
                                    e.getMessage(), Alert.AlertType.ERROR);
                        }
                    }
                }
            }
        });
    }
    private void initButtons() {
        btnEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(valiVacio()){
                    System.out.println("lleno");
                }
            }
        });
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
    }
    private void llenarSelecCarrera(int noUsuario) throws SQLException {
        cbAux.getItems().clear();
        cbAux.setItems(userDAO.getCarreras());
        cbAux.setValue(userDAO.getCarrera(noUsuario));
    }
    private void llenarSelecDepartamento(int noUsuario) throws SQLException {
        cbAux.getItems().clear();
        cbAux.setItems(userDAO.getDepas());
        cbAux.setValue(userDAO.getDepa(noUsuario));
    }
    private void createTable() {
        ObservableList <modeloUsers> usuarios;
        tblFiltrar.getItems().clear();
        tblFiltrar.getColumns().clear();
        TableColumn noUsuario = new TableColumn("No");
        TableColumn asignacion = new TableColumn("Asignacion");
        asignacion.setMinWidth(100);
        TableColumn usuario = new TableColumn("Usuario");
        usuario.setMinWidth(100);
        TableColumn contra = new TableColumn("Contra");
        contra.setMinWidth(100);
        TableColumn nombres = new TableColumn("Nombres");
        nombres.setMinWidth(100);
        TableColumn apellidos = new TableColumn("Apellidos");
        apellidos.setMinWidth(100);
        noUsuario.setCellValueFactory(new PropertyValueFactory<>("noUsuario"));
        asignacion.setCellValueFactory(new PropertyValueFactory<>("asignacion"));
        usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        contra.setCellValueFactory(new PropertyValueFactory<>("contra"));
        nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        apellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        tblFiltrar.getColumns()
                .addAll(noUsuario, asignacion, usuario, contra, nombres, apellidos);
        try {
            usuarios = userDAO.getTableAdmin();
            tblFiltrar.setItems(usuarios);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void llenarGenero() {
        ObservableList <String> generos = FXCollections.observableArrayList();
        generos.add("M");
        generos.add("F");
        cbGenero.setItems(generos);
    }
    private boolean valiVacio(){
        boolean bandera = false;
        if(txtnoUsuario.getText().isEmpty())
            alertMessage("noUsuario",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        else if(txtUsuario.getText().isEmpty())
            alertMessage("Usuario",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        else if(txtContra.getText().isEmpty())
            alertMessage("Contraseña",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        else if(txtNombres.getText().isEmpty())
            alertMessage("Nombres",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        else if(txtApellidos.getText().isEmpty())
            alertMessage("Apellidos",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        else if(txtCorreo.getText().isEmpty())
            alertMessage("Correo",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        else if(cbGenero.getSelectionModel().getSelectedItem() == null)
            alertMessage("Genero",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        else if(dpNacimiento.getEditor().getText().isEmpty())
            alertMessage("Nacimiento",null,
                    "Campos vacios", Alert.AlertType.ERROR);
        else
            bandera = true;
        return bandera;
    }
    private void defaultMode(){
        txtnoUsuario.setDisable(true);
        txtUsuario.setDisable(true);
        txtContra.setDisable(true);
        txtNombres.setDisable(true);
        txtApellidos.setDisable(true);
        cbGenero.setDisable(true);
        txtCorreo.setDisable(true);
        dpNacimiento.setDisable(true);
        cbAux.setDisable(true);
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        lblAux.setText("Asignacion");
        cbAux.getItems().clear();
        createTable();
    }
    private void editMode(){
        txtnoUsuario.setDisable(false);
        txtUsuario.setDisable(false);
        txtContra.setDisable(false);
        txtNombres.setDisable(false);
        txtApellidos.setDisable(false);
        cbGenero.setDisable(false);
        txtCorreo.setDisable(false);
        dpNacimiento.setDisable(false);
        cbAux.setDisable(false);
        btnEditar.setDisable(false);
        btnEliminar.setDisable(false);
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
