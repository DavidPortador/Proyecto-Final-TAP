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
    public ObservableList<Alerta> getAlertasGenerales() throws SQLException {
        ObservableList <Alerta> alertas = FXCollections.observableArrayList();
        try {
            String query = "select A.noAlerta, A.descripcion, A.noOrden " +
                    "from Consulta C inner join Orden O on C.noConsulta = O.noConsulta " +
                    "inner join Alerta A on O.noOrden = A.noOrden " +
                    "where noUsuario = 13";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                alertas.add(new Alerta(
                        rs.getInt("noAlerta"),
                        rs.getString("descripcion"),
                        "Alerta Medica",
                        rs.getString("noOrden")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getTableAdmin", e.getMessage(), Alert.AlertType.ERROR);
        }
        return alertas;
    }
}
