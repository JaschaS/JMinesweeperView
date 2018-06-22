package de.jscholz.jminesweeper.view;

import de.jscholz.jminesweeper.minesweeper.CellContent;
import de.jscholz.jminesweeper.minesweeper.CellState;
import de.jscholz.jminesweeper.minesweeper.ICell;
import de.jscholz.jminesweeper.minesweeper.ICellPosition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Set;

import static de.jscholz.jminesweeper.view.MinesweeperView.CELL_SIZE;

public class MinesweeperController {

    @FXML
    private GridPane field;
    @FXML
    private MenuItem newGame;
    @FXML
    private MenuItem loadGame;
    @FXML
    private MenuItem quit;

    private ButtonClick primaryClick;
    private ButtonClick secondaryClick;
    private HashMap<ICellPosition, Button> cellButtons;

    public MinesweeperController() {
        this.cellButtons = new HashMap<>();
    }

    public void initialize() {
        assert this.newGame != null : "NewGame MenuItem is null!";

        this.field.setId("gridpane");
        this.newGame.fire();
    }

    public void initField(final Set<ICellPosition> positionSet) {

        this.cellButtons.clear();
        this.field.getChildren().clear();

        for(final ICellPosition position : positionSet) {

            final int x = position.getX();
            final int y = position.getY();
            //final CellButton button = new CellButton(x, y, 32, 32);
            //Image image = new Image(getClass().getResourceAsStream("/test.png"));
            final Button button = new Button("");
            button.setId("UndiscoveredCell");
            button.setMinSize(CELL_SIZE, CELL_SIZE);
            //button.setOnClicked(event -> {
            button.setOnMouseClicked(event -> {
                final MouseButton type = event.getButton();
                final int clickCount = event.getClickCount();
                switch (type) {
                    case PRIMARY:
                        this.primaryClick.click(x, y, clickCount);
                        break;
                    case SECONDARY:
                        this.secondaryClick.click(x, y, clickCount);
                        break;
                }
            });
            this.cellButtons.put(position, button);

            this.field.add(button, x, y);
        }

    }

    public void setNewGameOnAction(final EventHandler<ActionEvent> click) {
        assert this.newGame != null : "NewGame MenuItem is null!";

        this.newGame.setOnAction(click);
    }

    public void setLoadGameOnAction(final EventHandler<ActionEvent> click) {
        assert this.loadGame != null : "LoadGame MenuItem is null!";

        this.loadGame.setOnAction(click);
    }

    public void setQuitOnAction(final EventHandler<ActionEvent> click) {
        assert this.quit != null : "Quit MenuItem is null!";

        this.quit.setOnAction(click);
    }

    public void setPrimaryClick(final ButtonClick onClick) {
        this.primaryClick = onClick;
    }

    public void setSecondaryClick(final ButtonClick onClick) {
        this.secondaryClick = onClick;
    }

    public void openButton(final ICellPosition position, final ICell cell, final String displayCharacter) {

        assert displayCharacter != null : "Display Character shouldn't be null!";
        assert position != null : "Position shouldn't be null!";
        assert cell != null : "CellContent shouldn't be null!";

        //final CellButton button = this.cellButtons.get(position);
        final Button button = this.cellButtons.get(position);
        assert button != null : "Button shouldn't be null!";

        final CellState state = cell.getCellState();

        switch (state) {
            case FLAGGED:
                button.setId("flaggedCell");
                break;
            case UNDISCOVERED:
                button.setId("UndiscoveredCell");
                break;
            case OPEN:
                final CellContent content = cell.getCellContent();

                //button.setButtonText(displayCharacter);
                button.setText(displayCharacter);

                switch (content) {
                    case EMPTY:
                        //button.setDisableButton(true);
                        button.setId("OpenCell");
                        //button.setDisable(true);
                        break;
                    case MINE:
                        break;
                    case UNKNOWN:
                        break;
                    default:
                        //button.setButtonId("numberButton");
                        //button.setTextStyle("-fx-text-fill: " + NUMBER_COLOR.get(content) + ";");
                        button.setId(content.toString());
                        break;
                }
                break;
        }

    }

    public void openButtonGameOver(final ICellPosition position, final ICell cell,  final String displayCharacter,
                                   final boolean wasExplodedMine) {

        assert displayCharacter != null : "Display Character shouldn't be null!";
        assert position != null : "Position shouldn't be null!";
        assert cell != null : "Cell shouldn't be null!";

        //final CellButton button = this.cellButtons.get(position);
        final Button button = this.cellButtons.get(position);

        assert button != null : "Button shouldn't be null!";

        //button.setButtonText(displayCharacter);
        button.setText(displayCharacter);

        // We disable all buttons
        //button.setDisableButton(true);
        button.setDisable(true);

        final CellContent content = cell.getCellContent();
        final CellState state = cell.getCellState();

        switch (state) {
            case FLAGGED:
                button.setId("MineFlagged");
                break;
            default:
                switch (content) {
                    case EMPTY:
                        button.setId("OpenCell");
                        break;
                    case MINE:
                        if(wasExplodedMine) {
                            //button.setButtonStyle("-fx-background-color: red;");
                            //button.setStyle("-fx-background-color: red;");
                            button.setId("MineExploded");
                        }
                        else {
                            // Check if mine was flagged
                            button.setId("Mine");
                        }

                        break;
                    default:
                        //button.setTextStyle("-fx-text-fill: " + NUMBER_COLOR.get(content) + ";");
                        button.setId(content.toString());
                        break;
                }
                break;
        }

    }

}
