package donnees.chess;


import java.util.HashMap;
import java.util.Map;

/**
 * une enumération qui représente les différents résultats d'un jeu
 */
public enum GameResult {

    WHITE_WON("1-0"),
    BLACK_WON("0-1"),
    DRAW("1/2-1/2"),
    ONGOING("*");

    static final Map<String, GameResult> notation = new HashMap<>(4);
    static {
        notation.put("1-0", WHITE_WON);
        notation.put("0-1", BLACK_WON);
        notation.put("1/2-1/2", DRAW);
        notation.put("*", ONGOING);
    }

    final String description;

    GameResult(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static GameResult fromNotation(String s) {
        return notation.get(s);
    }

}
