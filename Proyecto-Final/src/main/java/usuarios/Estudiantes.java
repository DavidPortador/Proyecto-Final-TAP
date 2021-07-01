package usuarios;
import controllers.Solicitud;
import database.ConsultaDAO;
import database.MySQLConnection;
import encuesta.Encuesta;
import javafx.collections.FXCollections;
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
public class Estudiantes implements Initializable {
    /*
    Estudiante y Personal son lo mismo casi casi xd
        Los botones son (En ese orden)
            Encuestas
            Alertas
            Ordenes
            Consultas   (opciones) -> solicitar consulta o imprimir recetas
    */
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    Usuario estudiante;
    Stage anterior;
    @FXML Button btnEncuestas, btnSalir, btnAlerta, btnConsulta, btnOrdenes, btnSolicitud;
    @FXML TableView tblEstudiante;
    @FXML Label lblUsuario;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(estudiante.getNombres()+" "+estudiante.getApellidos());
        createTableAlertas();
        initButtons();
    }
    private void initButtons() {
        btnEncuestas.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                try {
                    showEncuesta(event);
                } catch (IOException e) {
                    alertMessage("Error","btnEncuestas", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
        btnAlerta.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableAlertas();
            }
        });
        btnConsulta.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableConsultas();
            }
        });
        btnOrdenes.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                createTableOrdenes();
            }
        });
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
    }
    private void createTableAlertas() {
        ObservableList <Alerta> alertas = FXCollections.observableArrayList();
        ObservableList<Alerta> generales, monitoreadas;
        tblEstudiante.getItems().clear();
        tblEstudiante.getColumns().clear();
        TableColumn noAlerta = new TableColumn("noAlerta");
        noAlerta.setMinWidth(130);
        TableColumn noOrden = new TableColumn("noOrden");
        noOrden.setMinWidth(130);
        TableColumn TipoAlerta = new TableColumn("Tipo Alerta");
        TipoAlerta.setMinWidth(250);
        TableColumn descripcion = new TableColumn("Descripcion");
        descripcion.setMinWidth(350);
        noAlerta.setCellValueFactory(new PropertyValueFactory<>("noAlerta"));
        noOrden.setCellValueFactory(new PropertyValueFactory<>("noOrden"));
        TipoAlerta.setCellValueFactory(new PropertyValueFactory<>("tipoAlerta"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tblEstudiante.getColumns()
                .addAll(noAlerta, noOrden, TipoAlerta, descripcion);
        try {
            generales = consultaDAO.getAlertasGenerales(estudiante.getNoUsuario());
            monitoreadas = consultaDAO.getAlertasMonitoreadas(estudiante.getNoUsuario());
            for (int i = 0; i < generales.size(); i++)
                alertas.add(generales.get(i));
            for (int i = 0; i < monitoreadas.size(); i++)
                alertas.add(monitoreadas.get(i));
            tblEstudiante.setItems(alertas);
        } catch (SQLException e) {
            alertMessage("Error","createTable", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void createTableConsultas() {
        ObservableList <modeloConsulta> consultas;
        tblEstudiante.getItems().clear();
        tblEstudiante.getColumns().clear();
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
        tblEstudiante.getColumns()
                .addAll(noConsulta, sintomas, fecha, hora, tipo, cveAsignacion, noUsuario, noCedula);
        try {
            consultas = consultaDAO.getConsultaUsuario(estudiante.getNoUsuario());
            tblEstudiante.setItems(consultas);
        } catch (SQLException e) {
            alertMessage("Error", "createTableConsultas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void createTableOrdenes() {
        ObservableList <modeloOrden> ordenes;
        tblEstudiante.getItems().clear();
        tblEstudiante.getColumns().clear();

        TableColumn noOrden = new TableColumn("noOrd");
        noOrden.setMinWidth(80);
        TableColumn resultado = new TableColumn("resultado");
        resultado.setMinWidth(150);
        TableColumn noConsulta = new TableColumn("noConsulta");
        noConsulta.setMinWidth(120);
        TableColumn noCedula = new TableColumn("noCedula");
        noCedula.setMinWidth(120);
        TableColumn cvePrueba = new TableColumn("cvePrueba");
        cvePrueba.setMinWidth(120);

        noOrden.setCellValueFactory(new PropertyValueFactory<>("noOrden"));
        resultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        noConsulta.setCellValueFactory(new PropertyValueFactory<>("noConsulta"));
        noCedula.setCellValueFactory(new PropertyValueFactory<>("noCedula"));
        cvePrueba.setCellValueFactory(new PropertyValueFactory<>("cvePrueba"));
        tblEstudiante.getColumns()
                .addAll(noOrden, resultado, noConsulta, noCedula, cvePrueba);
        try {
            ordenes = consultaDAO.getOrdenUsuario(estudiante.getNoUsuario());
            tblEstudiante.setItems(ordenes);
        } catch (SQLException e) {
            alertMessage("Error", "createTableConsultas", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void showEncuesta(ActionEvent event) throws IOException {
        Stage encuestas = new Stage();
        encuestas.setTitle("Encuesta");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/encuesta.fxml"));
        Encuesta encuesta = new Encuesta();
        encuesta.setUsuario(estudiante);
        loader.setController(encuesta);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        encuestas.setResizable(false);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        //encuesta.setStageAnterior(actual);
        //actual.close();
        // Muestra el nuevo stage
        encuestas.setScene(scene);
        //primaryStage.show();
        encuestas.initOwner(actual);
        encuestas.initModality(Modality.WINDOW_MODAL);
        encuestas.show();
    }

    private void showSolicitud(ActionEvent event) throws IOException {
        Stage solicitud = new Stage();
        solicitud.setTitle("Solicitud");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/solicitudes.fxml"));
        Solicitud soli = new Solicitud();
        soli.setUsuario(estudiante);
        loader.setController(soli);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        solicitud.setResizable(false);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        //encuesta.setStageAnterior(actual);
        //actual.close();
        // Muestra el nuevo stage
        solicitud.setScene(scene);
        //primaryStage.show();
        solicitud.initOwner(actual);
        solicitud.initModality(Modality.WINDOW_MODAL);
        solicitud.show();
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
        estudiante = usuario;
    }
}
