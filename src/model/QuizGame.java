package model;

import java.util.ArrayList;

public class QuizGame {

    // Spécification
    private Player lePlayer;
    private int playerScore;
    private ArrayList<Question> questions;

    // Implémentation
    public QuizGame(Player lePlayer, int playerScore, ArrayList<Question> questions) {
        // Initialise un jeu de quiz avec les informations spécifiées
        super();
        this.lePlayer = lePlayer;
        this.playerScore = playerScore;
        this.questions = questions;
    }

    // Getter pour le joueur associé au jeu
    public Player getLePlayer() {
        return lePlayer;
    }

    // Setter pour définir le joueur associé au jeu
    public void setLePlayer(Player lePlayer) {
        this.lePlayer = lePlayer;
    }

    // Getter pour le score du joueur
    public int getPlayerScore() {
        return playerScore;
    }

    // Setter pour définir le score du joueur
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    // Getter pour la liste des questions du quiz
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    // Setter pour définir la liste des questions du quiz
    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    // Méthode pour ajouter le score du joueur
    public void addPlayerScore(int playerScore) {
        this.playerScore += playerScore;
    }

    // Méthode pour vérifier si une réponse sélectionnée est valide
    public Boolean isValidAnswer(String selectedAnswer) {
        selectedAnswer = selectedAnswer.toLowerCase();
        return selectedAnswer.equals("a") || selectedAnswer.equals("b") || selectedAnswer.equals("c") || selectedAnswer.equals("d");
    }

    // Méthode pour vérifier si une réponse sélectionnée est correcte
    public Boolean isCorrectThisAnswer(String selectedCodeAnswer, ArrayList<Answer> answers) {
        answers.removeIf(answer -> !answer.getCodeAnswer().contains(selectedCodeAnswer));
        return !answers.isEmpty() && answers.get(0).getIsCorrect();
    }

    // Méthode pour obtenir la question actuelle (à implémenter)
    public Object getCurrentQuestion() {
        // TODO: Implémenter la logique pour obtenir la question actuelle
        return null;
    }
}
