package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.Usuario;

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
    public ObservableList<Usuario> getTableDep(String p_employee) throws SQLException {
        ObservableList <Usuario> listEmployees = FXCollections.observableArrayList();
        try {
            String query = "select * from employees, dept_emp where dept_emp.dept_no like '" +
                    v_condicion +"' and employees.emp_no = dept_emp.emp_no limit 50";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listEmployees.add(new Employee(
                        rs.getInt("emp_no"),
                        rs.getDate("birth_date"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender").toString().charAt(0),
                        rs.getDate("hire_date")));
            }
        } catch (SQLException e) {
            alertMessage("Error","getTableDep", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listEmployees;
    }
}