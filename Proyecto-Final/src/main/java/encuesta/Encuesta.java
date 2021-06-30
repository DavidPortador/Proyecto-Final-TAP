package encuesta;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelos.Usuario;

import java.net.URL;
import java.util.ResourceBundle;
public class Encuesta implements Initializable {
    Usuario usuario;
    Stage anterior;
    @FXML RadioButton rdp1si, rdp1no, rdp2si, rdp2no, rdp3si, rdp3no, rdp4si, rdp4no, rdp5si, rdp5no,
            rdp6si, rdp6no, rdp7si, rdp7no, rdp8si, rdp8no, rdp9si, rdp9no, rdp10si, rdp10no,
            rdp11si, rdp11no, rdp12si, rdp12no, rdp13si, rdp13no;
    @FXML Label txtPersonal, txtH1, txtH2, txtH3,  txtP1, txtP2, txtP3, txtP4, txtP5, txtP6, txtP7, txtP8,
            txtP9, txtP10, txtP11, txtP12, txtP13, txtP14;
    @FXML TextField tfAbierta;
    @FXML
    Button btnCancelar, btnRegistrar;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crearEncuesta();
        initData();
    }
    void initData(){
        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                actual.close();
            }
        });
        btnRegistrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getRespuestas();
            }
        });
    }
    private void getRespuestas(){

        if(rdp1si.isSelected() == true){
            alertMessage("si","Responder Respuesta 1", null, Alert.AlertType.ERROR);
        }else if(rdp1no.isSelected() == true){
            alertMessage("no","Responder Respuesta 1", null, Alert.AlertType.ERROR);
        }else{
            alertMessage("Error","Responder Respuesta 1", null, Alert.AlertType.ERROR);
        }

        if((rdp1si.isSelected() == true) || (rdp1no.isSelected() == true)){
            if(rdp1si.isSelected() == true){
                alertMessage("si","Responder Respuesta 1", null, Alert.AlertType.ERROR);
            }else if(rdp1no.isSelected() == true){
                alertMessage("no","Responder Respuesta 1", null, Alert.AlertType.ERROR);
            }
        }else{
            alertMessage("no","Responder Respuesta 1", null, Alert.AlertType.ERROR);
        }


        /*if(rdp1si.isSelected() != true || rdp1no.isSelected() != true){
            alertMessage("Error","Responder Respuesta 1", null, Alert.AlertType.ERROR);
        }else if(rdp2si.isSelected() == true || rdp2no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 2", null, Alert.AlertType.ERROR);
        }else if(rdp3si.isSelected() == true || rdp3no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 3", null, Alert.AlertType.ERROR);
        }else if(rdp4si.isSelected() == true || rdp4no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 4", null, Alert.AlertType.ERROR);
        }else if(rdp5si.isSelected() == true || rdp5no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 5", null, Alert.AlertType.ERROR);
        }else if(rdp6si.isSelected() == true || rdp6no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 6", null, Alert.AlertType.ERROR);
        }else if(rdp7si.isSelected() == true || rdp7no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 7", null, Alert.AlertType.ERROR);
        }else if(rdp8si.isSelected() == true || rdp8no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 8", null, Alert.AlertType.ERROR);
        }else if(rdp9si.isSelected() == true || rdp9no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 9", null, Alert.AlertType.ERROR);
        }else if(rdp10si.isSelected() == true || rdp10no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 10", null, Alert.AlertType.ERROR);
        }else if(rdp11si.isSelected() == true || rdp11no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 11", null, Alert.AlertType.ERROR);
        }else if(rdp12si.isSelected() == true || rdp12no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 12", null, Alert.AlertType.ERROR);
        }else if(rdp13si.isSelected() == true || rdp13no.isSelected() == true){
            alertMessage("Error","Responder Respuesta 13", null, Alert.AlertType.ERROR);
        }else{

        }*/
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void crearEncuesta(){
        ToggleGroup gp1 = new ToggleGroup();
        ToggleGroup gp2 = new ToggleGroup();
        ToggleGroup gp3 = new ToggleGroup();
        ToggleGroup gp4 = new ToggleGroup();
        ToggleGroup gp5 = new ToggleGroup();
        ToggleGroup gp6 = new ToggleGroup();
        ToggleGroup gp7 = new ToggleGroup();
        ToggleGroup gp8 = new ToggleGroup();
        ToggleGroup gp9 = new ToggleGroup();
        ToggleGroup gp10 = new ToggleGroup();
        ToggleGroup gp11 = new ToggleGroup();
        ToggleGroup gp12 = new ToggleGroup();
        ToggleGroup gp13 = new ToggleGroup();

        txtPersonal.setText("Encuesta a "+ usuario.getNombres()+" "+usuario.getApellidos());
        // Seccion 1
        txtH1.setText(
                "Tener uno o mas de los siguientes sintomas justifica la ausencia o retiro " +
                "inmediato de la institucion.");
        // Pregunta 1
        txtP1.setText(
                "¿Siente fiebre, escalofrios como los de una gripe, o una fiebre con una\n" +
                "temperatura tomada por la boca de 38,1°C (100,6°F) o mas?");
        rdp1si.setToggleGroup(gp1);
        rdp1no.setToggleGroup(gp1);
        //Pregunta 2
        txtP2.setText(
                "¿Ha tenido una perdida repentina del olfato sin congestión nasal (nariz\n" +
                "tapada), con o sin perdida del gusto?");
        rdp2si.setToggleGroup(gp2);
        rdp2no.setToggleGroup(gp2);
        //Pregunta 3
        txtP3.setText(
                "¿Ha desarrollado una tos o su tos cronica ha empeorado recientemente?");
        rdp3si.setToggleGroup(gp3);
        rdp3no.setToggleGroup(gp3);
        //Pregunta 4
        txtP4.setText(
                "¿Tiene problemas al respirar o le falta el aliento?");
        rdp4si.setToggleGroup(gp4);
        rdp4no.setToggleGroup(gp4);
        //Pregunta 5
        txtP5.setText(
                "¿Tiene dolor de garganta?");
        rdp5si.setToggleGroup(gp5);
        rdp5no.setToggleGroup(gp5);
        //Pregunta 6
        txtP6.setText(
                "¿Tiene secreción o congestión nasal de causa desconocida?");
        rdp6si.setToggleGroup(gp6);
        rdp6no.setToggleGroup(gp6);

        // Seccion 2
        txtH2.setText(
                "Tener dos o más de los siguientes síntomas justifica la ausencia o retiro " +
                        "inmediato de la Institución.");
        // Pregunta 7
        txtP7.setText(
                "Dolor de estómago");
        rdp7si.setToggleGroup(gp7);
        rdp7no.setToggleGroup(gp7);
        // Pregunta 8
        txtP8.setText(
                "Náuseas o vómitos");
        rdp8si.setToggleGroup(gp8);
        rdp8no.setToggleGroup(gp8);
        // Pregunta 9
        txtP9.setText(
                "Diarrea");
        rdp9si.setToggleGroup(gp9);
        rdp9no.setToggleGroup(gp9);
        // Pregunta 10
        txtP10.setText(
                "Fatiga inusualmente intensa sin razón obvia");
        rdp10si.setToggleGroup(gp10);
        rdp10no.setToggleGroup(gp10);
        // Pregunta 11
        txtP11.setText(
                "Pérdida significativa de apetito");
        rdp11si.setToggleGroup(gp11);
        rdp11no.setToggleGroup(gp11);
        // Pregunta 12
        txtP12.setText(
                "Dolores musculares generalizados inusuales o sin razón obvia\n" +
                        "(no relacionado con el esfuerzo físico)");
        rdp12si.setToggleGroup(gp12);
        rdp12no.setToggleGroup(gp12);
        // Pregunta 13
        txtP13.setText(
                "Dolor de cabeza inhabitual");
        rdp13si.setToggleGroup(gp13);
        rdp13no.setToggleGroup(gp13);

        // Seccion 3
        txtH3.setText(
                "Otros Síntomas");
        // Pregunta 1
        txtP14.setText(
                "Describa si tiene otros síntomas: ");

    }
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
}
