package graphics;

import database.ConsultaDAO;
import database.MySQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.GridPane;
import modelosReportes.listCasosCarrera;
import javafx.scene.chart.*;
import modelosReportes.listCasosDepartamento;

import java.net.URL;
import java.util.ResourceBundle;

public class Graphics implements Initializable {
    @FXML
    GridPane gpBarras;
    @FXML
    ConsultaDAO consultaDAO = new ConsultaDAO(MySQLConnection.getConnection());
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    initCharts();
    }
    private void initCharts() {
        gpBarras.add(generateTotalCasosChart(),0,0);
    }

    private BarChart generateTotalCasosChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("TOTAL CASOS");
        xAxis.setLabel("Casos");
        yAxis.setLabel("Total");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Carrera");
        XYChart.Series series2 = new XYChart.Series();
        series1.setName("Departamento");

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
}
