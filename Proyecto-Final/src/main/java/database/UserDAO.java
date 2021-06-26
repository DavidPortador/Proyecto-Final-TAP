package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.Usuario;
import modelos.modeloUsers;
import java.sql.*;
public class UserDAO {
    Connection conn;
    public UserDAO (Connection conn) {
        this.conn = conn;
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public String getAsignacion(String user, String pass) throws SQLException {
        String consulta, asignacion = null;
        consulta = "select A.nombre from Asignacion A inner join Usuario U on A.noUsuario = U.noUsuario " +
                "where (U.usuario = '" + user + "' or U.correo = '" + user + "') and U.contra = '" + pass + "'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    asignacion = rs.getString("nombre");
            } catch (Exception e) {
                alertMessage("Error","Error de validacion", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return asignacion;
    }
    public String getCarrera(int noUsuario) throws SQLException {
        String consulta, carrera = null;
        consulta = "select C.nombre from Estudiante E inner join Carrera C on E.cveCarrera = C.cveCarrera " +
                "where E.noUsuario = " + noUsuario;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    carrera = rs.getString("nombre");
            } catch (Exception e) {
                alertMessage("Error","Error de validacion", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return carrera;
    }
    public Usuario getUsuario(modeloUsers modeloUser) throws SQLException {
        String consulta;
        Usuario user = null;
        consulta = "select * from Usuario where noUsuario = '" + modeloUser.getNoUsuario() +
                "' and usuario = '" + modeloUser.getUsuario() + "' and contra = '" + modeloUser.getContra() + "'";
        System.out.println(consulta);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    user = new Usuario(
                        rs.getInt("noUsuario"),
                        rs.getString("usuario"),
                        rs.getString("contra"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("genero"),
                        rs.getString("correo"),
                        rs.getDate("fechaNac"));
            } catch (Exception e) {
                alertMessage("Error","Error de validacion", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return user;
    }
    public ObservableList <modeloUsers> getTableAdmin() throws SQLException {
        ObservableList <modeloUsers> listUsuarios = FXCollections.observableArrayList();
        try {
            String query = "select U.noUsuario, A.nombre, U.usuario, U.contra, U.nombres, U.apellidos " +
                    "from Usuario U inner join Asignacion A on U.noUsuario = A.noUsuario " +
                    "where A.nombre = 'Estudiante' or A.nombre = 'Personal'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listUsuarios.add(new modeloUsers(
                        rs.getInt("noUsuario"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("contra"),
                        rs.getString("nombres"),
                        rs.getString("apellidos")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getTableDep", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listUsuarios;
    }
    public ObservableList <String> getCarreras() throws SQLException {
        ObservableList <String> listCarreras = FXCollections.observableArrayList();
        try {
            String query = "select nombre from Carrera";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listCarreras.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            alertMessage("Error","getTableDep", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listCarreras;
    }
}