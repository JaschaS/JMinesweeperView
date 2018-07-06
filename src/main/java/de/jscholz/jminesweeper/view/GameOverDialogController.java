package de.jscholz.jminesweeper.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class GameOverDialogController {

    @FXML
    private Label gamePlayed;
    @FXML
    private Label gameWon;
    @FXML
    private Label percentage;
    @FXML
    private Label date;
    @FXML
    private Label bestTime;
    @FXML
    private Label timePlayed;
    @FXML
    private Label notification;
    @FXML
    private Label gameOverMessage;
    @FXML
    private Button exitGame;
    @FXML
    private Button restartGame;
    @FXML
    private Button newGame;

    public void setGamePlayed(final String gamePlayed) {
        assert gamePlayed != null : "Given game played is null!";
        assert this.gamePlayed != null : "GamePlayed Label is null!";

        this.gamePlayed.setText(gamePlayed);
    }

    public void setGameWon(final String gameWon) {
        assert gameWon != null : "Given game won is null!";
        assert this.gameWon != null : "GameWon Label is null!";

        this.gameWon.setText(gameWon);
    }

    public void setPercentage(final String percentage) {
        assert percentage != null : "Given percentage is null!";
        assert this.percentage != null : "Percentage Label is null!";

        this.percentage.setText(percentage);
    }

    public void setDate(final String date) {
        assert date != null : "Given date is null!";
        assert this.date != null : "Date Label is null!";

        this.date.setText(date);
    }

    public void setBestTime(final String bestTime) {
        assert bestTime != null : "Given best time is null!";
        assert this.bestTime != null : "BestTime Label is null!";

        this.bestTime.setText(bestTime);
    }

    public void setTimePlayed(final String timePlayed) {
        assert timePlayed != null : "Given time played is null!";
        assert this.timePlayed != null : "TimePlayed Label is null!";

        this.timePlayed.setText(timePlayed);
    }

    public void setNotification(final String notification) {
        assert notification != null : "Given notification is null!";
        assert this.notification != null : "Notification Label is null!";

        this.notification.setText(notification);
    }

    public void setGameOverMessage(final String message) {
        assert message != null : "Given message is null!";
        assert this.gameOverMessage != null : "GameOverMessage Label is null!";

        this.gameOverMessage.setText(message);
    }

    public void setRestartGameAction(final EventHandler<ActionEvent> click) {
        assert this.restartGame != null : "Restart Button is null!";

        this.restartGame.setOnAction(click);
    }

    public void setExitGameAction(final EventHandler<ActionEvent> click) {
        assert this.exitGame != null : "Exit Button is null!";

        this.exitGame.setOnAction(click);
    }

    public void setNewGameAction(final EventHandler<ActionEvent> click) {
        assert this.newGame != null : "New Button is null!";

        this.newGame.setOnAction(click);
    }
}
