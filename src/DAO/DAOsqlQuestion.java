package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import control.Controller;

public class DAOsqlQuestion {

    private Controller myController;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private String[] questions = new String[2];

    // Implémentation
    public DAOsqlQuestion(Controller theController) {
        try {
            // Initialise le contrôleur et établit la connexion à la base de données
            this.myController = theController;
            String dbname = this.myController.getMyConfiguration().readProperty("databaseprivate.url");
            String username = this.myController.getMyConfiguration().readProperty("database.username");
            String password = this.myController.getMyConfiguration().readProperty("database.password");

            this.connection = DriverManager.getConnection(dbname, username, password);

            this.statement = connection.createStatement();
        } catch (SQLException e) {
            // Gère les exceptions liées à la connexion à la base de données
            e.printStackTrace();
        }
    }

    // Getter pour le tableau de questions
    public String[] getQuestions() {
        return questions;
    }

    // Méthode pour lire la question associée à un numéro de question depuis la base de données
    public void LireQuestionSQL(int numeroQuestion) {
        try {
            // Requête SQL pour récupérer la question associée au numéro spécifié
            String sqlQuery = "select question.id_question, descriptionquestion from question where id_question = " + numeroQuestion + ";";
            resultSet = statement.executeQuery(sqlQuery);

            // Parcourt les résultats de la requête
            while (resultSet.next()) {
                int questionId = resultSet.getInt("id_question");
                String questionDesc = resultSet.getString("descriptionquestion");

                // Remplit le tableau de questions avec les données lues
                questions[0] = String.valueOf(questionId);
                questions[1] = questionDesc;
            }

        } catch (Exception e) {
            // Gère les exceptions liées à la lecture de la question depuis la base de données
            e.printStackTrace();
        } finally {
            // Ferme les ressources (resultSet, statement, connection)
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
