package usuarios;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.Usuario;

import java.net.URL;
import java.util.ResourceBundle;
public class Administradores implements Initializable {
    Stage anterior, actual;
    @FXML TextField txtnoUsuario, txtUsuario, txtContraseña, txtNombres, txtApellidos, txtCorreo;
    @FXML
    TableView tblFiltrar;
    @FXML ComboBox cboGenero;
    @FXML DatePicker dpNacimiento;
    @Override public void initialize(URL location, ResourceBundle resources) {
        initData();
    }
    void initData(){

    }
    private void createTable() {
        ObservableList <Usuario> usuarios;
        tblFiltrar.getItems().clear();
        tblFiltrar.getColumns().clear();
        TableColumn noUsuario = new TableColumn("No");
        TableColumn cveUsuario = new TableColumn("Clave");
        TableColumn usuario = new TableColumn("Usuario");
        TableColumn contra = new TableColumn("Contrasenia");
        TableColumn nombre = new TableColumn("Nombres");
        TableColumn apellido = new TableColumn("Apellidos");
        TableColumn genero = new TableColumn("Genero");
        TableColumn correo = new TableColumn("Correo");
        TableColumn fecha = new TableColumn("Fecha");

        noUsuario.setCellValueFactory(new PropertyValueFactory<>("No"));
        cveUsuario.setCellValueFactory(new PropertyValueFactory<>("Clave"));
        usuario.setCellValueFactory(new PropertyValueFactory<>("Usuario"));
        contra.setCellValueFactory(new PropertyValueFactory<>("Contrasenia"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("Nombres"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("Apellidos"));
        genero.setCellValueFactory(new PropertyValueFactory<>("Genero"));
        correo.setCellValueFactory(new PropertyValueFactory<>("Correo"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("Fecha"));

        tblFiltrar.getColumns()
                .addAll(noUsuario, cveUsuario, usuario, contra, nombre, apellido, genero, correo, fecha);
        try {
            usuarios = employeeDAO.getTableDep(employee);
            lblEmployees.setText(employee);
            tblEmployees.setItems(employees);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
}
