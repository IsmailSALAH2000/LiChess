package donnees;
import donnees.chess.*; //importer toutes les classe du package donnees.chess

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import java.util.ArrayList;



/**
 * Cette classe permet de gerer tous les hashtables
 */
public class HashMapGenerator {
    //variable lock servant à l'accès unique du block synchronise. on ne peux pas appliquer synchronized sur une variable non final. dont l'existance de cette variable
    private static final Object lock = new Object();

    //Hashtable représentant la liste des jeux. id du jeu ---> jeu (Game)
    private final ConcurrentHashMap<String, LightGame> gameTable = new ConcurrentHashMap<>();

    //Hashtable représentant la liste des joueurs : nom du joueur ----> joueur (Player)
    private final ConcurrentHashMap<String, Player> playerTable = new ConcurrentHashMap<>();

    //Pour chaque joueur, la liste de tous leurs jeux
    private final ConcurrentHashMap<String, List<String>> playerToGamesTable = new ConcurrentHashMap<>();

    //Pour chaque nom d'ouverture, la liste des joueurs qui l'on executé
    private final ConcurrentHashMap<String, List<String>> openNameToPlayers = new ConcurrentHashMap<>();

    private static Integer counter = 0;

    public void computeGameHashmap(Game game) {
        this.gameTable.put(game.getGameId(), LightGame.fromGame(game));
        this.playerTable.put(game.getBlackPlayer().getName(), game.getBlackPlayer());
        this.playerTable.put(game.getWhitePlayer().getName(), game.getWhitePlayer());
        mergeMapList(this.playerToGamesTable, game.getWhitePlayer().getName(), game.getGameId());
        mergeMapList(this.playerToGamesTable, game.getBlackPlayer().getName(), game.getGameId());
        mergeMapList(this.openNameToPlayers, game.getOpening(), game.getWhitePlayer().getName());
        
        synchronized (lock) {
            counter++;
            System.out.printf("Jeu n°%d : %s vs %s%n", counter, game.getWhitePlayer().getName(), game.getBlackPlayer().getName());
        }
    }

    public void mergeMapList( ConcurrentHashMap<String, List<String>> mapList, String key, String value) {
        if(!mapList.containsKey(key)) {
            List<String> list = new ArrayList<>();
            list.add(value);
            mapList.put(key, list);
        } else {
            mapList.get(key).add(value);
        }
    }

    public void saveHashMaps() {
        System.out.print("Enregistrement des jeux... ");
        FileHandler.saveDataToFile(gameTable, "games.data");
        System.out.println("OK");
        System.out.print("Enregistrement des joueurs... ");
        FileHandler.saveDataToFile(playerTable, "players.data");
        System.out.println("OK");
        System.out.print("Enregistrement des joueurs associés aux jeux... ");
        FileHandler.saveDataToFile(playerToGamesTable, "playersToGame.data");
        System.out.println("OK");
        System.out.print("Enregistrement des ouvertures des joueurs... ");
        FileHandler.saveDataToFile(openNameToPlayers, "openNameToPlayers.data");
        System.out.println("OK");
    }
}
