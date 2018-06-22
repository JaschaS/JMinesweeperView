package de.jscholz.jminesweeper.view;

import de.jscholz.jminesweeper.minesweeper.ICell;
import de.jscholz.jminesweeper.minesweeper.IMinefield;

import java.util.Set;

public class PrimaryClickListener extends ClickListener {

    public PrimaryClickListener(final IMinefield minefield, final ClickCallback callback) {
        super(minefield, callback);
    }

    @Override
    protected void singleClick(final int x, final int y) {

        final IMinefield.OpenReturn returnValue = this.minefield.singleClick(x, y);

        switch (returnValue) {
            case OPEN:
                System.out.println("Cell is now open.");

                assert this.callback != null : "Callback shouldn't be null!";

                callback.update();
                break;
            case IS_ALREADY_OPEN:
                System.out.println("Cell was already open.");
                break;
            case NOT_VALID:
                System.out.println("The given position is not valid: [x: " + x + ", y: " + y + "]");
                break;
            case GAME_IS_ALREADY_OVER:
                System.out.println("The game is already over!");
                break;
            case WAS_MINE:
                System.out.println("You clicked on a mine! Game Over!");
                this.callback.mineExploded(x, y);

                break;
            case WAS_FLAGGED:
                System.out.println("Cell is flagged!");
                break;
            case GAME_CLEARED:
                System.out.println("Game Cleared!");
                this.callback.gameCleared();
                break;
            default:
                System.out.println("Wrong return value - " + returnValue);
                break;
        }
    }

    @Override
    protected void doubleClick(final int x, final int y) {
        System.err.println("Not yet implemented");
    }
}
