package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.*;
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
                alertMessage("Error","getAsignacion", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return asignacion;
    }
    public String getcveAsignacion(int noUsuario) throws SQLException {
        String consulta, cveasignacion = null;
        consulta = "select cveAsignacion from Asignacion where noUsuario = " + noUsuario;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    cveasignacion = rs.getString("cveAsignacion");
            } catch (Exception e) {
                alertMessage("Error","getcveAsignacion", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return cveasignacion;
    }

    public String getnoCedula(int noUsuario) throws SQLException {
        String consulta, noCedula = null;
        consulta = "select noCedula from Medico where noUsuario = " + noUsuario;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    noCedula = rs.getString("noCedula");
            } catch (Exception e) {
                alertMessage("Error","getnoCedula", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return noCedula;
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
                alertMessage("Error","getDCarrera", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return carrera;
    }
    public String getcveCarrera(String carrera) throws SQLException {
        String consulta, cveCarrera = null;
        consulta = "select cveCarrera from Carrera where nombre = '" + carrera + "'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    cveCarrera = rs.getString("cveCarrera");
            } catch (Exception e) {
                alertMessage("Error","getcveCarrera", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return cveCarrera;
    }
    public String getDepa(int noUsuario) throws SQLException {
        String consulta, depa = null;
        consulta = "select D.nombre from Personal P inner join Departamento D on P.cveDepa = D.cveDepa " +
                "where P.noUsuario = " + noUsuario;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    depa = rs.getString("nombre");
            } catch (Exception e) {
                alertMessage("Error","getDepa", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return depa;
    }
    public String getcveDepa(String departamento) throws SQLException {
        String consulta, cvedepa = null;
        consulta = "select cveDepa from Departamento " +
                "where nombre = '" + departamento + "'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    cvedepa = rs.getString("cveDepa");
            } catch (Exception e) {
                alertMessage("Error","getcveDepa", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return cvedepa;
    }
    public Usuario getUsuarioAD(modeloUsers modeloUser) throws SQLException {
        String consulta;
        Usuario user = null;
        consulta = "select * from Usuario where noUsuario = '" + modeloUser.getNoUsuario() +
                "' and usuario = '" + modeloUser.getUsuario() + "' and contra = '" + modeloUser.getContra() + "'";
        //System.out.println(consulta);
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
                alertMessage("Error","getUsuarioAD", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return user;
    }
    public Usuario getUsuarioLogin(String user, String pass) throws SQLException {
        String consulta;
        Usuario usuario = null;
        consulta = "select * from Usuario where usuario = '"+user+"' and contra = '"+pass+"'";
        //System.out.println(consulta);
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        if (rs != null) {
            try {
                while (rs.next())
                    usuario = new Usuario(
                            rs.getInt("noUsuario"),
                            rs.getString("usuario"),
                            rs.getString("contra"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("genero"),
                            rs.getString("correo"),
                            rs.getDate("fechaNac"));
            } catch (Exception e) {
                alertMessage("Error","getUsuarioLogin", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
        return usuario;
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
            alertMessage("Error","getTableAdmin", e.getMessage(), Alert.AlertType.ERROR);
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
            alertMessage("Error","getCarreras", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listCarreras;
    }
    public ObservableList <String> getDepas() throws SQLException {
        ObservableList <String> listDepas = FXCollections.observableArrayList();
        try {
            String query = "select nombre from Departamento";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listDepas.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            alertMessage("Error","getDepas", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listDepas;
    }
    public ObservableList <String> getMedicos() throws SQLException {
        ObservableList <String> medicos = FXCollections.observableArrayList();
        try {
            String query = "select noCedula from Medico";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                medicos.add(rs.getString("noCedula"));
            }
        } catch (SQLException e) {
            alertMessage("Error","getMedicos", e.getMessage(), Alert.AlertType.ERROR);
        }
        return medicos;
    }
    // Operaciones del CRUD
    // Estudiantes / Personal
    public boolean insertNewUsuario(Usuario usuario) {
        // Primero se agrega el usuario y luego se hace la relacion con asignacion y carrera
        try {
            String query = "insert into Usuario (usuario, contra, nombres, apellidos, genero, correo, fechaNac) " +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContra());
            ps.setString(3, usuario.getNombres());
            ps.setString(4, usuario.getApellidos());
            ps.setString(5, usuario.getGenero());
            ps.setString(6, usuario.getCorreo());
            ps.setDate(7, usuario.getFechaNac());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertNewUsuario", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    public boolean insertNewAsignacion(Usuario usuario, modeloAsignacion asignacion) {
        // Se hace la relacion entre usuario y asignacion
        try {
            String query = "insert into Asignacion " +
                    "values (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, asignacion.getNo());
            ps.setInt(2, usuario.getNoUsuario());
            ps.setString(3, asignacion.getCveAsignacion());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertNewAsignacion", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    public boolean insertNewEstudiante(modeloEstudiante estudiante) {
        // Se le asignan sus datos al estudiante
        try {
            String query = "insert into Estudiante " +
                    "values (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, estudiante.getNoCont());
            ps.setString(2, estudiante.getCveAsignacion());
            ps.setInt(3, estudiante.getNoUsuario());
            ps.setString(4, estudiante.getCveCarrera());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertNewEstudiante", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    public boolean insertNewPersonal(modeloPersonal personal) {
        // Se le asignan sus datos al personal
        try {
            String query = "insert into Personal " +
                    "values (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, personal.getNoCont());
            ps.setString(2, personal.getCveAsignacion());
            ps.setInt(3, personal.getNoUsuario());
            ps.setString(4, personal.getCveCarrera());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertNewPersonal", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
}