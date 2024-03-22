package model;

public class Player {
    
    // Spécification
    private String login;
    private String password;
    private String player_rank;

    // Implémentation
    public Player(String login, String password, String nomclassement) {
        // Initialise un joueur avec les informations spécifiées
        super();
        this.login = login;
        this.password = password;
        this.player_rank = nomclassement;
    }

    // Getter pour le nom d'utilisateur (login)
    public String getLogin() {
        return login;
    }

    // Setter pour définir le nom d'utilisateur (login)
    public void setLogin(String login) {
        this.login = login;
    }

    // Getter pour le mot de passe
    public String getPassword() {
        return password;
    }

    // Setter pour définir le mot de passe
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter pour le nom du joueur
    public String getplayer_rank() {
        return player_rank;
    }

    // Setter pour définir le nom du joueur
    public void setplayer_rank(String nomclassement) {
        this.player_rank = nomclassement;
    }
}
