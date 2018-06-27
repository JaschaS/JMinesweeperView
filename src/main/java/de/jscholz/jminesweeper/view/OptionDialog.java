package de.jscholz.jminesweeper.view;

import de.jscholz.jminesweeper.minesweeper.Difficulty;
import de.jscholz.jminesweeper.minesweeper.GameCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OptionDialog {

    private final Stage stage;
    private final OptionController controller;
    private final OptionCallback callback;

    public OptionDialog(final Stage parentStage, final OptionCallback callback) throws IOException {
        assert parentStage != null : "Given stage is null!";

        final FXMLLoader optionLoader = new FXMLLoader ( getClass ().getResource ( "/OptionDialog.fxml" ) );
        final Parent optionView = optionLoader.load();

        this.callback = callback;
        this.controller = optionLoader.getController();
        this.controller.setCancelOnAction((ActionEvent event) -> {
            hide();
        });
        this.controller.setOkOnAction((ActionEvent event) -> {

            final int difficulty = this.controller.getDifficulty();
            final int width = this.controller.getWidth();
            final int height = this.controller.getHeight();

            switch (difficulty) {
                case 0:
                    GameCreator.setGame(Difficulty.EASY);
                    break;
                case 1:
                    GameCreator.setGame(Difficulty.EXPERIENCED);
                    break;
                case 2:
                    GameCreator.setGame(Difficulty.EXPERT);
                    break;
                case 3:
                    final int minesPercent = this.controller.getMinesPercent();
                    GameCreator.setGame(width, height, minesPercent);
                    break;
                default:
                    throw new IndexOutOfBoundsException("There are not more than 4 difficulty levels.");
            }

            this.callback.resetGame(width, height);
            hide();
        });

        this.stage = new Stage();
        this.stage.centerOnScreen();
        this.stage.setTitle("Options");
        this.stage.setScene(new Scene(optionView));
        this.stage.initOwner(parentStage);
        this.stage.initModality(Modality.APPLICATION_MODAL);
    }

    public void show() {
        assert this.stage != null : "Stage is null!";

        this.stage.show();
    }

    public void hide() {
        assert this.stage != null : "Stage is null!";

        this.stage.hide();
    }

}
