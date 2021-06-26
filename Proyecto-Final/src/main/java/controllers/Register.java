package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
public class Register implements Initializable {
    @FXML ComboBox cbTipoUsuario, cbAsignacion, cbGenero;
    @FXML TextField txtNo_PE, txtUsuario, txtNombres, txtApellidos, txtCorreo;
    @FXML PasswordField psfContra;
    @FXML DatePicker dpFecha;
    @Override public void initialize(URL location, ResourceBundle resources) {
        llenarTipoUsuario();
        llenarAsignacion();
        llenarGenero();
        defaultMode();
    }
    private void llenarTipoUsuario() {
        ObservableList<String> generos = FXCollections.observableArrayList();
        generos.add("Estudiante");
        generos.add("Personal");
        cbTipoUsuario.setItems(generos);
    }
    private void llenarGenero() {
        ObservableList<String> generos = FXCollections.observableArrayList();
        generos.add("M");
        generos.add("F");
        cbGenero.setItems(generos);
    }
    private void llenarAsignacion(){
        String tipoUsuario;
        tipoUsuario = (String) cbTipoUsuario.getSelectionModel().getSelectedItem();
    }
    private void defaultMode(){
        cbTipoUsuario.setDisable(false);
        cbAsignacion.setDisable(true);
        txtNo_PE.setDisable(true);
        txtUsuario.setDisable(true);
        psfContra.setDisable(true);
        txtNombres.setDisable(true);
        txtApellidos.setDisable(true);
        cbGenero.setDisable(true);
        txtCorreo.setDisable(true);
        dpFecha.setDisable(true);
    }
    private void personalMode(){

    }
    private void estudianteMode(){

    }
}
