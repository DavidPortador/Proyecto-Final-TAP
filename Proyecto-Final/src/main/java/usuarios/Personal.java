package usuarios;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class Personal implements Initializable {
    Stage anterior, actual;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setStageActual(Stage stage){
        actual = stage;
    }
}
