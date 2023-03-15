package donnees.chess;


import java.io.Serializable;

/**
 * La classe représentant un joueur d'un jeu
 */
public class Player implements Serializable {

    public Player(String value) {
        this.name = value;
    }

    private int elo;
    private String name;

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
