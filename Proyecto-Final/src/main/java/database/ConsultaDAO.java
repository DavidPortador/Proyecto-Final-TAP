package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.Alerta;
import modelos.modeloMonitoreo;
import modelosReportes.listCasosCarrera;
import modelosReportes.listCasosDelPersonal;
import modelosReportes.listCasosDepartamento;
import modelosReportes.listCasosEstudiantes;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
    // Reportes
    public List<listCasosCarrera> getListContagiadosCarrera() {
        List <listCasosCarrera> listCarrera = FXCollections.observableArrayList();
        try {
            String query = "select * from Reporte3Carreras";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listCarrera.add(new listCasosCarrera(
                        rs.getString("Carrera"),
                        rs.getInt("Contagiados")));
            }
        } catch (SQLException e) {
            alertMessage("Error","listCarrera", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listCarrera;
    }
    public List <listCasosDepartamento> getListContagiadosDepartamento() {
        List <listCasosDepartamento> listDepartamento = FXCollections.observableArrayList();
        try {
            String query = "select * from Reporte4Departamentos";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listDepartamento.add(new listCasosDepartamento(
                        rs.getString("Departamento"),
                        rs.getInt("Contagiados")));
            }
        } catch (SQLException e) {
            alertMessage("Error","listDepartamento", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listDepartamento;
    }
    public List <listCasosEstudiantes> getListContagiadosEstudiantes() {
        List <listCasosEstudiantes> listEstudiantes = FXCollections.observableArrayList();
        try {
            String query = "select * from Reporte1Estudiantes";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listEstudiantes.add(new listCasosEstudiantes(
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getDate("Fecha"),
                        rs.getString("Resultado"),
                        rs.getString("Carrera")));
            }
        } catch (SQLException e) {
            alertMessage("Error","listEstudiantes", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listEstudiantes;
    }
    public List <listCasosDelPersonal> getListContagiadosPersonal() {
        List <listCasosDelPersonal> listPersonal = FXCollections.observableArrayList();
        try {
            String query = "select * from Reporte2Personal";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                listPersonal.add(new listCasosDelPersonal(
                        rs.getString("Nombres"),
                        rs.getString("Apellidos"),
                        rs.getDate("Fecha"),
                        rs.getString("Resultado"),
                        rs.getString("Departamento")));
            }
        } catch (SQLException e) {
            alertMessage("Error","listPersonal", e.getMessage(), Alert.AlertType.ERROR);
        }
        return listPersonal;
    }
    public ObservableList<modeloMonitoreo> getMonitoreo() throws SQLException{
        ObservableList<modeloMonitoreo> monito= FXCollections.observableArrayList();
        try{
            String query = "select O.noOrden, resultado, U.nombres, cvePrueba from Orden O inner join Consulta C on O.noConsulta = C.noConsulta inner join Medico M on C.noCedula = M.noCedula inner join Usuario U on C.noUsuario = U.noUsuario;";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                monito.add(new modeloMonitoreo(
                        rs.getString("noOrden"),
                        rs.getString("resultado"),
                        rs.getString("nombres"),
                        rs.getString("cvePrueba")
                ));
            }
        }catch (SQLException e){
        }
        return monito;
    }
}
