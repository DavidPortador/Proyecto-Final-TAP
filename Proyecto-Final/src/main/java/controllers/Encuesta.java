package controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.net.URL;
import java.util.ResourceBundle;
public class Encuesta implements Initializable {
    String a_personal;
    @FXML
    RadioButton rdp1si, rdp1no, rdp2si, rdp2no;
    @FXML
    Label txtH1,txtP1, txtP2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crearEncuesta();
    }
    void crearEncuesta(){
        ToggleGroup gp1 = new ToggleGroup();
        ToggleGroup gp2 = new ToggleGroup();
        // Seccion 1
        txtH1.setText("Tener uno o más de los siguientes síntomas justifica la ausencia o retiro inmediato de la institución.");
        // Pregunta 1
        txtP1.setText("¿Siente fiebre, escalofríos como los de una gripe, o una fiebre\n" +
                "con una temperatura tomada por la boca de 38,1°C (100,6°F)\n" +
                "o más?");
        rdp1si.setToggleGroup(gp1);
        rdp1no.setToggleGroup(gp1);
        //Pregunta 2
        txtP2.setText("¿Ha tenido una pérdida repentina del olfato sin congestión\n" +
                "nasal (nariz tapada), con o sin pérdida del gusto?");
        rdp2si.setToggleGroup(gp2);
        rdp2no.setToggleGroup(gp2);
    }
    void setPersonal(String p_personal){
        a_personal = p_personal;
    }
}
