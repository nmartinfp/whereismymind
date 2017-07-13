package org.academiadecodigo.mindblowers.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Developed @ <Academia de Código_>
 * Created by
 * <Code Cadet> Filipe Santos Sá
 */

public class ConnectionManager {

    Connection connection = null;


    public Connection getConnection() {

        if (connection == null) {

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mymind", "root", "");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failure to connect to database : " + e.getMessage());
                System.exit(1);
            }
        }
        return connection;

    }


    public void close() {

        if (connection != null) {

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Failure to close database connections: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
