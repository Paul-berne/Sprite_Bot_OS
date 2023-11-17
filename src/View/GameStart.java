package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import control.Controller;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStart extends JFrame {

    // Contrôleur associé à la vue
    private Controller myController;

    // Panneau de contenu
    private JPanel contentPane;

    // Constructeur
    public GameStart(Controller unController) {
        // Initialise la vue avec le contrôleur spécifié
        this.myController = unController;

        // Paramètres de la fenêtre
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\sb-logo-monogram-circle.jpg"));
        setTitle("Sprite bot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 300, 200);

        // Panneau de contenu
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Bouton "Start"
        JButton btnStart = new JButton("Start");
        btnStart.setBounds(160, 82, 89, 23);
        contentPane.add(btnStart);

        // Ajout d'un écouteur d'événements pour le bouton "Start"
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Appelle la méthode du contrôleur pour créer l'IHM du quiz
                myController.CreateQuizGameGUI();

                // Ferme la fenêtre actuelle
                dispose();

                // Affiche un message dans la console
                System.out.println("Game started!");
            }
        });
    }
}
