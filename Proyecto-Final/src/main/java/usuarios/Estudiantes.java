package usuarios;
import database.ConsultaDAO;
import database.MySQLConnection;
import database.UserDAO;
import encuesta.Encuesta;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelos.Alerta;
import modelos.Usuario;
import modelos.modeloUsers;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
public class Estudiantes implements Initializable {
    /*
    Estudiante y Personal son lo mismo casi casi xd
    Cada que entre un estudiante revisar si tiene alertas
    Crear de manera aleatoria la encuesta para el usuario
        Los botones son (En ese orden)
            Alertas
            Ordenes
            Consultas   (opciones) -> solicitar consulta o imprimir recetas
    */
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    Stage anterior;
    Usuario usuario;
    Estudiantes estudiante;
    @FXML Button btnConfig, btnSalir, btnAlerta, btnConsulta, btnOrdenes;
    @FXML TableView tblAlertas;
    @Override public void initialize(URL location, ResourceBundle resources) {
        createTable();
    }
    private void createTable() {
        ObservableList<Alerta> alertas;
        tblAlertas.getItems().clear();
        tblAlertas.getColumns().clear();

        TableColumn noAlerta = new TableColumn("No Alerta");
        TableColumn TipoAlerta = new TableColumn("Tipo Alerta");
        TipoAlerta.setMinWidth(100);
        TableColumn usuario = new TableColumn("Descripcion");
        usuario.setMinWidth(100);
        TableColumn contra = new TableColumn("no Orden");
        contra.setMinWidth(100);
        noAlerta.setCellValueFactory(new PropertyValueFactory<>("noAlerta"));
        TipoAlerta.setCellValueFactory(new PropertyValueFactory<>("tipoAlerta"));
        usuario.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        contra.setCellValueFactory(new PropertyValueFactory<>("noOrden"));

        tblAlertas.getColumns()
                .addAll(noAlerta, TipoAlerta, usuario, contra);
        try {
            alertas = consultaDAO.getAlertasGenerales();
            tblAlertas.setItems(alertas);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
}
