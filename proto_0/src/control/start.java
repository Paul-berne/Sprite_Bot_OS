package control;

import java.sql.Connection;
import java.sql.SQLException;

public class start {

    public static void main(String[] args) {
        controller gameController = new controller();

        gameController.startGame();

        testDatabaseConnection();
    }

    public static void testDatabaseConnection() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            System.out.println("Database connection established successfully.");
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Failed to establish a database connection.");
        }
    }
}
