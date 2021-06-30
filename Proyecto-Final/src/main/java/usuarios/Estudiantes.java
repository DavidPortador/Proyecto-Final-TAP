package usuarios;
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
import modelos.Alerta;
import modelos.Usuario;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Estudiantes implements Initializable {
    /*
    Estudiante y Personal son lo mismo casi casi xd
    Crear de manera aleatoria la encuesta para el usuario
        Los botones son (En ese orden)
            Alertas
            Ordenes
            Consultas   (opciones) -> solicitar consulta o imprimir recetas
    */
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    Usuario estudiante;
    Stage anterior;
    @FXML Button btnEncuestas, btnSalir, btnAlerta, btnConsulta, btnOrdenes;
    @FXML TableView tblAlertas;
    @FXML Label lblUsuario;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(estudiante.getNombres()+" "+estudiante.getApellidos());
        createTable();
        initButtons();
    }
    private void initButtons() {
        btnEncuestas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showEncuesta(event);
                } catch (IOException e) {
                    alertMessage("Error","btnEncuestas", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
    }
    private void createTable() {
        ObservableList <Alerta> alertas = FXCollections.observableArrayList();
        ObservableList<Alerta> generales, monitoreadas;
        tblAlertas.getItems().clear();
        tblAlertas.getColumns().clear();
        TableColumn noAlerta = new TableColumn("noAlerta");
        noAlerta.setMinWidth(130);
        TableColumn noOrden = new TableColumn("noOrden");
        noOrden.setMinWidth(130);
        TableColumn TipoAlerta = new TableColumn("Tipo Alerta");
        TipoAlerta.setMinWidth(150);
        TableColumn descripcion = new TableColumn("Descripcion");
        descripcion.setMinWidth(250);
        noAlerta.setCellValueFactory(new PropertyValueFactory<>("noAlerta"));
        noOrden.setCellValueFactory(new PropertyValueFactory<>("noOrden"));
        TipoAlerta.setCellValueFactory(new PropertyValueFactory<>("tipoAlerta"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tblAlertas.getColumns()
                .addAll(noAlerta, noOrden, TipoAlerta, descripcion);
        try {
            generales = consultaDAO.getAlertasGenerales(estudiante.getNoUsuario());
            monitoreadas = consultaDAO.getAlertasMonitoreadas(estudiante.getNoUsuario());
            for (int i = 0; i < generales.size(); i++)
                alertas.add(generales.get(i));
            for (int i = 0; i < monitoreadas.size(); i++)
                alertas.add(monitoreadas.get(i));
            tblAlertas.setItems(alertas);
        } catch (SQLException e) {
            alertMessage("Error","createTable", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    int getRandom(){
        int v_random;
        v_random = (int) (Math.floor(Math.random() * (3 - 1 + 1)) + 1);
        return  v_random;
    }
    private void showEncuesta(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Encuesta");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/encuesta.fxml"));
        Encuesta encuesta = new Encuesta();
        encuesta.setUsuario(estudiante);
        loader.setController(encuesta);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setResizable(false);
        // Le pasa como parametro el stage actual y nueva
        Stage actual = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
        //encuesta.setStageAnterior(actual);
        //actual.close();
        // Muestra el nuevo stage
        primaryStage.setScene(scene);
        primaryStage.show();
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
