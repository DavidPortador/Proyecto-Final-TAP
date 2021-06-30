package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.Alerta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ConsultaDAO {
    Connection conn;
    public ConsultaDAO (Connection conn) {
        this.conn = conn;
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public ObservableList<Alerta> getAlertasGenerales(int noUsuario) throws SQLException {
        ObservableList <Alerta> alertas = FXCollections.observableArrayList();
        try {
            String query = "select A.noAlerta, A.noOrden, A.descripcion " +
                    "from Consulta C inner join Orden O on C.noConsulta = O.noConsulta " +
                    "inner join Alerta A on O.noOrden = A.noOrden " +
                    "where noUsuario = " + noUsuario;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                alertas.add(new Alerta(
                        rs.getInt("noAlerta"),
                        rs.getInt("noOrden"),
                        "Alerta Medica",
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getTableAdmin", e.getMessage(), Alert.AlertType.ERROR);
        }
        return alertas;
    }
    public ObservableList<Alerta> getAlertasMonitoreadas(int noUsuario) throws SQLException {
        ObservableList <Alerta> alertas = FXCollections.observableArrayList();
        try {
            String query = "select AM.noAlertaMonitoreo, AM.noOrden, AM.descripcion " +
                    "from Consulta C inner join Orden O on C.noConsulta = O.noConsulta " +
                    "inner join AlertaMonitoreada AM on O.noOrden = AM.noOrden " +
                    "where noUsuario = " + noUsuario;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                alertas.add(new Alerta(
                        rs.getInt("noAlertaMonitoreo"),
                        rs.getInt("noOrden"),
                        "Alerta Monitoreada",
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getAlertasMonitoreadas", e.getMessage(), Alert.AlertType.ERROR);
        }
        return alertas;
    }

}
