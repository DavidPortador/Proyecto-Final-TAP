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
public class Directivos implements Initializable {
    /*
    La interfaz esta vacia :'u
    Puede ver los reportes
    Puede ver la dashboard (graficas)
     */
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Usuario directivo;
    Stage anterior;
    @FXML Label lblUsuario;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(directivo.getNombres()+" "+directivo.getApellidos());
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        directivo = usuario;
    }
}
