package usuarios;
import database.EncuestaDAO;
import database.MySQLConnection;
import database.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.Usuario;
import modelos.modeloEncuesta;
import modelos.modeloUsers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Medicos implements Initializable {
    /*
    Agregar iconos a los botones
     */
    EncuestaDAO encuestaDAO = new EncuestaDAO(MySQLConnection.getConnection());
    Usuario medico;
    Stage anterior;
    @FXML Button btnFiltrar, btnPrueba, btnEncuestas, btnOrden, btnConsultas, btnSalir;
    @FXML ComboBox cbFiltrar, cbPrueba;
    @FXML TableView tblMedicos;
    @FXML Label lblUsuario;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(medico.getNombres()+" "+medico.getApellidos());
        initButtons();
        createTableEncuestas();
    }

    private void initButtons() {
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
    }
    private void createTableEncuestas() {
        ObservableList <modeloEncuesta> encuestas;
        tblMedicos.getItems().clear();
        tblMedicos.getColumns().clear();
        TableColumn noEncuesta = new TableColumn("noEnc");
        noEncuesta.setMinWidth(80);
        TableColumn noUsuario = new TableColumn("noUsu");
        noUsuario.setMinWidth(80);
        TableColumn p1 = new TableColumn("P1");
        p1.setMinWidth(50);
        TableColumn p2 = new TableColumn("P2");
        p2.setMinWidth(50);
        TableColumn p3 = new TableColumn("P3");
        p3.setMinWidth(50);
        TableColumn p4 = new TableColumn("P4");
        p4.setMinWidth(50);
        TableColumn p5 = new TableColumn("P5");
        p5.setMinWidth(50);
        TableColumn p6 = new TableColumn("P6");
        p6.setMinWidth(50);
        TableColumn p7 = new TableColumn("P7");
        p7.setMinWidth(50);
        TableColumn p8 = new TableColumn("P8");
        p8.setMinWidth(50);
        TableColumn p9 = new TableColumn("P9");
        p9.setMinWidth(50);
        TableColumn p10 = new TableColumn("P10");
        p10.setMinWidth(60);
        TableColumn p11 = new TableColumn("P11");
        p11.setMinWidth(60);
        TableColumn p12 = new TableColumn("P12");
        p12.setMinWidth(60);
        TableColumn p13 = new TableColumn("P13");
        p13.setMinWidth(60);
        TableColumn otros = new TableColumn("Otros");
        p13.setMinWidth(60);

        noEncuesta.setCellValueFactory(new PropertyValueFactory<>("noEncuesta"));
        noUsuario.setCellValueFactory(new PropertyValueFactory<>("noUsuario"));
        p1.setCellValueFactory(new PropertyValueFactory<>("respuesta1"));
        p2.setCellValueFactory(new PropertyValueFactory<>("respuesta2"));
        p3.setCellValueFactory(new PropertyValueFactory<>("respuesta3"));
        p4.setCellValueFactory(new PropertyValueFactory<>("respuesta4"));
        p5.setCellValueFactory(new PropertyValueFactory<>("respuesta5"));
        p6.setCellValueFactory(new PropertyValueFactory<>("respuesta6"));
        p7.setCellValueFactory(new PropertyValueFactory<>("respuesta7"));
        p8.setCellValueFactory(new PropertyValueFactory<>("respuesta8"));
        p9.setCellValueFactory(new PropertyValueFactory<>("respuesta9"));
        p10.setCellValueFactory(new PropertyValueFactory<>("respuesta10"));
        p11.setCellValueFactory(new PropertyValueFactory<>("respuesta11"));
        p12.setCellValueFactory(new PropertyValueFactory<>("respuesta12"));
        p13.setCellValueFactory(new PropertyValueFactory<>("respuesta13"));
        otros.setCellValueFactory(new PropertyValueFactory<>("otrosSintomas"));
        tblMedicos.getColumns()
                .addAll(noEncuesta, noUsuario, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, otros);
        try {
            encuestas = encuestaDAO.getTableEncuestas();
            tblMedicos.setItems(encuestas);
        } catch (SQLException e) {
            alertMessage("Error", "createTableEncuestas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        medico = usuario;
    }
}
