package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.Usuario;
import modelos.modeloEncuesta;

import java.sql.*;
import java.util.ArrayList;
public class EncuestaDAO {
    Connection conn;
    public EncuestaDAO (Connection conn) {
        this.conn = conn;
    }
    private void alertMessage(String title, String Header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(Header);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public ObservableList<modeloEncuesta> getTableEncuestas() throws SQLException {
        ObservableList <modeloEncuesta> encuestas = FXCollections.observableArrayList();
        try {
            String query = "select * from Encuesta";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                ArrayList <Integer> Nrespuestas = new ArrayList<>();
                ArrayList <String> respuestas = new ArrayList<>();
                for(int i = 1; i < 14; i++){
                    Nrespuestas.add(rs.getInt("respuesta"+i));
                    //System.out.println(Nrespuestas.get(i-1));
                }
                for(int i = 0; i < 13; i++){
                    respuestas.add(getRespuesta(Nrespuestas.get(i)));
                    //System.out.println(respuestas.get(i));
                }
                encuestas.add(new modeloEncuesta(
                        rs.getInt("noEncuesta"),
                        respuestas.get(0),
                        respuestas.get(1),
                        respuestas.get(2),
                        respuestas.get(3),
                        respuestas.get(4),
                        respuestas.get(5),
                        respuestas.get(6),
                        respuestas.get(7),
                        respuestas.get(8),
                        respuestas.get(9),
                        respuestas.get(10),
                        respuestas.get(11),
                        respuestas.get(12),
                        rs.getString("otrosSintomas"),
                        rs.getInt("noUsuario")
                ));
            }
        } catch (SQLException e) {
            alertMessage("Error","getTableEncuestas", e.getMessage(), Alert.AlertType.ERROR);
        }
        return encuestas;
    }
    public String getRespuesta(int respuesta){
        String resultado = null;
        if(respuesta == 0)
            resultado = "No";
        else if(respuesta == 1)
            resultado = "Si";
        return resultado;
    }
    public boolean valiSintoma(int respuesta){
        boolean bandera = false;
        if (respuesta == 1)
            bandera = true;
        return bandera;
    }
    // Operaciones del CRUD
    public boolean insertNewEncuesta(Usuario usuario, int[] respuestas, String otros) {
        try {
            String query = "insert into Encuesta (respuesta1, respuesta2, respuesta3, respuesta4, " +
                    "respuesta5, respuesta6, respuesta7, respuesta8, respuesta9, respuesta10, " +
                    "respuesta11, respuesta12, respuesta13, otrosSintomas, noUsuario) values " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, respuestas[0]);
            ps.setInt(2, respuestas[1]);
            ps.setInt(3, respuestas[2]);
            ps.setInt(4, respuestas[3]);
            ps.setInt(5, respuestas[4]);
            ps.setInt(6, respuestas[5]);
            ps.setInt(7, respuestas[6]);
            ps.setInt(8, respuestas[7]);
            ps.setInt(9, respuestas[8]);
            ps.setInt(10, respuestas[9]);
            ps.setInt(11, respuestas[10]);
            ps.setInt(12, respuestas[11]);
            ps.setInt(13, respuestas[12]);
            ps.setString(14, otros);
            ps.setInt(15, usuario.getNoUsuario());
            ps.execute();
            return true;
        } catch (SQLException e) {
            alertMessage("Error","insertNewEncuesta", e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }
    }
}
