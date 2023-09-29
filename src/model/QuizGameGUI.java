package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import control.Controller;

public class QuizGameGUI extends JFrame {
    private QuizGame game;
    private int currentQuestionIndex = 0;
    private JLabel questionLabel;
    private JRadioButton[] answerRadioButtons;
    private JButton submitButton;

    public QuizGameGUI(QuizGame game) {
        this.game = game;

        setTitle("Quiz Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        questionLabel = new JLabel();
        answerRadioButtons = new JRadioButton[4];
        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel answerPanel = new JPanel(new GridLayout(4, 1));

        for (int i = 0; i < 4; i++) {
            answerRadioButtons[i] = new JRadioButton();
            buttonGroup.add(answerRadioButtons[i]);
            answerPanel.add(answerRadioButtons[i]);
        }

        submitButton = new JButton("Submit");

        setLayout(new BorderLayout());
        add(questionLabel, BorderLayout.NORTH);
        add(answerPanel, BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                nextQuestion();
            }
        });

        displayQuestion();

        setVisible(true);
    }

    private void displayQuestion() {
        Question currentQuestion = game.getQuestions().get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getDescriptionQuestion());

        ArrayList<Answer> answers = currentQuestion.getAnswers();
        for (int i = 0; i < 4; i++) {
            answerRadioButtons[i].setText(answers.get(i).getDescriptionAnswer());
        }
    }

    private void checkAnswer() {
        Question currentQuestion = game.getQuestions().get(currentQuestionIndex);
        int selectedAnswerIndex = -1;

        for (int i = 0; i < 4; i++) {
            if (answerRadioButtons[i].isSelected()) {
                selectedAnswerIndex = i;
                break;
            }
        }

        if (selectedAnswerIndex != -1 && currentQuestion.getAnswers().get(selectedAnswerIndex).getIsCorrect()) {
            game.addPlayerScore(10);
            JOptionPane.showMessageDialog(this, "Bravo ! Vous avez obtenu la bonne réponse.");
        } else {
            JOptionPane.showMessageDialog(this, "Dommage, la réponse était incorrecte.");
        }
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < game.getQuestions().size()) {
            displayQuestion();
        } else {
            endGame();
        }
    }

    private void endGame() {
        String feedbackMessage = "Votre score final est de " + game.getPlayerScore() + " points.";
        if (game.getPlayerScore() >= 30) {
            feedbackMessage += "\nFélicitations ! Vous avez gagné !";
        } else {
            feedbackMessage += "\nDommage, vous avez perdu.";
        }
        JOptionPane.showMessageDialog(this, feedbackMessage);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String playerName = JOptionPane.showInputDialog("Quel est votre nom ?");
                if (playerName != null && !playerName.isEmpty()) {
                    QuizGame player1game = Controller.getRandomQuizSetForQuizGame(playerName);
                    new QuizGameGUI(player1game);
                }
            }
        });
    }
}
