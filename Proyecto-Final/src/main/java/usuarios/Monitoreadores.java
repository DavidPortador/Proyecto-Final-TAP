package usuarios;
import database.ConsultaDAO;
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
import modelos.modeloAlertaM;
import modelos.modeloOrden;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
public class Monitoreadores implements Initializable {
    /*
    Puede ver las ordenes y con ello generar alertas monitoreadas
    Agregar iconos
     */
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Usuario monitoreo;
    Stage anterior;
    @FXML Label lblUsuario;
    @FXML TableView tblMonitoreo;
    @FXML Button btnSalir, btnAlerta, btnOrdenes, btnContactos;
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(monitoreo.getNombres()+" "+monitoreo.getApellidos());
        createTableOrdenes();
        initButtons();
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
        btnOrdenes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createTableOrdenes();
            }
        });
        btnAlerta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                modeloOrden orden = (modeloOrden) tblMonitoreo.getSelectionModel().getSelectedItem();
                //System.out.println(orden.getNoOrden());
                if(orden == null){
                    alertMessage("Error", "Seleccionar Orden",
                            "No se selecciono ninguna orden", Alert.AlertType.ERROR);
                }else{
                    TextInputDialog dialog = new TextInputDialog("ninguna");
                    dialog.setTitle("Descripcion de la alerta");
                    dialog.setHeaderText("Generando Alerta Monitoreada");
                    dialog.setContentText("Descripcion: ");
                    // Traditional way to get the response value.
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()){
                        //System.out.println("Your name: " + result.get());
                        try {
                            modeloAlertaM alertaM = new modeloAlertaM(
                                    0,
                                    result.get(),
                                    orden.getNoOrden(),
                                    userDAO.getnoMonitoreo(monitoreo.getNoUsuario())
                            );
                            if(consultaDAO.insertAlertaMonitoreada(alertaM)){
                                alertMessage(
                                        "Alerta Exitosa", "Se genero la alerta de manera exitosa",
                                        "Se genero una alerta monitoreada para la consulta " +
                                                orden.getNoConsulta(),
                                        Alert.AlertType.INFORMATION);
                            }
                        } catch (SQLException e) {
                            alertMessage("Error", "btnAlerta",
                                    e.getMessage(), Alert.AlertType.ERROR);
                        }
                    }
                }
            }
        });
    }
    private void createTableOrdenes() {
        ObservableList<modeloOrden> ordenes;
        tblMonitoreo.getItems().clear();
        tblMonitoreo.getColumns().clear();

        TableColumn noOrden = new TableColumn("noOrden");
        noOrden.setMinWidth(80);
        TableColumn resultado = new TableColumn("resultado");
        resultado.setMinWidth(130);
        TableColumn noConsulta = new TableColumn("noConsulta");
        noConsulta.setMinWidth(130);
        TableColumn noCedula =new TableColumn("noCedula");
        noCedula.setMinWidth(130);
        TableColumn cvePrueba =new TableColumn("cvePrueba");
        cvePrueba.setMinWidth(130);


        noOrden.setCellValueFactory(new PropertyValueFactory<>("noOrden"));
        resultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));
        noConsulta.setCellValueFactory(new PropertyValueFactory<>("noConsulta"));
        noCedula.setCellValueFactory(new PropertyValueFactory<>("noCedula"));
        cvePrueba.setCellValueFactory(new PropertyValueFactory<>("cvePrueba"));

        tblMonitoreo.getColumns()
                .addAll(noOrden, resultado, noConsulta, noCedula, cvePrueba);
        try {
            ordenes = consultaDAO.getOrdenes();
            tblMonitoreo.setItems(ordenes);
        } catch (SQLException e) {
            e.printStackTrace();
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
        monitoreo = usuario;
    }
}
