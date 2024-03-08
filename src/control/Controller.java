package control;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.SwingUtilities;
import model.*;
import view.*;
import DAO.*;

public class Controller {

    // Spécification
    private QuizGame theGame;
    private Player lePlayer;
    private DAOsqlQuestion leStubQuestion;
    private DAOsqlAnswer leStubAnswer;
    private ArrayList<Question> lesQuestions;
    private ArrayList<Answer> lesReponses;
    private Login myLogin;
    private Configuration myConfiguration;

    // Implémentation
    public Controller() throws ParseException, SQLException {
        // Initialise la configuration, la fenêtre de connexion et charge les questions
        this.myConfiguration = new Configuration();
        this.myLogin = new Login(this);
        this.myLogin.setLocation(100, 100);
        myLogin.setVisible(true);
        initializeQuestions();
    }

    private void initializeQuestions() {
        // Initialise la liste de questions de manière aléatoire
        lesQuestions = new ArrayList<Question>();
        Random random = new Random();
        ArrayList<Integer> numerosUtilises = new ArrayList<Integer>();

        for (int i = 0; i < 5; i++) {
            int randomNumber;

            do {
                randomNumber = random.nextInt(20) + 1;
            } while (numerosUtilises.contains(randomNumber));

            numerosUtilises.add(randomNumber);

            // Charge la question à partir de la base de données
            this.leStubQuestion = new DAOsqlQuestion(this);
            this.leStubQuestion.LireQuestionSQL(randomNumber);
            String[] question = this.leStubQuestion.getQuestions();

            // Crée un objet Question et l'ajoute à la liste
            Question laQuestion = new Question(Integer.valueOf(question[0]), question[1], new ArrayList<Answer>());
            lesQuestions.add(laQuestion);

            // Charge les réponses à partir de la base de données
            this.leStubAnswer = new DAOsqlAnswer(this);
            this.leStubAnswer.LireAnswerSQL(randomNumber);
            this.lesReponses = leStubAnswer.getLesAnswer();
            laQuestion.setAnswers(lesReponses);
        }
    }

    // Vérifie les informations de connexion de l'utilisateur
    public boolean verifyUserLogin(String login, String password) {
        DataFileUser dataFileUser = new DataFileUser(this);
        try {
            if (dataFileUser.lireUserSQL(login, password)) {
            	this.lePlayer = dataFileUser.getLePlayer();
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Crée une fenêtre de changement de mot de passe
    public void CreateFrameChangePassword(String thelogin) {
        SwingUtilities.invokeLater(() -> {
            ChangePassword monIHM = new ChangePassword(this, thelogin);
            monIHM.setVisible(true);
        });
    }

    // Crée l'interface graphique du jeu de quiz
    public void CreateQuizGameGUI() {
        SwingUtilities.invokeLater(() -> {
            QuizGameGUI monIHM = null;

            if (lesQuestions.isEmpty()) {
                initializeQuestions();
            }

            // Crée un objet QuizGame et son interface graphique
            this.theGame = new QuizGame(lePlayer, 0, lesQuestions);
            monIHM = new QuizGameGUI(this, theGame);
            monIHM.setVisible(true);
        });
    }

    // Crée l'écran de démarrage du jeu
    public void CreateGameStart() {
        SwingUtilities.invokeLater(() -> {
            GameStart monIHM = new GameStart(this);
            monIHM.setVisible(true);
        });
    }

    // Crée l'écran de récapitulation du jeu
    public void CreateRecapGame(int playerscore, Boolean hasWon) {
        SwingUtilities.invokeLater(() -> {
            RecapGame recapGame = new RecapGame(this, playerscore, hasWon);
            recapGame.setVisible(true);
        });
    }

    // Génère de nouvelles questions
    public void generateNewQuestions() {
        initializeQuestions();
    }

    public QuizGame getTheGame() {
        return theGame;
    }

    public void setTheGame(QuizGame theGame) {
        this.theGame = theGame;
    }

    public Player getLePlayer() {
        return lePlayer;
    }

    public void setLePlayer(Player lePlayer) {
        this.lePlayer = lePlayer;
    }

    public ArrayList<Question> getLesQuestions() {
        return lesQuestions;
    }

    public void setLesQuestions(ArrayList<Question> lesQuestions) {
        this.lesQuestions = lesQuestions;
    }

    public ArrayList<Answer> getLesReponses() {
        return lesReponses;
    }

    public void setLesReponses(ArrayList<Answer> lesReponses) {
        this.lesReponses = lesReponses;
    }

    public Login getMyLogin() {
        return myLogin;
    }

    public void setMyLogin(Login myLogin) {
        this.myLogin = myLogin;
    }

    public Configuration getMyConfiguration() {
        return myConfiguration;
    }
}
