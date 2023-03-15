package donnees.chess;

import java.io.Serializable;
import java.util.Map;

/**
 * Classe représentant une partie de jeu
 */
public class Game implements Serializable {
    private String gameId;
    private Player whitePlayer;
    private Player blackPlayer;
    private GameResult result;
    private StringBuilder moveText;
    private String opening;
    private Map<String, String> property;

    public Game(String gameId) {
        this.gameId = gameId;
        this.result = GameResult.ONGOING;
    }



    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }


    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public StringBuilder getMoveText() {
        return moveText;
    }

    public void setMoveText(StringBuilder moveText) {
        this.moveText = moveText;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public Map<String, String> getProperty() {
        return property;
    }

    public void setProperty(Map<String, String> property) {
        this.property = property;
    }
}
