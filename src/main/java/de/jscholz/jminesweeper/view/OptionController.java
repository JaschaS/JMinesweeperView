package de.jscholz.jminesweeper.view;

import de.jscholz.jminesweeper.minesweeper.Difficulty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        assert this.beginner != null : "beginner Radio-Button is null";
        assert this.intermediate != null : "intermediate Radio-Button is null";
        assert this.advanced != null : "advanced Radio-Button is null";
        assert this.custom != null : "custom Radio-Button is null";

        assert this.widthSlider != null : "widthSlider Slider is null";
        assert this.heightSlider != null : "heightSlider Slider is null";
        assert this.minesSlider != null : "minesSlider Slider is null";

        assert this.widthValue != null : "widthValue Label is null";
        assert this.heightValue != null : "heightValue Label is null";
        assert this.minesValue != null : "minesValue Label is null";

        assert this.continueGame != null : "continueGame Checkbox is null";
        assert this.saveGame != null : "saveGame Checkbox is null";

        assert this.ok != null : "ok Button is null";
        assert this.cancel != null : "cancel Button is null";

        this.widthSlider.valueProperty().addListener((final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) -> {
            this.widthValue.setText(newValue.intValue() + "");
        });

        this.heightSlider.valueProperty().addListener((final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) -> {
            this.heightValue.setText(newValue.intValue() + "");
        });

        this.minesSlider.valueProperty().addListener((final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue) -> {
            this.minesValue.setText(newValue.intValue() + "");
        });

        this.beginner.setOnAction((ActionEvent event) -> {
            this.setSlider(Difficulty.EASY, true);
        });
        this.intermediate.setOnAction((ActionEvent event) -> {
            this.setSlider(Difficulty.EXPERIENCED, true);
        });
        this.advanced.setOnAction((ActionEvent event) -> {
            this.setSlider(Difficulty.EXPERT, true);
        });
        this.custom.setOnAction((ActionEvent event) -> {
            this.setSlider(Difficulty.EXPERT, false);
        });

        this.beginner.setSelected(true);
        this.setSlider(Difficulty.EASY, true);
    }

    public void setOkOnAction(final EventHandler<ActionEvent> click) {
        assert this.ok != null : "Ok MenuItem is null!";

        this.ok.setOnAction(click);
    }

    public void setCancelOnAction(final EventHandler<ActionEvent> click) {
        assert this.cancel != null : "Cancel MenuItem is null!";

        this.cancel.setOnAction(click);
    }

    public int getDifficulty() {

        if(this.beginner.isSelected()) {
            return 0;
        }
        if (this.intermediate.isSelected()) {
            return 1;
        }
        if(this.advanced.isSelected()) {
            return 2;
        }

        return 3;
    }

    public int getWidth() {
        return Integer.parseInt(this.widthValue.getText());
    }

    public int getHeight() {
        return Integer.parseInt(this.heightValue.getText());
    }

    public int getMinesPercent() {
        return Integer.parseInt(this.minesValue.getText());
    }

    private void setSlider(final Difficulty difficulty, final boolean disable) {
        final int width = difficulty.getRows();
        final int height = difficulty.getColumns();
        final int mines = difficulty.getMinesPercent();

        this.widthSlider.setValue(width);
        this.widthSlider.setDisable(disable);
        this.widthValue.setText(width + "");

        this.heightSlider.setValue(height);
        this.heightSlider.setDisable(disable);
        this.heightValue.setText(height + "");

        this.minesSlider.setValue(mines);
        this.minesSlider.setDisable(disable);
        this.minesValue.setText(mines + "");
    }
}
