package controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.net.URL;
import java.util.ResourceBundle;
public class Main implements Initializable {
    @FXML
    RadioButton rdp1si, rdp1no;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup group = new ToggleGroup();

        rdp1si.setToggleGroup(group);

        rdp1no.setToggleGroup(group);
    }
}
