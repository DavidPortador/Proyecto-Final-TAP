package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.*;
import modelosReportes.*;
import java.sql.*;
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
    public ObservableList<String> getPruebas() throws SQLException {
        ObservableList <String> pruebas = FXCollections.observableArrayList();
        try {
            String query = "select tipo from Prueba";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                pruebas.add(
                        rs.getString("tipo")
                );
            }
        } catch (SQLException e) {
            alertMessage("Error","getPruebas", e.getMessage(), Alert.AlertType.ERROR);
        }
        return pruebas;
    }
    public ObservableList<modeloSolicitud> getSolicitudes() throws SQLException {
        ObservableList <modeloSolicitud> solicitudes = FXCollections.observableArrayList();
        try {
            String query = "select * from Solicitud";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                solicitudes.add(new modeloSolicitud(
                        rs.getInt("noSolicitud"),
                        rs.getString("estado"),
                        rs.getString("tipo"),
                        rs.getString("cveAsignacion"),
                        rs.getInt("noUsuario"),
                        rs.getString("noCedula")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getSolicitudes", e.getMessage(), Alert.AlertType.ERROR);
        }
        return solicitudes;
    }
    public ObservableList<modeloConsulta> getConsultas() throws SQLException {
        ObservableList <modeloConsulta> consultas = FXCollections.observableArrayList();
        try {
            String query = "select * from Consulta";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                consultas.add(new modeloConsulta(
                        rs.getInt("noConsulta"),
                        rs.getString("sintomas"),
                        rs.getDate("fecha"),
                        rs.getString("hora"),
                        rs.getString("tipo"),
                        rs.getString("cveAsignacion"),
                        rs.getInt("noUsuario"),
                        rs.getString("noCedula")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getConsultas", e.getMessage(), Alert.AlertType.ERROR);
        }
        return consultas;
    }
    public ObservableList<modeloConsulta> getConsultaUsuario(int noUsuario) throws SQLException {
        ObservableList <modeloConsulta> consultas = FXCollections.observableArrayList();
        try {
            String query = "select * from Consulta where noUsuario = " + noUsuario;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                consultas.add(new modeloConsulta(
                        rs.getInt("noConsulta"),
                        rs.getString("sintomas"),
                        rs.getDate("fecha"),
                        rs.getString("hora"),
                        rs.getString("tipo"),
                        rs.getString("cveAsignacion"),
                        rs.getInt("noUsuario"),
                        rs.getString("noCedula")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getConsultaUsuario", e.getMessage(), Alert.AlertType.ERROR);
        }
        return consultas;
    }
    public ObservableList<modeloOrden> getOrdenUsuario(int noUsuario) throws SQLException {
        ObservableList <modeloOrden> ordenes = FXCollections.observableArrayList();
        try {
            String query = "select O.noOrden, O.resultado, O.noConsulta, O.noCedula, O.cvePrueba from Orden O inner join Consulta C on O.noConsulta = C.noConsulta where C.noUsuario = " + noUsuario;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ordenes.add(new modeloOrden(
                        rs.getInt("noOrden"),
                        rs.getString("resultado"),
                        rs.getInt("noConsulta"),
                        rs.getString("noCedula"),
                        rs.getString("cvePrueba")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getConsultaUsuario", e.getMessage(), Alert.AlertType.ERROR);
        }
        return ordenes;
    }
    public ObservableList<modeloOrden> getOrdenes() throws SQLException {
        ObservableList <modeloOrden> ordenes = FXCollections.observableArrayList();
        try {
            String query = "select * from Orden";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ordenes.add(new modeloOrden(
                        rs.getInt("noOrden"),
                        rs.getString("resultado"),
                        rs.getInt("noConsulta"),
                        rs.getString("noCedula"),
                        rs.getString("cvePrueba")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getConsultaUsuario", e.getMessage(), Alert.AlertType.ERROR);
        }
        return ordenes;
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
    public List <listConsultasTotalMedicos> getListConsultasTotalMedicos() {
            List <listConsultasTotalMedicos> listTotalMedicos = FXCollections.observableArrayList();
            try {
                String query = "select * from Grafica1";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    listTotalMedicos.add(new listConsultasTotalMedicos(
                            rs.getString("Medico"),
                            rs.getInt("Consultas")));
                }
            } catch (SQLException e) {
                alertMessage("Error","listMedicos", e.getMessage(), Alert.AlertType.ERROR);
            }
            return listTotalMedicos;
    }
    public List <modeloOrden> getListOrdenes(int usuario) {
        List <modeloOrden> modeloOrdenes = FXCollections.observableArrayList();
        try {
            String query = "select O.noOrden, O.resultado, O.noConsulta, O.noCedula, O.cvePrueba from Orden O inner join Consulta C on O.noConsulta = C.noConsulta where noUsuario ="+usuario;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                modeloOrdenes.add(new modeloOrden(
                        rs.getInt("noOrden"),
                        rs.getString("resultado"),
                        rs.getInt("noConsulta"),
                        rs.getString("noCedula"),
                        rs.getString("cvePrueba")));
            }
        } catch (SQLException e) {
            alertMessage("Error","listMedicos", e.getMessage(), Alert.AlertType.ERROR);
        }
        return modeloOrdenes;
    }
    public List <modeloReceta> getmodeloReceta(int usuario) {
        List <modeloReceta> modeloReceta = FXCollections.observableArrayList();
        try {
            String query = "select R.noReceta, R.indicaciones, R.noConsulta from Receta R inner join Consulta C on R.noConsulta = C.noConsulta where C.noUsuario = "+usuario;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                modeloReceta.add(new modeloReceta(
                        rs.getInt("noReceta"),
                        rs.getString("indicaciones"),
                        rs.getString("noConsulta")));
            }
        } catch (SQLException e) {
            alertMessage("Error","listMedicos", e.getMessage(), Alert.AlertType.ERROR);
        }
        return modeloReceta;
    }
    // Operaciones del CRUD
    // Inserts
    public boolean insertNewSolicitud(modeloSolicitud solicitud) {
        // Se le asignan sus datos al personal
        try {
            String query = "insert into Solicitud (estado, tipo, cveAsignacion, noUsuario, noCedula)" +
                    "values (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, solicitud.getEstado());
            ps.setString(2, solicitud.getTipo());
            ps.setString(3, solicitud.getCveAsignacion());
            ps.setInt(4, solicitud.getNoUsuario());
            ps.setString(5, solicitud.getNoCedula());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertNewSolicitud", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    public boolean insertNewConsulta(modeloConsulta consulta) {
        // Se le asignan sus datos al personal
        try {
            String query = "insert into Consulta (sintomas, fecha, hora, tipo, cveAsignacion, noUsuario, noCedula)" +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, consulta.getSintomas());
            ps.setDate(2, consulta.getFecha());
            ps.setString(3, consulta.getHora());
            ps.setString(4, consulta.getTipo());
            ps.setString(5, consulta.getCveAsignacion());
            ps.setInt(6, consulta.getNoUsuario());
            ps.setString(7, consulta.getNoCedula());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertNewConsulta", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    public boolean insertNewOrden(modeloOrden orden) {
        // Se le asignan sus datos al personal
        try {
            String query = "insert into Orden (resultado, noConsulta, noCedula, cvePrueba) values " +
                    "(?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, orden.getResultado());
            ps.setInt(2, orden.getNoConsulta());
            ps.setString(3, orden.getNoCedula());
            ps.setString(4, orden.getCvePrueba());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertNewOrden", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    public boolean insertAlertaMonitoreada(modeloAlertaM alertaM) {
        try {
            String query = "insert into AlertaMonitoreada (descripcion, noOrden, noMonitoreo) values" +
                    "(?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, alertaM.getDescripcion());
            ps.setInt(2, alertaM.getNoOrden());
            ps.setString(3, alertaM.getNoMonitoreo());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertAlertaMonitoreada", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
    // Update
    public boolean setEstadoSolicitud(int noSolicitud) {
        // Se le asignan sus datos al personal
        try {
            String query = "update Solicitud set estado = 'Aceptado' where noSolicitud = " + noSolicitud;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","setEstadoSolicitud", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
}
