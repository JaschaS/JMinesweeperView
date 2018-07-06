package de.jscholz.jminesweeper.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverDialog {

    private final Stage stage;
    private final GameOverDialogController controller;
    private final GameOverCallback callback;

    public GameOverDialog(final Stage parentStage, final GameOverCallback callback) throws IOException {
        assert parentStage != null : "Given stage is null!";

        final FXMLLoader gameOverLoader = new FXMLLoader ( getClass ().getResource ( "/GameOverDialog.fxml" ) );
        final Parent optionView = gameOverLoader.load();

        this.callback = callback;
        this.controller = gameOverLoader.getController();
        this.controller.setExitGameAction((ActionEvent event) -> {
            callback.exitGame();
            hide();
        });
        this.controller.setRestartGameAction((ActionEvent event) -> {
            callback.restartGame();
            hide();
        });
        this.controller.setNewGameAction((ActionEvent event) -> {
            callback.newGame();
            hide();
        });

        this.stage = new Stage();
        this.stage.centerOnScreen();
        this.stage.setTitle("Game Over");
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
