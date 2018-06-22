package de.jscholz.jminesweeper.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionController implements Initializable {

    @FXML
    private RadioButton beginner;
    @FXML
    private RadioButton intermediate;
    @FXML
    private RadioButton advanced;
    @FXML
    private RadioButton custom;
    @FXML
    private Slider widthSlider;
    @FXML
    private Slider heightSlider;
    @FXML
    private Slider minesSlider;
    @FXML
    private Label widthValue;
    @FXML
    private Label heightValue;
    @FXML
    private Label minesValue;
    @FXML
    private CheckBox continueGame;
    @FXML
    private CheckBox saveGame;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert beginner != null : "beginner Radio-Button is null";
        assert intermediate != null : "intermediate Radio-Button is null";
        assert advanced != null : "advanced Radio-Button is null";
        assert custom != null : "custom Radio-Button is null";

        assert widthSlider != null : "widthSlider Slider is null";
        assert heightSlider != null : "heightSlider Slider is null";
        assert minesSlider != null : "minesSlider Slider is null";

        assert widthValue != null : "widthValue Label is null";
        assert heightValue != null : "heightValue Label is null";
        assert minesValue != null : "minesValue Label is null";

        assert continueGame != null : "continueGame Checkbox is null";
        assert saveGame != null : "saveGame Checkbox is null";

        assert ok != null : "ok Button is null";
        assert cancel != null : "cancel Button is null";
    }
}
