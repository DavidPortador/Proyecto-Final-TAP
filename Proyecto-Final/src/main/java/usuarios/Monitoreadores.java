package usuarios;
import database.ConsultaDAO;
import database.MySQLConnection;
import database.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.Usuario;
import modelos.modeloMonitoreo;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Monitoreadores implements Initializable {
    /*
    Puede ver las ordenes y con ello generar alertas monitoreadas
    Agregar iconos
     */
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Usuario monitoreo;
    Stage anterior;
    @FXML Label lblUsuario;
    @FXML TableView tblMonitoreo;
    @FXML Button btnSalir, btnAlerta, btnConsulta;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(monitoreo.getNombres()+" "+monitoreo.getApellidos());
        createTable();
        initButtons();
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
        ObservableList<modeloMonitoreo> ordenes;
        tblMonitoreo.getItems().clear();
        tblMonitoreo.getColumns().clear();
        TableColumn noOrden = new TableColumn("No");
        noOrden.setMinWidth(50);
        TableColumn resultado = new TableColumn("Resultado");
        resultado.setMinWidth(130);
        TableColumn nombres = new TableColumn("Nombres");
        nombres.setMinWidth(130);
        TableColumn tipo =new TableColumn("Prueba");
        tipo.setMinWidth(350);
        noOrden.setCellValueFactory(new PropertyValueFactory<>("noOrden"));
        resultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        nombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tblMonitoreo.getColumns()
                .addAll(noOrden, resultado, nombres, tipo);
        try {
            ordenes = consultaDAO.getMonitoreo();
            tblMonitoreo.setItems(ordenes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        monitoreo = usuario;
    }
}
