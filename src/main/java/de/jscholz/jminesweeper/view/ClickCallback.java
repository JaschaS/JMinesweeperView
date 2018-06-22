package de.jscholz.jminesweeper.view;

public interface ClickCallback {
    void update();
    void gameCleared();
    void mineExploded(final int x, final int y);
}
