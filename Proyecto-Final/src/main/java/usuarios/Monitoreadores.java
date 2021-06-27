package usuarios;
import database.MySQLConnection;
import database.UserDAO;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
public class Monitoreadores implements Initializable {
    /*
    Puede ver las ordenes y con ello generar alertas monitoreadas
    Agregar iconos
     */
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Stage anterior;
    @Override public void initialize(URL location, ResourceBundle resources) {

    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
}
