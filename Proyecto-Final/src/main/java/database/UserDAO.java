package database;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class UserDAO {
    Connection conn;
    public UserDAO (Connection conn) {
        this.conn = conn;
    }
    public String getUsuario(String user, String pass) throws SQLException {
        String consulta, usuario = null;
        consulta = "select A.nombre from Asignacion A inner join Usuario U on A.noUsuario = U.noUsuario " +
                "where (U.usuario = '"+user+"' or U.correo = '"+user+"') and U.contra = '"+pass+"'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    usuario = rs.getString("nombre");
            } catch (Exception e) {
                alertMessage("Error","Error de validacion",
                        e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return usuario;
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}