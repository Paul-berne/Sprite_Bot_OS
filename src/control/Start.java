package control;

import model.QuizGameGUI;
import model.QuizGame;

import javax.swing.*;

public class Start {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String playerName = JOptionPane.showInputDialog("Quel est votre nom ?");
            if (playerName != null && !playerName.isEmpty()) {
                QuizGame player1game = Controller.getRandomQuizSetForQuizGame(playerName);
                new QuizGameGUI(player1game);
            }
        });
    }
}
