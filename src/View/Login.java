package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import control.Controller;

public class Login extends JFrame {

    // Contrôleur associé à la vue
    private Controller myController;

    // Panneau de contenu
    private JPanel contentPane;

    // Champs de texte pour le login et le mot de passe
    private JTextField txtLogin;
    private JPasswordField txtPwd;

    // Boutons
    private JButton btnLogin;
    private JButton btnChangePassword;
    private JButton btnShowPassword;

    // Constructeur
    public Login(Controller unController) {
        // Initialise la vue avec le contrôleur spécifié
        this.myController = unController;

        // Paramètres de la fenêtre
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\sb-logo-monogram-circle.jpg"));
        setTitle("Sprite bot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 350, 155);
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
        txtLogin.setBounds(87, 11, 200, 20);
        contentPane.add(txtLogin);
        txtLogin.setColumns(10);

        txtPwd = new JPasswordField();
        txtPwd.setBounds(87, 36, 150, 20);
        contentPane.add(txtPwd);

        // Bouton "Show Password"
        btnShowPassword = new JButton();
        ImageIcon closedEyeIcon = new ImageIcon("img\\eye-off.png");
        ImageIcon resizedClosedEyeIcon = new ImageIcon(closedEyeIcon.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH));
        btnShowPassword.setIcon(resizedClosedEyeIcon);
        btnShowPassword.setBounds(247, 36, 20, 20);
        contentPane.add(btnShowPassword);

        // Bouton "Login"
        btnLogin = new JButton("Login");
        btnLogin.setBounds(23, 82, 89, 23);
        contentPane.add(btnLogin);
        getRootPane().setDefaultButton(btnLogin);

        // Bouton "Change Password"
        btnChangePassword = new JButton("Change Password");
        btnChangePassword.setBounds(134, 82, 157, 23);
        contentPane.add(btnChangePassword);
        btnChangePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomUtilisateur = txtLogin.getText();
                String motDePasse = txtPwd.getText();
                myController.getLePlayer().setPseudo(nomUtilisateur);
                myController.getLePlayer().setPassword(motDePasse);
                myController.getLePlayer().setNomclassement("");
                
                myController.AskChangePassword();
                if (myController.verifyUserLogin()) {
                    myController.CreateFrameChangePassword(nomUtilisateur);
                    dispose();
                } else {
                    if (myController.getLePlayer().getNomclassement().equals("already connect")) {
                        JOptionPane.showMessageDialog(null, "Ce joueur est déjà connecté.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect.");
                    }
                }
            }
        });


        
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomUtilisateur = txtLogin.getText();
                String motDePasse = txtPwd.getText();
                myController.getLePlayer().setPseudo(nomUtilisateur);
                myController.getLePlayer().setPassword(motDePasse);
                myController.getLePlayer().setNomclassement("");

                if (myController.verifyUserLogin()) {
                    myController.CreateGameStart();
                    dispose();
                } else {
                    if (myController.getLePlayer().getNomclassement().equals("already connect")) {
                        JOptionPane.showMessageDialog(null, "Ce joueur est déjà connecté.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Nom d'utilisateur ou mot de passe incorrect.");
                    }
                }
            }
        });
        
        btnShowPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (txtPwd.getEchoChar() == '\u2022') {
                    txtPwd.setEchoChar((char) 0);
                    ImageIcon eyeOpenIcon = new ImageIcon("img\\eye.png");
                    ImageIcon resizedEyeOpenIcon = new ImageIcon(eyeOpenIcon.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH));
                    btnShowPassword.setIcon(resizedEyeOpenIcon);
                } else {
                    txtPwd.setEchoChar('\u2022');
                    ImageIcon eyeClosedIcon = new ImageIcon("img\\eye-off.png");
                    ImageIcon resizedEyeClosedIcon = new ImageIcon(eyeClosedIcon.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH));
                    btnShowPassword.setIcon(resizedEyeClosedIcon);
                }
            }
        });
    }
}
