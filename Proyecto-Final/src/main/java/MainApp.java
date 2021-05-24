import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;
public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Proyecto Final Papus");
        Parent v_root = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene v_sc = new Scene(v_root);
        v_sc.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setResizable(false);
        primaryStage.setScene(v_sc);
        primaryStage.show();

        //THE MADAFUCKING MRCAGUAMA
        //Hola wapas Otaker estuvo aqui uwu
    }
}
