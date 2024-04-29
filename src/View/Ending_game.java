package view;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import control.Controller;
import model.Score;

public class Ending_game{
	
	private Controller unController;
	private Client client;
	private Kryo kryo;
	
	private DefaultTableModel model;
    private JButton leaveButton;

	
	public Ending_game(Controller theController) {
		System.out.println("dans ending");
		this.unController = theController;
		client = unController.getClient();
		kryo = unController.getKryo();
        // Créer le modèle de tableau
        this.model = new DefaultTableModel(new Object[]{"Classement", "Pseudo", "Score", "Temps"}, 0);

        // Créer le tableau
        JTable table = new JTable(model);

        // Ajouter le tableau à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        // Créer le cadre
        JFrame frame = new JFrame("Fin du jeu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane);
        
        leaveButton = new JButton("Leave");
        leaveButton.setVisible(false);
        leaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	unController.CreateGameStart();
                frame.dispose();  
            }
        });
        frame.getContentPane().add(leaveButton, BorderLayout.SOUTH);
        
        // Afficher le cadre
        frame.pack();
        frame.setVisible(true);
        
        System.out.println("entrée de la fonction return score");
    	Listener listener = new Listener() {
    	    public void received (Connection connection, Object object) {
			    System.out.println("Objet reçu de type : " + object.getClass().getName());
    	    	 if (object instanceof ArrayList<?>) {
    	    	        ArrayList<Score> scores = (ArrayList<Score>) object;
    	    	        unController.getTheGame().setLesScores(scores);
    	    	        UpdateScore(scores);
    	    	        
    	    	 }else if (object instanceof String) {
					String response = (String) object;
					System.out.println(response);
					if (response.equals("finish")) {
						leaveButton.setVisible(true);
						frame.pack();
    	    	        client.removeListener(this);  // Supprime le Listener
					}
				}
    	    }
    	};
    	System.out.println("score du joueur : " + unController.getLeScore());
    	client.addListener(listener);
    	client.sendTCP(unController.getLeScore());
    	
    }
	
	public void UpdateScore(ArrayList<Score> lesScores) {
	    DefaultTableModel model = this.model;

	    model.setRowCount(0);
	    int position = 0;
	    for (Score score : lesScores) {
	    	LocalTime timeBegin = LocalTime.parse(score.getTime_begin());
	        LocalTime timeEnd = LocalTime.parse(score.getTime_end());
	        
	        long diffInSeconds = ChronoUnit.SECONDS.between(timeBegin, timeEnd);

	        model.addRow(new Object[]{position+1, score.getPseaudo(), score.getPlayer_score(), diffInSeconds});
	        position++;
		}

	}
	
}
