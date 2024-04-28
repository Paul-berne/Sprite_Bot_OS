package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import control.Controller;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.JLabel;

public class DashBoard extends JFrame {

    // Contrôleur associé à la vue
    private Controller myController;
    
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

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
        setBounds(100, 100, 1108, 619);

        // Panneau de contenu
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Bouton "Start"
        JButton btnMonoplayer = new JButton("Monoplayer");
        btnMonoplayer.setBounds(250, 525, 109, 44);
        contentPane.add(btnMonoplayer);
        getRootPane().setDefaultButton(btnMonoplayer);
        
        JButton btnLeave = new JButton("Leave");
        btnLeave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnLeave.setBounds(572, 525, 188, 44);
        contentPane.add(btnLeave);
        
        JLabel lblNewLabel = new JLabel("Welcome " + unController.getLePlayer().getPseudo() + " !");
        lblNewLabel.setBounds(339, 11, 257, 20);
        contentPane.add(lblNewLabel);
        
        JLabel resultgame;
        
        
        JButton btnmultiplayer = new JButton("Multiplayer");
        btnmultiplayer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnmultiplayer.setBounds(409, 525, 109, 44);
        contentPane.add(btnmultiplayer);
        
        DefaultCategoryDataset dataset = createDataset();
        JFreeChart chart = ChartFactory.createLineChart(
                "Progression du joueur",
                "Date",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        NumberAxis rangeAxis = new NumberAxis();
        rangeAxis.setRange(0.0, 50.0);
        rangeAxis.setTickUnit(new NumberTickUnit(10));

        plot.setRangeAxis(rangeAxis);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setBounds(132, 110, 878, 389);
        contentPane.add(chartPanel);
        
        JLabel rank_player = new JLabel("Votre rank est " + unController.getLePlayer().getNomclassement());
        rank_player.setBounds(572, 14, 170, 14);
        contentPane.add(rank_player);
        
        JLabel rank_player_1 = new JLabel("Votre rank est <dynamic>");
        rank_player_1.setBounds(818, 14, 170, 14);
        contentPane.add(rank_player_1);
        
        
        try {
            if (unController.getTheGame().getType_game() != null && unController.getTheGame().getType_game().equals("monoplayer")  ) {

            	if (unController.getLeScore().getPlayer_score() >= 30) {
                	resultgame = new JLabel("Bravo vous avez gagné !! Avec un score de " + unController.getLeScore().getPlayer_score());
    			}else {
    				resultgame = new JLabel("Malheureusement vous avez perdu, votre score est de " + unController.getLeScore().getPlayer_score() + " retentez votre chance :D");
    			}
            	
            	resultgame.setBounds(23, 42, 539, 20);
                contentPane.add(resultgame);
                }
        } catch (Exception e) {
			// TODO: handle exception
		}
        
        
        
        

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
            	myController.CreateQuizGameGUIMulti();
                // Ferme la fenêtre actuelle
                dispose();

                // Affiche un message dans la console
                System.out.println("Game started!");
            }
        });
        
        btnLeave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	myController.DeletePlayerArray("delete");
                // Ferme la fenêtre actuelle
                dispose();
                // Affiche un message dans la console
                System.out.println("Game Closed!");
            }
        });
    }
    
    public DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	try {
            // Initialise le contrôleur et établit la connexion à la base de données
    		
            String dbname = this.myController.getMyConfiguration().readProperty("databaseprivate.url");
            String username = this.myController.getMyConfiguration().readProperty("database.username");
            String password = this.myController.getMyConfiguration().readProperty("database.password");

            try {
            	this.connection = DriverManager.getConnection(dbname, username, password);	
			} catch (Exception e) {
				dbname = this.myController.getMyConfiguration().readProperty("databasepublic.url");
                this.connection = DriverManager.getConnection(dbname, username, password);
			}
            this.statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT date_game, player_score, time_end FROM score WHERE player_score is not null and pseudo = '" + myController.getLePlayer().getPseudo() + "' order by date_game limit 20");
            while (resultSet.next()) {
            	Time time = resultSet.getTime("time_end");
                String date = resultSet.getString("date_game");
                int score = resultSet.getInt("player_score");
                String dateTime = date + " " + time.toString();
                dataset.addValue(score, "Score", dateTime);
            }
        } catch (SQLException e) {
            // Gère les exceptions liées à la connexion à la base de données
            e.printStackTrace();
        }
    	return dataset;
    	
    }
}
