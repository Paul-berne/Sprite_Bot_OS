package View;

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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
	
	private JTable playerTable;
	private DefaultTableModel tableModel;
	
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
		System.out.println("entrée dans la class wait gui");
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
					Timeremaining.setText(String.valueOf(theTime));
				}
			}
		};
		client.addListener(listener);
		client.sendTCP("ready");


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
				leController.DeletePlayerArray("delete from game");
				leController.CreateGameStart();
				dispose();
				// Affiche un message dans la console
				System.out.println("Game Closed!");
			}
		});

		tableModel = new DefaultTableModel();
	    tableModel.addColumn("Pseudo");
	    tableModel.addColumn("Rank");
	    playerTable = new JTable(tableModel);
	    playerTable.setBounds(66, 11, 200, 300); // Ajustez la taille et la position selon vos besoins
	    contentPane.add(playerTable);
	    updatePlayers(theGame.getLesPlayers());
	    
		setVisible(true);
	}

	public void updatePlayers(ArrayList<Player> players) {
	    tableModel.setRowCount(0); // Clear the table
	    for (Player player : players) {
	        tableModel.addRow(new Object[]{player.getPseudo(), "RANK: " + player.getNomclassement()});
	    }
	}
}
