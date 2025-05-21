package com.example.alfredAI.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class which establishes a connection with the database
 */
public class SqliteConnection {
    private static Connection instance = null;

    /**
     * Private constructor which instantiates the connection instance
     */
    private SqliteConnection() {
        String url = "jdbc:sqlite:alfred.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Gets the database connection instance
     * @return The Connection instance
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
}