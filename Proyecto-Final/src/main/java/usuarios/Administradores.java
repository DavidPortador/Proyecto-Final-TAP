package usuarios;
import database.MySQLConnection;
import database.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.modeloUsers;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Administradores implements Initializable {
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Stage anterior, actual;
    @FXML TextField txtnoUsuario, txtUsuario, txtContraseña, txtNombres, txtApellidos, txtCorreo;
    @FXML Button btnSalir;
    @FXML TableView tblFiltrar;
    @FXML ComboBox cboGenero;
    @FXML DatePicker dpNacimiento;
    @Override public void initialize(URL location, ResourceBundle resources) {
        initData();
        initButtons();
    }
    void initData(){
        createTable();
    }
    private void initButtons() {
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
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
    private void valiVacio(){
        if(txtnoUsuario.getText().isEmpty()){
            alertMessage("noUsuario",null,
                    "Revise que los datos sean correctos", Alert.AlertType.ERROR);
        }else if(txtUsuario.getText().isEmpty()){
            alertMessage("Usuario",null,
                    "Revise que los datos sean correctos", Alert.AlertType.ERROR);
        }else if(txtContraseña.getText().isEmpty()){
            alertMessage("Contraseña",null,
                    "Revise que los datos sean correctos", Alert.AlertType.ERROR);
        }else if(txtNombres.getText().isEmpty()){
            alertMessage("Nombres",null,
                    "Revise que los datos sean correctos", Alert.AlertType.ERROR);
        }else if(txtApellidos.getText().isEmpty()){
            alertMessage("Apellidos",null,
                    "Revise que los datos sean correctos", Alert.AlertType.ERROR);
        }else if(txtCorreo.getText().isEmpty()){
            alertMessage("Correo",null,
                    "Revise que los datos sean correctos", Alert.AlertType.ERROR);
        }else if(cboGenero.getSelectionModel().getSelectedItem().equals("")){
            alertMessage("Genero",null,
                    "Revise que los datos sean correctos", Alert.AlertType.ERROR);
        }else if(dpNacimiento.getEditor().getText().isEmpty()){
            alertMessage("Nacimiento",null,
                    "Revise que los datos sean correctos", Alert.AlertType.ERROR);
        }
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
