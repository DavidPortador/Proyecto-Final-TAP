package usuarios;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelos.Usuario;

import java.net.URL;
import java.util.ResourceBundle;
public class Medicos implements Initializable {
    /*
    Agregar iconos a los botones
     */
    Usuario medico;
    Stage anterior;
    @FXML Label lblUsuario;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(medico.getNombres()+" "+medico.getApellidos());
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        medico = usuario;
    }
}
