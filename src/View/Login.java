package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import control.Controller;
import tools.BCrypt;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    // Contrôleur associé à la vue
    private Controller myController;

    // Panneau de contenu
    private JPanel contentPane;

    // Champs de texte pour le login et le mot de passe
    private JTextField txtLogin;
    private JTextField txtPwd;

    // Constructeur
    public Login(Controller unController) {
        // Initialise la vue avec le contrôleur spécifié
        this.myController = unController;

        // Paramètres de la fenêtre
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\sb-logo-monogram-circle.jpg"));
        setTitle("Sprite bot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 278, 155);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Labels pour le login et le mot de passe
        JLabel lblLogin = new JLabel("Login : ");
        lblLogin.setBounds(10, 11, 46, 14);
        contentPane.add(lblLogin);

        JLabel lblPwd = new JLabel("Password :");
        lblPwd.setBounds(10, 36, 62, 14);
        contentPane.add(lblPwd);

        // Champs de texte pour le login et le mot de passe
        txtLogin = new JTextField();
        txtLogin.setBounds(87, 11, 86, 20);
        contentPane.add(txtLogin);
        txtLogin.setColumns(10);

        txtPwd = new JPasswordField();
        txtPwd.setColumns(10);
        txtPwd.setBounds(87, 36, 86, 20);
        contentPane.add(txtPwd);

        // Bouton "Login"
        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(23, 82, 89, 23);
        contentPane.add(btnLogin);

        // Bouton "Change Password"
        JButton btnChangePassword = new JButton("Change Password");
        btnChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Récupère le nom d'utilisateur et le mot de passe
                String nomUtilisateur = txtLogin.getText();
                String motDePasse = txtPwd.getText();

                // Vérifie l'authentification
                if (unController.verifyUserLogin(nomUtilisateur, motDePasse)) {
                    // Affiche la fenêtre de changement de mot de passe
                    myController.CreateFrameChangePassword(nomUtilisateur);
                    dispose(); // Ferme la fenêtre actuelle
                } else {
                    // Affiche un message d'erreur
                    JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect.");
                }
            }
        });
        btnChangePassword.setBounds(134, 82, 118, 23);
        contentPane.add(btnChangePassword);

        // Ajout d'un écouteur d'événements pour le bouton "Login"
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Récupère le nom d'utilisateur et le mot de passe
                String nomUtilisateur = txtLogin.getText();
                String motDePasse = txtPwd.getText();
                // Vérifie l'authentification
                if (unController.verifyUserLogin(nomUtilisateur, motDePasse)) {
                    // Affiche la fenêtre de démarrage du jeu
                    myController.CreateGameStart();
                    dispose(); // Ferme la fenêtre actuelle
                } else {
                    // Affiche un message d'erreur
                    JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect.");
                }
            }
        });
    }
}
