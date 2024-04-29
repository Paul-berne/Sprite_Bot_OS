package control;

import java.io.IOException;
import java.nio.file.Watchable;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import model.*;
import view.*;

public class Controller {

    // Spécification
	private Configuration myConfiguration;
	
	//model
    private Game theGame;
    private Player lePlayer;
    private ArrayList<Question> lesQuestions;
    private ArrayList<Answer> lesReponses;
    private ArrayList<Score> lesScores;
    private Login myLogin;
    private Score leScore;
    
    //websocket
	private Kryo kryo;
	private Client client;
	private volatile boolean responseReceived = false;
	private volatile boolean responseLogin = false;


    // Implémentation
    public Controller() throws ParseException, SQLException {
    	
    	this.myConfiguration = new Configuration();
    	
        // Initialise la configuration, la fenêtre de connexion et charge les questions
		client = new Client(5000, 5000);
		kryo = client.getKryo();
		kryo.register(SampleRequest.class);
		kryo.register(String.class);
		kryo.register(Player.class);
		kryo.register(Game.class);
		kryo.register(ArrayList.class);
		kryo.register(Question.class);
		kryo.register(Answer.class);
		kryo.register(Integer.class);
		kryo.register(Score.class);
		
		client.start();
		String address = "127.0.0.1";
		
		System.out.println("Connecting to server...");
		try {
			client.connect(5000, address, 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		this.lePlayer = new Player();
        this.myLogin = new Login(this);
        this.myLogin.setLocation(100, 100);
        myLogin.setVisible(true);
    }
    
    public void DeletePlayerArray(String msg) {
    	lePlayer.setAction(msg);
    	client.sendTCP(lePlayer);
    }

    //side client
    // Vérifie les informations de connexion de l'utilisateur
    public boolean verifyUserLogin() {
    	lePlayer.setAction("login");
    	System.out.println("code listener");
    	Listener listener = new Listener() {
    	    public void received (Connection connection, Object object) {
    	        if (object instanceof Player) {
    	        	if (((Player) object).getNomclassement() != "") {
    	        		System.out.println("Received response from server: " + ((Player) object).getNomclassement());
        	            lePlayer.setNomclassement(((Player) object).getNomclassement());
        	            responseLogin = true;
        	            client.removeListener(this);  // Supprime le Listener
					}else {
						responseLogin = false;
					}
    	        	responseReceived = true;
    	        }
    	    }
    	};
    	client.addListener(listener);
    	client.sendTCP(lePlayer);
        while(!responseReceived) {
            try {
                Thread.sleep(100); // Attendre 100 millisecondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseReceived = false;
        return responseLogin;
    }
    
    public void AskChangePassword() {
    	lePlayer.setAction("changepassword");
    	System.out.println(lePlayer.getPassword());
    	System.out.println("code listener");
    	Listener listener = new Listener() {
    	    public void received (Connection connection, Object object) {
    	        if (object instanceof Boolean) {
    	            client.removeListener(this);  // Supprime le Listener
    	        	responseReceived = true;
    	        }
    	    }
    	};
    	client.addListener(listener);
    	client.sendTCP(lePlayer);

        while(!responseReceived) {
            try {
                Thread.sleep(100); // Attendre 100 millisecondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        responseReceived = false;
        
        if (responseReceived) {
			CreateGameStart();
		}
    }
    
 // Crée une fenêtre de changement de mot de passe
    public void CreateFrameChangePassword(String thelogin) {
        SwingUtilities.invokeLater(() -> {
            ChangePassword monIHM = new ChangePassword(this, thelogin);
            monIHM.setVisible(true);
        });
    }
    
    // Crée l'écran de démarrage du jeu
    public void CreateGameStart() {
        SwingUtilities.invokeLater(() -> {
            DashBoard monIHM = new DashBoard(this);
            monIHM.setVisible(true);
        });
    }
    
    public void CreateTheGame() {
    	System.out.println("createthegame : " + theGame.getType_game().toString());
    	SwingUtilities.invokeLater(() -> {
            QuizGameGUI monIHM = new QuizGameGUI(this, theGame);
            monIHM.setVisible(true);
        });
    }
    
    public void CreateEnding() {
    	System.out.println("createending");
    	Ending_game ending_game = new Ending_game(this);
    	
    }

    // Crée l'interface graphique du jeu de quiz
    public void CreateQuizGameGUIMono() {
    	Listener listener = new Listener() {
    	    public void received (Connection connection, Object object) {
    	        if (object instanceof Game) {
    	        	theGame = (Game) object;
    	        	leScore = new Score(theGame.getId_game(), lePlayer.getPseudo(), Date.valueOf(LocalDate.now()).toString(), 0, LocalTime.now().toString(), null);
    	        	System.out.println("création de quizgamegui" + leScore.getDate_game());
    	        	
    	            client.removeListener(this);  // Supprime le Listener
    	        	responseReceived = true;
    	        }
    	    }
    	};
    	client.addListener(listener);
    	client.sendTCP("monoplayer");
    	
        while(!responseReceived) {
            try {
                Thread.sleep(100); // Attendre 100 millisecondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (responseReceived) {
    		CreateTheGame();
		}
        responseReceived = false;
    }
    
    public void CreateQuizGameGUIMulti() {
    	Listener listener = new Listener() {
    	    public void received (Connection connection, Object object) {
    	        if (object instanceof Game) {
    	        	theGame = (Game) object;
    	        	theGame.setType_game("multiplayer");
    	        	leScore = new Score(theGame.getId_game(), lePlayer.getPseudo(), null, 0, null , null);
    	            client.removeListener(this);  // Supprime le Listener
    	            
    	        	responseReceived = true;
    	        }
    	    }
    	};
    	client.addListener(listener);
    	client.sendTCP("multiplayer");
    	
    	while(!responseReceived) {
            try {
                Thread.sleep(100); // Attendre 100 millisecondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    	if (responseReceived) {
			WaitStartGameGUI laFileDattente = new WaitStartGameGUI(this);
		}
    	responseReceived = false;
    }
    
    public void ReturnScore() {
    	System.out.println("entrée de la fonction return score");
    	Listener listener = new Listener() {
    	    public void received (Connection connection, Object object) {
			    System.out.println("Objet reçu de type : " + object.getClass().getName());
    	    	 if (object instanceof ArrayList<?>) {
    	    	        ArrayList<Score> scores = (ArrayList<Score>) object;
    	    	        theGame.setLesScores(scores);
    	    	        for (Score score : scores) {
							System.out.println(score.getPlayer_score());
						}
    	    	        CreateGameStart();
    	    	        responseReceived = true;
    	    	        client.removeListener(this);  // Supprime le Listener
    	    	 }
    	    }
    	};
    	client.addListener(listener);
    	System.out.println("Score du joueur : "+ leScore.getPlayer_score());
    	client.sendTCP(leScore);
    	
    	while(!responseReceived) {
            try {
                Thread.sleep(100); // Attendre 100 millisecondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    	System.out.println("nom du joueur : "+ leScore.getPseaudo());
    }
    
    public Game getTheGame() {
        return theGame;
    }

    public void setTheGame(Game theGame) {
        this.theGame = theGame;
    }

    public Player getLePlayer() {
        return lePlayer;
    }

    public void setLePlayer(Player lePlayer) {
        this.lePlayer = lePlayer;
    }

    public ArrayList<Question> getLesQuestions() {
        return lesQuestions;
    }

    public void setLesQuestions(ArrayList<Question> lesQuestions) {
        this.lesQuestions = lesQuestions;
    }

    public ArrayList<Answer> getLesReponses() {
        return lesReponses;
    }

    public void setLesReponses(ArrayList<Answer> lesReponses) {
        this.lesReponses = lesReponses;
    }

    public Login getMyLogin() {
        return myLogin;
    }

    public void setMyLogin(Login myLogin) {
        this.myLogin = myLogin;
    }

    public static class SampleRequest {
	    public String text;
	}

	public static class SampleResponse {
	    public String text;
	}

	public Score getLeScore() {
		return leScore;
	}

	public ArrayList<Score> getLesScores() {
		return lesScores;
	}

	public Kryo getKryo() {
		return kryo;
	}

	public Client getClient() {
		return client;
	}

	public Configuration getMyConfiguration() {
		return myConfiguration;
	}
	
	
}
