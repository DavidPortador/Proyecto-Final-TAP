package Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    Connection conn;
    public UserDAO (Connection conn) {
        this.conn = conn;
    }
    public void getUsuario(String user, String pass) throws SQLException {
        String consulta, usuario = null;
        consulta = "select A.nombre from Asignacion A inner join Usuario U on A.noUsuario = U.noUsuario " +
                "where (U.usuario = '"+user+"' or U.correo = '"+user+"')and U.contraseña = '"+pass+"'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    usuario = rs.getString("nombre");
            } catch (Exception e) {

            }
        }
        System.out.println(usuario);
        //return usuario;
    }
}