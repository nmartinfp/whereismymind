package org.academiadecodigo.mindblowers.server;

import java.sql.*;

/**
 * Developed @ <Academia de Código_>
 * Created by Team MindBlowers:
 * @ <Academia de Código_>'s 8th Bootcamp Hackathon - WhereIsMyMind?
 * <Code Cadets> Filipe Santos Sá, Igor Busquets, Diogo Costa, João Fernandes, Nelson Pereira
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
     * @param team
     * @param score
     */
    public void addScore(String team, int score) {
        try {
            String query = "INSERT INTO user(name, score) VALUES (?, ?)";

            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, team);
            statement.setInt(2, score);
            statement.executeUpdate();

            closeStatement(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int findTeamByName(String team) {

        try {
            String query = "SELECT id FROM user WHERE name = ?";

            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setString(1, team);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idTeam = resultSet.getInt("id");

                closeStatement(statement);

                return idTeam;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public void updateScore(int id, int score) {

        String query = "UPDATE user SET score = ? WHERE id = ?";

        try {
            PreparedStatement statement = dbConnection.prepareStatement(query);
            statement.setInt(1, score);
            statement.setInt(2, id);
            statement.executeUpdate();

            closeStatement(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void closeStatement(Statement statement) throws SQLException {

        if (statement != null) {
            statement.close();
        }
    }
}
