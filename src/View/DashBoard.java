package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import control.Controller;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class DashBoard extends JFrame {

    // Contrôleur associé à la vue
    private Controller myController;

    // Panneau de contenu
    private JPanel contentPane;

    // Constructeur
    public DashBoard(Controller unController) {
        // Initialise la vue avec le contrôleur spécifié
        this.myController = unController;

        // Paramètres de la fenêtre
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\sb-logo-monogram-circle.jpg"));
        setTitle("Sprite bot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 703, 401);

        // Panneau de contenu
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Bouton "Start"
        JButton btnMonoplayer = new JButton("Monoplayer");
        btnMonoplayer.setBounds(93, 286, 109, 44);
        contentPane.add(btnMonoplayer);
        
        JButton btnLeave = new JButton("Leave");
        btnLeave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnLeave.setBounds(351, 286, 188, 44);
        contentPane.add(btnLeave);
        
        JLabel lblNewLabel = new JLabel("Welcome " + unController.getLePlayer().getPseudo() + " !");
        lblNewLabel.setBounds(223, 11, 257, 20);
        contentPane.add(lblNewLabel);
        
        JButton btnmultiplayer = new JButton("Multiplayer");
        btnmultiplayer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnmultiplayer.setBounds(223, 286, 109, 44);
        contentPane.add(btnmultiplayer);
        

        // Ajout d'un écouteur d'événements pour le bouton "Start"
        btnMonoplayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Appelle la méthode du contrôleur pour créer l'IHM du quiz
                myController.CreateQuizGameGUIMono();

                // Ferme la fenêtre actuelle
                dispose();

                // Affiche un message dans la console
                System.out.println("Game started!");
            }
        });
        
        btnmultiplayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Appelle la méthode du contrôleur pour créer l'IHM du quiz
                //myController.CreateQuizGameGUI();

                // Ferme la fenêtre actuelle
                dispose();

                // Affiche un message dans la console
                System.out.println("Game started!");
            }
        });
        
        btnLeave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ferme la fenêtre actuelle
                dispose();
                // Affiche un message dans la console
                System.out.println("Game Closed!");
            }
        });
    }
}
