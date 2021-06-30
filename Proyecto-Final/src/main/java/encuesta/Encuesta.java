package encuesta;
import database.EncuestaDAO;
import database.MySQLConnection;
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
    EncuestaDAO encuestaDAO = new EncuestaDAO(MySQLConnection.getConnection());
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
                getRespuestas(event);
            }
        });
    }
    private void getRespuestas(ActionEvent event){
        int[] respuestas = new int[13];
        String otros;
        // Validaciones y llenado de respuestas
        boolean bandera = true;
        // La bandera valida que las rspuestas sean seleccionadas
        // Respuesta 1
        if(bandera)
            if(rdp1si.isSelected() == true)
                respuestas[0] = 1;
            else if(rdp1no.isSelected() == true)
                respuestas[0] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 1", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 2
        if(bandera)
            if(rdp2si.isSelected() == true)
                respuestas[1] = 1;
            else if(rdp2no.isSelected() == true)
                respuestas[1] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 2", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 3
        if(bandera)
            if(rdp3si.isSelected() == true)
                respuestas[2] = 1;
            else if(rdp3no.isSelected() == true)
                respuestas[2] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 3", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 4
        if(bandera)
            if(rdp4si.isSelected() == true)
                respuestas[3] = 1;
            else if(rdp4no.isSelected() == true)
                respuestas[3] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 4", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 5
        if(bandera)
            if(rdp5si.isSelected() == true)
                respuestas[4] = 1;
            else if(rdp5no.isSelected() == true)
                respuestas[4] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 5", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 6
        if(bandera)
            if(rdp6si.isSelected() == true)
                respuestas[5] = 1;
            else if(rdp6no.isSelected() == true)
                respuestas[5] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 6", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 7
        if(bandera)
            if(rdp7si.isSelected() == true)
                respuestas[6] = 1;
            else if(rdp7no.isSelected() == true)
                respuestas[6] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 7", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 8
        if(bandera)
            if(rdp8si.isSelected() == true)
                respuestas[7] = 1;
            else if(rdp8no.isSelected() == true)
                respuestas[7] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 8", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 9
        if(bandera)
            if(rdp9si.isSelected() == true)
                respuestas[8] = 1;
            else if(rdp9no.isSelected() == true)
                respuestas[8] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 9", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 10
        if(bandera)
            if(rdp10si.isSelected() == true)
                respuestas[9] = 1;
            else if(rdp10no.isSelected() == true)
                respuestas[9] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 10", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 11
        if(bandera)
            if(rdp11si.isSelected() == true)
                respuestas[10] = 1;
            else if(rdp11no.isSelected() == true)
                respuestas[10] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 11", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 12
        if(bandera)
            if(rdp12si.isSelected() == true)
                respuestas[11] = 1;
            else if(rdp12no.isSelected() == true)
                respuestas[11] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 12", Alert.AlertType.ERROR);
                bandera = false;
            }
        // Respuesta 13
        if(bandera)
            if(rdp13si.isSelected() == true)
                respuestas[12] = 1;
            else if(rdp13no.isSelected() == true)
                respuestas[12] = 0;
            else{
                alertMessage("Error",null, "Responder Respuesta 13", Alert.AlertType.ERROR);
                bandera = false;
            }
        otros = tfAbierta.getText();
        for (int i = 0; i < respuestas.length; i++)
            System.out.println(respuestas[i]);
        if(bandera){
            if(encuestaDAO.insertNewEncuesta(usuario, respuestas, otros)){
                alertMessage("Operacion Exitosa","Encuesta registrada",
                        "Se agrego la encuesta a " + usuario.getNombres(), Alert.AlertType.INFORMATION);
                Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                actual.close();
            }
        }
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
        // Pregunta 14
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
