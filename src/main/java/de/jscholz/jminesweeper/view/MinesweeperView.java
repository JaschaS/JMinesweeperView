package de.jscholz.jminesweeper.view;

import de.jscholz.jminesweeper.minesweeper.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MinesweeperView extends Application implements ClickCallback, OptionCallback {

    public static void main(final String[] args) {
        /**
         * GUI:
         * - Zellen sollen einen doppel-klick erhalten
         * - Zellen sollen auch doppel-klickbar sein, wenn sie bereits aufgedeckt worden sind.
         * - Flag auf zahl wird anschließend grün markiert.
         * - Game Over dialog
         * - Game Cleared dialog
         * - Statistik hinzufuegen
         * - Achievements hinzufuegen
         * - Spiel laden
         * - Spiel speichern
         * - Schwierigkeitsgrad wechseln
         * - Hilfe hinzufuegen
         * - Save Options
         */
        launch ( args );
    }

    public static final int CELL_SIZE = 32;
    public static final int CELL_MARGIN = 2;
    private final static HashMap<CellState, CharacterDesign> STATE_CHARACTER;
    private final static HashMap<CellContent, String> CONTENT_CHARACTER;

    static {
        CONTENT_CHARACTER = new HashMap<>();
        CONTENT_CHARACTER.put(CellContent.MINE, "X");
        CONTENT_CHARACTER.put(CellContent.EIGHT, "8");
        CONTENT_CHARACTER.put(CellContent.SEVEN, "7");
        CONTENT_CHARACTER.put(CellContent.SIX, "6");
        CONTENT_CHARACTER.put(CellContent.FIVE, "5");
        CONTENT_CHARACTER.put(CellContent.FOUR, "4");
        CONTENT_CHARACTER.put(CellContent.THREE, "3");
        CONTENT_CHARACTER.put(CellContent.TWO, "2");
        CONTENT_CHARACTER.put(CellContent.ONE, "1");
        CONTENT_CHARACTER.put(CellContent.EMPTY, "");

        STATE_CHARACTER = new HashMap<>();
        STATE_CHARACTER.put(CellState.FLAGGED, (final ICell cell) -> "F" );
        STATE_CHARACTER.put(CellState.UNDISCOVERED, (final ICell cell) -> "");
        STATE_CHARACTER.put(CellState.OPEN, (final ICell cell) -> CONTENT_CHARACTER.get(cell.getCellContent()));
    }

    private final PrimaryClickListener primaryClickListener;
    private final SecondaryClickListener secondaryClickListener;
    private OptionDialog optionDialog;
    private IMinefield minefield;
    private Map<ICellPosition, ICell> field;
    private MinesweeperController controller;
    private Stage primaryStage;

    public MinesweeperView() {
        GameCreator.setGame(Difficulty.EXPERT);
        this.minefield =  GameCreator.createGame();
        this.field = minefield.getFieldForVisualization();
        this.controller = null;
        this.primaryClickListener = new PrimaryClickListener(this.minefield, this);
        this.secondaryClickListener = new SecondaryClickListener(this.minefield, this);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        //1x2x3x4x5
        final FXMLLoader viewLoader = new FXMLLoader ( getClass ().getResource ( "/Minesweeper.fxml" ) );

        this.optionDialog = new OptionDialog(primaryStage, this);

        final Parent view = viewLoader.load();
        this.controller = viewLoader.getController();
        this.controller.setPrimaryClick(this.primaryClickListener);
        this.controller.setSecondaryClick(this.secondaryClickListener);
        this.controller.setNewGameOnAction((ActionEvent event) -> {
            this.minefield = GameCreator.createGame();
            this.primaryClickListener.setMinefield(this.minefield);
            this.secondaryClickListener.setMinefield(this.minefield);
            this.field.clear();
            this.field.putAll(this.minefield.getFieldForVisualization());
            this.controller.initField(this.field.keySet());
        });
        this.controller.setQuitOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        this.controller.setOptionsOnAction((ActionEvent event) -> {
            this.optionDialog.show();
        });
        this.controller.initialize();

        final Scene scene = new Scene( view );
        scene.getStylesheets().add("/default_style.css");

        this.primaryStage.centerOnScreen ();
        this.primaryStage.setTitle ( "Minesweeper" );
        this.primaryStage.setScene ( scene );
        this.primaryStage.setWidth(getWidth());
        this.primaryStage.setHeight(getHeight());
        this.primaryStage.show ();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void update() {
        final Set<ICell> cells = this.minefield.getUpdateCells();

        assert cells.size() > 0 : "Size should be greater than 0! Otherwise don't call update";

        long startTime = System.nanoTime();

        for (final ICell cell : cells) {
            final ICellPosition position = cell.getPosition();

            this.field.put(position, cell);

            final String displayCharacter = STATE_CHARACTER.get(cell.getCellState()).getCharacter(cell);

            assert displayCharacter != null : "Display Character shouldn't be null!";

            this.controller.openButton(position, cell, displayCharacter);
        }

        long endTime = System.nanoTime() - startTime;
        System.out.println("Update " + TimeUnit.NANOSECONDS.toMillis(endTime));
    }

    @Override
    public void gameCleared() {
        final Set<ICell> cells = this.minefield.getUpdateCells();

        assert cells.size() > 0 : "Size should be greater than 0! Otherwise don't call update";

        for (final ICell cell : cells) {
            final ICellPosition position = cell.getPosition();

            this.field.put(position, cell);

            final String displayCharacter = CONTENT_CHARACTER.get(cell.getCellContent());

            assert displayCharacter != null : "Display Character shouldn't be null!";

            this.controller.openButtonGameOver(position, cell, displayCharacter, false);
        }
    }

    @Override
    public void mineExploded(final int x, final int y) {
        final Set<ICell> cells = this.minefield.getUpdateCells();

        assert cells.size() > 0 : "Size should be greater than 0! Otherwise don't call update";

        long startTime = System.nanoTime();

        for (final ICell cell : cells) {
            final ICellPosition position = cell.getPosition();

            this.field.put(position, cell);

            final String displayCharacter = CONTENT_CHARACTER.get(cell.getCellContent());

            assert displayCharacter != null : "Display Character shouldn't be null!";

            final boolean isExplodedMine = position.compare(x, y);

            this.controller.openButtonGameOver(position, cell, displayCharacter, isExplodedMine);
        }

        long endTime = System.nanoTime() - startTime;
        System.out.println("Game Over " + TimeUnit.NANOSECONDS.toMillis(endTime));
    }

    @Override
    public void resetGame(final int width, final int height) {
        this.primaryStage.setWidth(getWidth(width));
        this.primaryStage.setHeight(getHeight(height));
        this.primaryStage.centerOnScreen ();
        this.controller.initialize();
    }

    private int getWidth() {
        final int rows = this.minefield.getRows();
        return getWidth(rows);
    }

    private int getWidth(final int rows) {
        return CELL_SIZE * rows + CELL_MARGIN * (rows+1);
    }

    private int getHeight() {
        final int cols = this.minefield.getColumns();
        return getHeight(cols);
    }

    private int getHeight(final int cols) {
        return CELL_SIZE * cols + CELL_MARGIN * (cols+1) + 51;
    }

    private interface CharacterDesign {
        String getCharacter(final ICell cell);
    }
}
