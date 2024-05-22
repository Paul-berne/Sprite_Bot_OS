package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;

import control.Controller;
import model.Answer;
import model.Question;
import model.Score;
import model.Game;

public class QuizGameGUI extends JFrame {

    // Le contrôleur pour la communication avec d'autres parties du programme
    private Controller theController;

    // Le jeu de quiz
    private Game game;
    
    // L'index de la question actuelle
    private int currentQuestionIndex = 0;

    // Les composants d'interface utilisateur
    private JLabel questionLabel;
    private JRadioButton[] answerRadioButtons;
    private JButton submitButton;

    // Constructeur de la classe
    public QuizGameGUI(Controller leController, Game game) {
        this.theController = leController;
        this.game = game;
        
        // Configuration de la fenêtre
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\sb-logo-monogram-circle.jpg"));
        setTitle("Quiz Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1422, 742);
        setLocationRelativeTo(null);

        // Initialisation des composants d'interface utilisateur
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        answerRadioButtons = new JRadioButton[4];
        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel answerPanel = new JPanel(new GridLayout(4, 1));

        // Initialisation des boutons radio pour les réponses
        for (int i = 0; i < 4; i++) {
            answerRadioButtons[i] = new JRadioButton();
            answerRadioButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            buttonGroup.add(answerRadioButtons[i]);
            answerPanel.add(answerRadioButtons[i]);
        }

        // Initialisation du bouton de soumission
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        getRootPane().setDefaultButton(submitButton);
        
        // Configuration du panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Ajout des composants au panneau principal
        mainPanel.add(questionLabel, BorderLayout.NORTH);
        mainPanel.add(answerPanel, BorderLayout.CENTER);
        mainPanel.add(submitButton, BorderLayout.SOUTH);

        // Ajout du panneau principal à la fenêtre
        getContentPane().add(mainPanel);

        // Ajout d'un écouteur d'événements pour le bouton "Submit"
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(); // Vérifie la réponse
                nextQuestion(); // Passe à la question suivante
            }
        });

        // Affichage de la première question
        displayQuestion();

        // Rend la fenêtre visible
        setVisible(true);
    }

    // Affiche la question actuelle et les réponses possibles
    private void displayQuestion() {
        Question currentQuestion = game.getLesQuestions().get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.getDescriptionQuestion());

        ArrayList<Answer> answers = currentQuestion.getAnswers();
        for (int i = 0; i < 4; i++) {
            answerRadioButtons[i].setText(answers.get(i).getDescriptionAnswer());
            answerRadioButtons[i].setSelected(false);
        }
    }

    // Vérifie la réponse et met à jour le score du joueur
    private void checkAnswer() {
        Question currentQuestion = game.getLesQuestions().get(currentQuestionIndex);
        int selectedAnswerIndex = -1;

        for (int i = 0; i < 4; i++) {
            if (answerRadioButtons[i].isSelected()) {
                selectedAnswerIndex = i;
                break;
            }
        }

        if (selectedAnswerIndex != -1 && currentQuestion.getAnswers().get(selectedAnswerIndex).getIsCorrect()) {
            theController.getLeScore().addPlayerScore(10);
            //JOptionPane.showMessageDialog(this, "Bravo ! Vous avez obtenu la bonne réponse.", "Réponse correcte", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //JOptionPane.showMessageDialog(this, "Dommage, la réponse était incorrecte.", "Réponse incorrecte", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Passe à la question suivante ou termine le jeu
    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < game.getLesQuestions().size()) {
            displayQuestion();
        } else {
        	theController.getLeScore().setTime_end(LocalTime.now().toString());
            endGame();
        }
    }

    // Affiche un message de fin de jeu avec le score final du joueur
    private void endGame() {
    	JOptionPane.showMessageDialog(this, "voulez-vous finir ?", "oui",JOptionPane.INFORMATION_MESSAGE);
    	
    	if (game.getType_game().toString() == "multiplayer") {
			theController.CreateEnding();
		}else {
	    	theController.ReturnScore();
		}
        dispose();
    }
}
