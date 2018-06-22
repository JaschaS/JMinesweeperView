package de.jscholz.jminesweeper.view;

import de.jscholz.jminesweeper.minesweeper.ICell;
import de.jscholz.jminesweeper.minesweeper.IMinefield;

import java.util.Set;

public abstract class ClickListener implements ButtonClick {

    protected final ClickCallback callback;
    protected IMinefield minefield;

    public ClickListener(final IMinefield minefield, final ClickCallback callback) {
        assert minefield != null : "Minefield shouldn't be null!";
        assert callback != null : "Callback shouldn't be null!";

        this.minefield = minefield;
        this.callback = callback;
    }

    @Override
    public void click(final int x, final int y, final int clickCount) {

        switch (clickCount) {
            case 1:
                singleClick(x, y);
                break;
            case 2:
                doubleClick(x, y);
                break;
        }

    }

    public Set<ICell> getCells() {return minefield.getUpdateCells();}

    public ClickCallback getCallback() {
        return callback;
    }

    public IMinefield getMinefield() {
        return minefield;
    }

    public void setMinefield(IMinefield minefield) {
        this.minefield = minefield;
    }

    protected abstract void singleClick(final int x, final int y);

    protected abstract void doubleClick(final int x, final int y);
}
