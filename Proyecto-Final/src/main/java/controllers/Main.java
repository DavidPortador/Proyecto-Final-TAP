package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class Main implements Initializable {
    String a_modalidad;
    //ObservableList<String> personal;
    @FXML
    Button btnIniciar, btnCrear;
   /* @FXML
    ComboBox cbxPersonal;*/
    @FXML
    Label txtMod;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initData();
        initGUI();
    }

    void initData(){
        int v_aux;

        /*personal = FXCollections.observableArrayList();
        personal.add("Estudiantes");
        personal.add("Medicos");
        personal.add("Monitoreo");
        personal.add("Directivo");
        personal.add("Administrador");
        cbxPersonal.setItems(personal);*/

        v_aux = getRandom();
        if(v_aux == 1){
            a_modalidad = "Obligatoria";
        }else if(v_aux == 2){
            a_modalidad = "Voluntaria";
        }else if(v_aux == 3){
            a_modalidad = "Aleatoria";
        }
    }

    void initGUI() {
        txtMod.setText("Moalidad de la encuesta: "+a_modalidad);
        btnIniciar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showEncuesta();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnCrear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(getRandom());
            }
        });
    }

    void showEncuesta() throws IOException {
        String v_personal;
        v_personal = "personal";
        //v_personal = cbxPersonal.getSelectionModel().getSelectedItem().toString();
        System.out.println("-> "+v_personal);
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Encuesta "+v_personal);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/encuesta.fxml"));
        Encuesta encuesta = new Encuesta() ;
        encuesta.setPersonal(v_personal);
        loader.setController(encuesta);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void sendMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
    int getRandom(){
        int v_random;
        v_random = (int) (Math.floor(Math.random() * (3 - 1 + 1)) + 1);
        return  v_random;
    }
}
