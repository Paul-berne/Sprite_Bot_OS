package model;

import java.util.ArrayList;

public class Game {

    private int id_game;
    private String type_game;
    private ArrayList<Question> lesQuestions;
    private ArrayList<Player> lesPlayers;
    private String Statut;
    
	public Game() {
		
	}

	public ArrayList<Player> getLesPlayers() {
		return lesPlayers;
	}

	public String getStatut() {
		return Statut;
	}

	public int getId_game() {
		return id_game;
	}

	public String getType_game() {
		return type_game;
	}

	public ArrayList<Question> getLesQuestions() {
		return lesQuestions;
	}
    
	
    
}
