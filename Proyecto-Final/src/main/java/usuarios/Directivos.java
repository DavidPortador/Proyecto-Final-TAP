package usuarios;
import database.ConsultaDAO;
import database.MySQLConnection;
import database.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modelos.Usuario;
import modelosReportes.listCasosCarrera;
import modelosReportes.listCasosDepartamento;
import modelosReportes.listConsultasTotalMedicos;

import java.net.URL;
import java.util.ResourceBundle;
public class Directivos implements Initializable {
    /*
    Puede ver los reportes
    Puede ver la dashboard (graficas)
     */
    Usuario directivo;
    Stage anterior;
    @FXML Label lblUsuario;
    @FXML GridPane gpBarras;
    @FXML ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    @Override public void initialize(URL location, ResourceBundle resources) {
        lblUsuario.setText(directivo.getNombres()+" "+directivo.getApellidos());
       initCharts();
    }

    private void initCharts() {
        gpBarras.add(generateTotalCasosChart(),0,0);
        gpBarras.add(generateMedicosPieChart(),0,1);
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
    private PieChart generateMedicosPieChart() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (listConsultasTotalMedicos listTotalMedicos : consultaDAO.getListConsultasTotalMedicos()) {
            pieChartData.add(new PieChart.Data(listTotalMedicos.getCedula(), listTotalMedicos.getTotal()));
        }
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("CONSULTAS DE MEDICOS");
        return chart;
    }
    public void setStageAnterior(Stage stage){
        anterior = stage;
    }
    public void setUsuario(Usuario usuario){
        directivo = usuario;
    }
}
