package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import control.Controller;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
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
    	this.myController = unController;
    	
        setResizable(false);
        setTitle("Sprite bot");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 464, 242); 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblLogin = new JLabel("Login : ");
        lblLogin.setBounds(30, 30, 60, 20); 
        contentPane.add(lblLogin);

        JLabel lblPwd = new JLabel("Password :");
        lblPwd.setBounds(30, 70, 80, 20); 
        contentPane.add(lblPwd);

        txtLogin = new JTextField();
        txtLogin.setBounds(120, 30, 150, 20); 
        contentPane.add(txtLogin);
        txtLogin.setColumns(10);

        txtPwd = new JPasswordField();
        txtPwd.setBounds(120, 70, 150, 20); 
        contentPane.add(txtPwd);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(70, 146, 100, 30); 
        contentPane.add(btnLogin);

        JButton btnChangePassword = new JButton("Change Password");
        btnChangePassword.setBounds(206, 146, 130, 30); 
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
    }
}
