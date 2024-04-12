package view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.Timer;
import javax.print.attribute.standard.JobKOctets;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive;

import control.Controller;
import model.Game;
import model.Player;
import model.Score;

public class WaitStartGameGUI extends JFrame {

	private Controller leController;
	private Game theGame;

	private Client client;
	private Kryo kryo;

	private volatile boolean responseReceived = false;

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel Timeremaining;
	private JLabel timerLabel;
	private JList<String> playerList;
	private DefaultListModel<String> listModel;
	private Timer swingTimer;

	private int theTime;

	public WaitStartGameGUI(Controller theController) {
		this.leController = theController;
		theGame = leController.getTheGame();

		client = leController.getClient();
		kryo = leController.getKryo();

		createGUI();

		Listener listener = new Listener() {
			public void received(Connection connection, Object object) {
				if (!(object instanceof KeepAlive))
					System.out.println("Objet reçu de type : " + object.getClass().getName());

				if (object instanceof ArrayList<?>) {
					ArrayList<Player> lesPlayers = (ArrayList<Player>) object;
					theGame.setLesPlayers(lesPlayers);
					updatePlayers(lesPlayers);
				} else if (object instanceof String) {
					String msg = (String) object;
					System.out.println(msg);
					if(msg.equals("start")) {
						leController.getLeScore().setDate_game(Date.valueOf(LocalDate.now()).toString());
						leController.getLeScore().setTime_begin(LocalTime.now().toString());
						theController.CreateTheGame();
						dispose();
						responseReceived = true;
						client.removeListener(this);
					}
				} else if (object instanceof Integer) {
					theTime = (Integer) object;
					System.out.println(theTime);

					Timeremaining.setText(String.valueOf(theTime));
				}
			}
		};
		client.addListener(listener);
		client.sendTCP("ready");
		/**
		 * setResizable(false);
		 * setIconImage(Toolkit.getDefaultToolkit().getImage("img\\sb-logo-monogram-circle.jpg"));
		 * setTitle("Sprite bot"); setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * setBounds(100, 100, 301, 448); contentPane = new JPanel();
		 * contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		 * setContentPane(contentPane); contentPane.setLayout(null);
		 * 
		 * // Labels pour le login et le mot de passe JLabel lblLogin = new
		 * JLabel("Player name"); lblLogin.setBounds(66, 11, 69, 14);
		 * contentPane.add(lblLogin);
		 * 
		 * JLabel lblPwd = new JLabel("Rank"); lblPwd.setBounds(173, 11, 62, 14);
		 * contentPane.add(lblPwd);
		 * 
		 * // Bouton "Login" JButton btnLogin = new JButton("Login");
		 * btnLogin.setBounds(76, 375, 134, 23); contentPane.add(btnLogin);
		 * 
		 * lblNewLabel = new JLabel("Time remaining :"); lblNewLabel.setBounds(10, 354,
		 * 78, 14); contentPane.add(lblNewLabel);
		 **/

	}

	private void createGUI() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\sb-logo-monogram-circle.jpg"));
		setTitle("Sprite bot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 301, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Timer label
//		timerLabel = new JLabel();
//		timerLabel.setBounds(141, 354, 69, 14);
//		contentPane.add(timerLabel);

		Timeremaining = new JLabel();
		Timeremaining.setBounds(141, 354, 69, 14);
		contentPane.add(Timeremaining);

		// Player list
		listModel = new DefaultListModel<>();
		playerList = new JList<>(listModel);
		playerList.setBounds(66, 11, 69, 14);
		contentPane.add(playerList);

		JButton quitButton = new JButton("Quitter");
		quitButton.setBounds(76, 375, 134, 23);
		contentPane.add(quitButton);

		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ferme la fenêtre actuelle
				leController.CreateGameStart();
				dispose();
				// Affiche un message dans la console
				System.out.println("Game Closed!");
			}
		});

		setVisible(true);
	}

	public void updatePlayers(ArrayList<Player> players) {
		listModel.clear();
		for (Player player : players) {
			listModel.addElement(player.getPseudo() + " - RANK: " + player.getNomclassement());
		}
	}

	public void updateTimer(Time timeremaining) {
		timerLabel.setText("Timer " + timeremaining.toString());
	}
}
