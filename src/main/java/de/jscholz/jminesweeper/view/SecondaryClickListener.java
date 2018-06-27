package de.jscholz.jminesweeper.view;

import de.jscholz.jminesweeper.minesweeper.IMinefield;

public class SecondaryClickListener extends ClickListener  {

    public SecondaryClickListener(final IMinefield minefield, final ClickCallback callback) {
        super(minefield, callback);
    }

    @Override
    protected void singleClick(final int x, final int y) {
        final IMinefield.OpenReturn returnValue = minefield.secondaryClick(x, y);

        switch (returnValue) {
            case NOW_FLAGGED:
                //System.out.println("Cell is now flagged.");
                callback.update();

                break;
            case REMOVE_FLAG:

                //System.out.println("Cell is no longer flagged.");
                callback.update();

                break;
            default:
                //System.out.println("Wrong return value - " + returnValue);
                break;
        }
    }

    @Override
    protected void doubleClick(final int x, final int y) {
        System.err.println("Not yet implemented");
    }
}
