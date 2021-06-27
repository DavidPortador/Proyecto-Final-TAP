package usuarios;
import database.MySQLConnection;
import database.UserDAO;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class Directivos implements Initializable {
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Stage anterior;
    @Override public void initialize(URL location, ResourceBundle resources) {

    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
}
