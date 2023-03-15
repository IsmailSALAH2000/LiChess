
package donnees.pgn;

import donnees.chess.Game;
import donnees.chess.GameResult;
import donnees.chess.Player;
import donnees.util.StringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import static donnees.pgn.PgnProperty.*;


/**
 * Cette classe permet de convertir la donnée lue dans le fichier d'entrée, en objet concret
 */
public class GameLoader {

    /**
     * méthode permettant de créer le jeu à partir des lignes qui sont lu dans le fichier d'entrée
     * @param iterator
     * @return
     */
    public static Game loadNextGame(Iterator<String> iterator) {

        Game game = null;
        Player whitePlayer = null;
        Player blackPlayer = null;
        StringBuilder moveText = null;
        boolean moveTextParsing = false;

        while (iterator.hasNext()) {// tant que nextLine (dans la classe "LargeFile") n'est pas null
            String line = iterator.next();
            try {
                line = line.trim();//Cette méthode renvoie une copie de la chaîne, les espaces blancs de début et de fin étant omis.
                
                //UTF8_BOM, l'indicateur d'ordre des octets ou BOM est une donnée qui indique l'utilisation d'un encodage unicode ainsi que l'ordre des octets, 
                //généralement situé au début de certains fichiers texte
                //ça présence peu bloquer des outils de java
                if (line.startsWith(UTF8_BOM)) {
                    //garder que la chaine sans UTF8_BOM
                    line = line.substring(1);
                }
                if (isProperty(line)) {
                    PgnProperty p = parsePgnProperty(line);
                    if (p != null) {
                        String tag = p.name.toLowerCase().trim();

                        switch (tag) {
                            case "event":
                                if (moveTextParsing && game != null) {
                                    setMoveText(game, moveText);
                                }
                                game = null;
                                whitePlayer = null;
                                blackPlayer = null;
                                moveText = new StringBuilder();

                                break;
                            case "white": {
                                if (game == null) {
                                    game = new Game(UUID.randomUUID().toString());
                                }

                                Player player = new Player(p.value);
                                player.setName(p.value);

                                game.setWhitePlayer(player);
                                whitePlayer = player;

                                break;
                            }
                            case "black": {
                                if (game == null) {
                                    game = new Game(UUID.randomUUID().toString());
                                }

                                Player player = new Player(p.value);
                                player.setName(p.value);

                                game.setBlackPlayer(player);
                                blackPlayer = player;

                                break;
                            }
                            case "result":
                                if (game != null) {
                                    GameResult r = GameResult.fromNotation(p.value);
                                    game.setResult(r);
                                }
                                break;
                            case "opening":
                                if (game != null) {
                                    game.setOpening(p.value);
                                }
                                break;
                            case "whiteelo":
                                if (whitePlayer != null) {
                                    try {
                                        whitePlayer.setElo(Integer.parseInt(p.value));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                                break;
                            case "blackelo":
                                if (blackPlayer != null) {
                                    try {
                                        blackPlayer.setElo(Integer.parseInt(p.value));
                                    } catch (NumberFormatException ignored) {

                                    }
                                }
                                break;
                            default:
                                if (game != null) {
                                    if (game.getProperty() == null) {
                                        game.setProperty(new HashMap<>());
                                    }
                                    game.getProperty().put(p.name, p.value);
                                }
                                break;
                        }
                    }
                }else if (!line.trim().equals("") && moveText != null) {
                    moveText.append(line);
                    moveText.append('\n');
                    moveTextParsing = true;
                    if (line.endsWith("1-0") ||
                            line.endsWith("0-1") ||
                            line.endsWith("1/2-1/2") ||
                            line.endsWith("*")) {
                        //end of PGN
                        if (game != null) {
                            setMoveText(game, moveText);
                        }
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return game;
    }

    private static void setMoveText(Game game, StringBuilder moveText) {
        StringUtil.replaceAll(moveText, "1-0", "");
        StringUtil.replaceAll(moveText, "0-1", "");
        StringUtil.replaceAll(moveText, "1/2-1/2", "");
        StringUtil.replaceAll(moveText, "*", "");
        game.setMoveText(moveText);
    }
}
