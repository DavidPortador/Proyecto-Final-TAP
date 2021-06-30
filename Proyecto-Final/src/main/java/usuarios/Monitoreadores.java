package usuarios;
import database.ConsultaDAO;
import database.MySQLConnection;
import database.UserDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.Usuario;
import modelos.modeloUsers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Monitoreadores implements Initializable {
    /*
    Puede ver las ordenes y con ello generar alertas monitoreadas
    Agregar iconos
     */
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Usuario monitoreo;
    Stage anterior;
    @FXML Label lblUsuario;
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    @FXML
    TableView tblEstudiantes;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(monitoreo.getNombres()+" "+monitoreo.getApellidos());
        createTable();
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        monitoreo = usuario;
    }
    private void createTable() {
        ObservableList<modeloUsers> usuarios;
        tblEstudiantes.getItems().clear();
        tblEstudiantes.getColumns().clear();
        TableColumn noOrden = new TableColumn("No Orden");
        noOrden.setMinWidth(50);
        TableColumn Resultado = new TableColumn("Resultado");
        Resultado.setMinWidth(130);
        TableColumn cedula = new TableColumn("Cedula");
        cedula.setMinWidth(130);
        TableColumn nombres=new TableColumn("Nombres");
        noOrden.setCellValueFactory(new PropertyValueFactory<>("noOrden"));
        Resultado.setCellValueFactory(new PropertyValueFactory<>("Resultado"));
        cedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        tblEstudiantes.getColumns()
                .addAll(noOrden, Resultado,nombres,cedula);
        try {
            monitoreo = (Usuario) consultaDAO.getMonitoreo();
            tblEstudiantes.setItems((ObservableList) monitoreo);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
