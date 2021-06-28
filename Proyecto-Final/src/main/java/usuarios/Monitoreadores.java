package usuarios;
import database.MySQLConnection;
import database.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelos.Usuario;
import java.net.URL;
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
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(monitoreo.getNombres()+" "+monitoreo.getApellidos());
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        monitoreo = usuario;
    }
}
