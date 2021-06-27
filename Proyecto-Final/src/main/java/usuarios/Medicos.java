package usuarios;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
public class Medicos implements Initializable {
    /*
    Agregar iconos a los botones
     */
    Stage anterior, actual;
    @Override public void initialize(URL location, ResourceBundle resources) {
        System.out.println("llego");

    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
}
