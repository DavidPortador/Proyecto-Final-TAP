package usuarios;
import database.ConsultaDAO;
import database.MySQLConnection;
import database.UserDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelos.Usuario;
import modelosReportes.listCasosCarrera;
import modelosReportes.listCasosDepartamento;
import java.net.URL;
import java.util.ResourceBundle;
public class Directivos implements Initializable {
    /*
    Puede ver los reportes
    Puede ver la dashboard (graficas)
     */
    UserDAO userDAO = new UserDAO(MySQLConnection.getConnection());
    Usuario directivo;
    Stage anterior;
    @FXML Label lblUsuario;
    @FXML GridPane gpBarras;
    @FXML Button btnSalir;
    @FXML ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(directivo.getNombres()+" "+directivo.getApellidos());
       initCharts();
       initButtons();
    }
    private void initButtons() {
        btnSalir.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent event) {
                Stage stage = ((Stage)(((Button)event.getSource()).getScene().getWindow()));
                stage.close();
                anterior.show();
            }
        });
    }
    private void initCharts() {
        gpBarras.add(generateTotalCasosChart(),0,0);
    }
    private BarChart generateTotalCasosChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Total Casos");
        xAxis.setLabel("Casos");
        yAxis.setLabel("Total");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Carrera");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Departamento");
        for (listCasosCarrera row : consultaDAO.getListContagiadosCarrera()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(row.getCarrera(), row.getContagiados());
            series1.getData().add(data);
        }
        for (listCasosDepartamento row2 : consultaDAO.getListContagiadosDepartamento()) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(row2.getDepartamentos(), row2.getContagiados());
            series2.getData().add(data);
        }
        bc.getData().addAll(series1,series2);
        return bc;
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        directivo = usuario;
    }
}
