package usuarios;
import controllers.Consulta;
import database.ConsultaDAO;
import database.EncuestaDAO;
import database.MySQLConnection;
import encuesta.Encuesta;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelos.*;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Medicos implements Initializable {
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    EncuestaDAO encuestaDAO = new EncuestaDAO(MySQLConnection.getConnection());
    Usuario medico;
    Stage anterior;
    @FXML Button btnFiltrar, btnPrueba, btnEncuestas, btnSolicitudes, btnConsultas, btnSalir, btnAceptar;
    @FXML ComboBox cbFiltrar, cbPrueba;
    @FXML TableView tblMedicos;
    @FXML Label lblUsuario;
    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            lblUsuario.setText(medico.getNombres()+" "+medico.getApellidos());
            btnFiltrar.setDisable(false);
            cbFiltrar.setDisable(false);
            btnPrueba.setDisable(true);
            cbPrueba.setDisable(true);
            btnAceptar.setDisable(true);
            initButtons();
            createTableEncuestas();
            llenarPruebas();
        } catch (SQLException e) {
            alertMessage("Error", "llenarPruebas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void initButtons() {
        btnEncuestas.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableEncuestas();
                btnFiltrar.setDisable(false);
                cbFiltrar.setDisable(false);
                btnPrueba.setDisable(true);
                cbPrueba.setDisable(true);
                btnAceptar.setDisable(true);
            }
        });
        btnSolicitudes.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableSolicitudes();
                btnFiltrar.setDisable(true);
                cbFiltrar.setDisable(true);
                btnPrueba.setDisable(true);
                cbPrueba.setDisable(true);
                btnAceptar.setDisable(false);
            }
        });
        btnConsultas.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableConsultas();
                btnFiltrar.setDisable(true);
                cbFiltrar.setDisable(true);
                btnPrueba.setDisable(false);
                cbPrueba.setDisable(false);
                btnAceptar.setDisable(true);
            }
        });
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
        btnPrueba.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                String tipo;
                tipo = (String) cbPrueba.getSelectionModel().getSelectedItem();
                if(tipo == null){
                    alertMessage("Error", "No selecciono ningun tipo de prueba", null, Alert.AlertType.ERROR);
                    System.out.println(tipo);
                }else{
                    // Se genera una orden
                    System.out.println(tipo);

                }
            }
        });
        btnAceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                modeloSolicitud solicitud = (modeloSolicitud) tblMedicos.getSelectionModel().getSelectedItem();
                if(solicitud == null){
                    alertMessage("Error", null, "No selecciono ninguna solicitud", Alert.AlertType.ERROR);
                }else{
                    try {
                        showConsulta(event, solicitud);
                        // Generar consulta
                        System.out.println(solicitud.getEstado());
                    } catch (IOException e) {
                        alertMessage("Error", "showConsulta", e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            }
        });
    }
    private void llenarPruebas() throws SQLException {
            cbPrueba.setItems(consultaDAO.getPruebas());
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
    private void createTableSolicitudes() {
        ObservableList <modeloSolicitud> solicitudes;
        tblMedicos.getItems().clear();
        tblMedicos.getColumns().clear();
        TableColumn noSolicitud = new TableColumn("noSoli");
        noSolicitud.setMinWidth(80);
        TableColumn estado = new TableColumn("Estado");
        estado.setMinWidth(150);
        TableColumn tipo = new TableColumn("Tipo");
        tipo.setMinWidth(150);
        TableColumn cveAsignacion = new TableColumn("cveAsig");
        cveAsignacion.setMinWidth(150);
        TableColumn noUsuario = new TableColumn("noUsuario");
        noUsuario.setMinWidth(150);
        TableColumn noCedula = new TableColumn("noCedula");
        noCedula.setMinWidth(150);
        noSolicitud.setCellValueFactory(new PropertyValueFactory<>("noSolicitud"));
        estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cveAsignacion.setCellValueFactory(new PropertyValueFactory<>("cveAsignacion"));
        noUsuario.setCellValueFactory(new PropertyValueFactory<>("noUsuario"));
        noCedula.setCellValueFactory(new PropertyValueFactory<>("noCedula"));
        tblMedicos.getColumns()
                .addAll(noSolicitud, estado, tipo, cveAsignacion, noUsuario, noCedula);
        try {
            solicitudes = consultaDAO.getSolicitudes();
            tblMedicos.setItems(solicitudes);

        } catch (SQLException e) {
            alertMessage("Error", "createTableSolicitudes", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void createTableConsultas() {
        ObservableList <modeloConsulta> consultas;
        tblMedicos.getItems().clear();
        tblMedicos.getColumns().clear();
        TableColumn noConsulta = new TableColumn("noCon");
        noConsulta.setMinWidth(80);
        TableColumn sintomas = new TableColumn("sintomas");
        sintomas.setMinWidth(150);
        TableColumn fecha = new TableColumn("Fecha");
        fecha.setMinWidth(120);
        TableColumn hora = new TableColumn("Hora");
        hora.setMinWidth(120);
        TableColumn tipo = new TableColumn("Tipo");
        tipo.setMinWidth(120);
        TableColumn cveAsignacion = new TableColumn("cveAsig");
        cveAsignacion.setMinWidth(120);
        TableColumn noUsuario = new TableColumn("noUsua");
        noUsuario.setMinWidth(120);
        TableColumn noCedula = new TableColumn("noCedu");
        noCedula.setMinWidth(120);
        noConsulta.setCellValueFactory(new PropertyValueFactory<>("noConsulta"));
        sintomas.setCellValueFactory(new PropertyValueFactory<>("sintomas"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        hora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        cveAsignacion.setCellValueFactory(new PropertyValueFactory<>("cveAsignacion"));
        noUsuario.setCellValueFactory(new PropertyValueFactory<>("noUsuario"));
        noCedula.setCellValueFactory(new PropertyValueFactory<>("noCedula"));
        tblMedicos.getColumns()
                .addAll(noConsulta, sintomas, fecha, hora, tipo, cveAsignacion, noUsuario, noCedula);
        try {
            consultas = consultaDAO.getConsultas();
            tblMedicos.setItems(consultas);
        } catch (SQLException e) {
            alertMessage("Error", "createTableConsultas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showConsulta(ActionEvent event, modeloSolicitud solicitud) throws IOException {
        Stage consulta = new Stage();
        consulta.setTitle("Consulta");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/consulta.fxml"));
        Consulta consu = new Consulta();
        consu.setMedico(medico);
        consu.setSolicitud(solicitud);
        loader.setController(consu);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        consulta.setResizable(false);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        //encuesta.setStageAnterior(actual);
        //actual.close();
        // Muestra el nuevo stage
        consulta.setScene(scene);
        //primaryStage.show();
        consulta.initOwner(actual);
        consulta.initModality(Modality.WINDOW_MODAL);
        consulta.show();
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
