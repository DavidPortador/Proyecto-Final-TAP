package usuarios;
import encuesta.Encuesta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelos.Usuario;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class Personal implements Initializable {
    /*
    Cuando se acabe la interfaz de Estudiante se adapa con Personal
    */
    Usuario personal;
    Stage anterior;
    @FXML Label lblUsuario;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(personal.getNombres()+" "+personal.getApellidos());
    }
    int getRandom(){
        int v_random;
        v_random = (int) (Math.floor(Math.random() * (3 - 1 + 1)) + 1);
        return  v_random;
    }
    void showEncuesta(ActionEvent event) throws IOException {
        String v_personal;
        v_personal = "'aqui va el personal'";
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Encuesta "+v_personal);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/encuesta.fxml"));
        Encuesta encuesta = new Encuesta();
        encuesta.setPersonal(v_personal);
        loader.setController(encuesta);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setResizable(false);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        //encuesta.setStageAnterior(actual);
        actual.close();
        // Muestra el nuevo stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        personal = usuario;
    }
}
