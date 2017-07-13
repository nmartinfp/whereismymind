package org.academiadecodigo.mindblowers.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class JdbcService {

    private ConnectionManager connectionManager;
    private Connection dbConnection;

    public JdbcService() {
        connectionManager = new ConnectionManager();
        dbConnection = connectionManager.getConnection();
    }

    /**
     * Inserts and entry in the database with the player names and their score.
     * @param player1
     * @param player2
     * @param score
     */
    public void addScore(String player1, String player2, int score) {
        try {
            String query = "INSERT INTO user(name, score) VALUES (?, ?)";

            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, (player1 + " & " + player2));
            statement.setInt(2, score);
            statement.executeUpdate();

            if (statement != null) {
                statement.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
