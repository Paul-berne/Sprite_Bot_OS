package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import control.Controller;
import model.Player;
import tools.BCrypt;

public class DataFileUser {
    
    // Spécification
    private Controller myController;
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    
    private Player lePlayer;

    // Implémentation
    public DataFileUser(Controller theController) {
        try {
            // Initialise le contrôleur et établit la connexion à la base de données
            this.myController = theController;
            String dbname = this.myController.getMyConfiguration().readProperty("database.url");
            String username = this.myController.getMyConfiguration().readProperty("database.username");
            String password = this.myController.getMyConfiguration().readProperty("database.password");

            this.connection = DriverManager.getConnection(dbname, username, password);

            this.statement = connection.createStatement();
        } catch (SQLException e) {
            // Gère les exceptions liées à la connexion à la base de données
            e.printStackTrace();
        }
    }

    // Méthode pour vérifier les informations de connexion de l'utilisateur
    public boolean lireUserSQL(String theLogin, String thePassword) throws ParseException {
        boolean verif = false;

        try {
            // Requête SQL pour récupérer les informations de tous les joueurs
            String sqlQuery = "SELECT login, password, player_name FROM player";
            resultSet = statement.executeQuery(sqlQuery);

            // Parcourt les résultats de la requête
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String name = resultSet.getString("player_name");

            	System.out.println(login + password + name);
            	
            	System.out.println(BCrypt.checkpw(thePassword, password));
            	
                // Vérifie si les informations de connexion correspondent
                if (login.equals(theLogin) && BCrypt.checkpw(thePassword, password)) {
                	System.out.println(login + password + name);
                	System.out.println(BCrypt.checkpw(thePassword, password));
                    verif = true;
                    this.lePlayer = new Player(theLogin, password, name);
                    break;
                }
            }
        } catch (SQLException e) {
            // Gère les exceptions liées à la lecture des informations de l'utilisateur
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return verif;
    }

 

	// Méthode pour changer le mot de passe de l'utilisateur
    public void ChangePasswordUser(String theLogin, String thePassword) {
        // Hash le nouveau mot de passe avec un sel
        String passwordToHash = thePassword;
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(passwordToHash, salt);

        // Requête SQL pour mettre à jour le mot de passe de l'utilisateur
        String sqlQuery = "UPDATE player SET password = '" + hashedPassword + "' WHERE login = '" + theLogin + "'";
        try {
            // Exécute la requête de mise à jour
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            // Gère les exceptions liées à la mise à jour du mot de passe
            e.printStackTrace();
        }
    }

    // Getter pour l'objet Player associé à l'utilisateur
    public Player getLePlayer() {
 		return lePlayer;
 	}
}
