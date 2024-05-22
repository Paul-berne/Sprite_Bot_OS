package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.Controller;

public class RecapGame extends JFrame {

    // Contrôleur associé à la vue
    private Controller controller;

    // Score final et indicateur de victoire
    private int finalScore;
    private boolean hasWon;

    // Constructeur
    public RecapGame(Controller controller, int finalScore, boolean hasWon) {
        // Initialise la vue avec le contrôleur spécifié et les informations du jeu
        this.controller = controller;
        this.finalScore = finalScore;
        this.hasWon = hasWon;

        // Paramètres de la fenêtre
        setTitle("Récapitulatif du jeu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau principal avec un layout en boîte verticale
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Label pour afficher le score final
        JLabel scoreLabel = new JLabel("Votre score final est de " + finalScore + " points.");
        panel.add(scoreLabel);

        // Label pour afficher le résultat du jeu (gagné ou perdu)
        JLabel resultLabel = new JLabel(hasWon ? "Félicitations ! Vous avez gagné !" : "Dommage, vous avez perdu.");
        panel.add(resultLabel);

        // Bouton "Rejouer"
        JButton replayButton = new JButton("Rejouer");
        replayButton.setAlignmentY(Component.TOP_ALIGNMENT);
        replayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Génère de nouvelles questions, ferme la fenêtre actuelle et lance une nouvelle partie
                controller.CreateQuizGameGUIMono();
            	dispose();
                
            }
        });
        panel.add(replayButton);

        // Bouton "Quitter"
        JButton quitButton = new JButton("Quitter");
        quitButton.setAlignmentX(0.5f);
        panel.add(quitButton);

        // Ajout d'un écouteur d'événements pour le bouton "Quitter"
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ferme l'application
                System.exit(0);
            }
        });

        // Ajout du panneau au contenu de la fenêtre
        getContentPane().add(panel);

        // Rend la fenêtre visible
        setVisible(true);
    }
}
