package database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import modelos.modeloEncuesta;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
