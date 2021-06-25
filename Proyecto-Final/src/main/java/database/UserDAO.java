package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.Usuario;
import modelos.modeloUsers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public ObservableList <modeloUsers> getTableAdmin() throws SQLException {
        ObservableList <modeloUsers> listUsuarios = FXCollections.observableArrayList();
        try {
            String query = "select U.noUsuario, A.nombre, U.usuario, U.contra, U.nombres, U.apellidos from Usuario U inner join Asignacion A on U.noUsuario = A.noUsuario where A.nombre = 'Estudiante' or A.nombre = 'Personal'";
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
    public void llenarEstudiantes(ObservableList <modeloUsers> listUsuarios){

    }
    public void llenarPersonal(){

    }
}