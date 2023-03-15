package donnees.chess;


import java.io.Serializable;

/**
 * Une version moins lourde du jeu pour enregistrement dans un fichier.
 * Elle ne contient pas les moves du jeu
 */
public class LightGame implements Serializable {
    private String gameId;
    private String whitePlayer;
    private String blackPlayer;
    private GameResult result;
    private String opening;

    public static LightGame fromGame(Game game) {
        var lightGame = new LightGame();
        lightGame.setGameId(game.getGameId());
        lightGame.setBlackPlayer(game.getBlackPlayer().getName());
        lightGame.setWhitePlayer(game.getWhitePlayer().getName());
        lightGame.setResult(game.getResult());
        lightGame.setOpening(game.getOpening());
        return lightGame;
    }
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(String whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }
}
