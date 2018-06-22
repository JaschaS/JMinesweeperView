package de.jscholz.jminesweeper.database;

import java.net.URL;
import java.sql.*;

public class DatabaseHandler {

    private static final String DATABASE_PREFIX = "jdbc:sqlite:";
    private static final String DATABASE_NAME = "game";
    private static final String CREATE_OPTION_TABLE = "CREATE TABLE IF NOT EXISTS options (id integer PRIMARY KEY, difficulty integer not null, width integer, height integer, mines integer, save_game integer, load_game integer)";

    private final Connection databaseConnection;

    public DatabaseHandler() throws SQLException {
        final URL resource = getClass().getResource("/" + DATABASE_NAME + ".db");
        this.databaseConnection = DriverManager.getConnection(DATABASE_PREFIX + resource.toString());

    }

    public void setupDatabase() throws SQLException {
        final Statement statement = databaseConnection.createStatement();
        statement.execute(CREATE_OPTION_TABLE);
        //statement.execute("INSERT INTO options VALUES (1, 0, 8, 8, 16, 0, 0)");
        final ResultSet result = statement.executeQuery("select * from options");
        while (result.next()) {
            System.out.println(result.getInt("id"));
            System.out.println(result.getInt("difficulty"));
            System.out.println(result.getInt("width"));
            System.out.println(result.getInt("height"));
            System.out.println(result.getInt("mines"));
        }
    }

    public void saveOptionsData(final int difficulty, final int width, final int height, final int mines, final int saveGame, final int loadGame) throws SQLException {
        final Statement statement = databaseConnection.createStatement();
        statement.execute("INSERT INTO options VALUES (1, 0, 8, 8, 16, 0, 0)");
    }

    public void close() throws SQLException {
        if(databaseConnection != null) {
            databaseConnection.close();
        }
    }
}
