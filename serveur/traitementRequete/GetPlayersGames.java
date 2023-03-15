package serveur.traitementRequete;
/*
 * Cette clase permet de gérer la recherche de toutes les
 * parties jouées entre deux joueurs
 */
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import donnees.chess.*;

public class GetPlayersGames {
    private ConcurrentHashMap<String, LightGame> gameTable; //id -> partie allégée
    private ArrayList result; //stock les résultat
    private String player1; //joueur1
    private String player2; //joueur2

    public GetPlayersGames(String player1, String player2, ConcurrentHashMap<String, LightGame> gameTable){
        this.result = new ArrayList<>();
        this.player1 = player1;
        this.player2 = player2;
        this.gameTable = gameTable;
    }

    public void findGame(LightGame lg){
        if( (lg.getBlackPlayer().equals(player1) && lg.getWhitePlayer().equals(player2) )
            || (lg.getBlackPlayer().equals(player2) && lg.getWhitePlayer().equals(player1) ) ){
            result.add(gameToString(lg));
        }
    }

    public String gameToString(LightGame lg){
        return "Black player: "+lg.getBlackPlayer() + "\n White player: " 
                + lg.getWhitePlayer() + "\n Opening: " + lg.getOpening() + "\n Result: " 
                + lg.getResult() + "\n\n";
    }

    public ArrayList findGames() {  
        try{        
            //Récupération du nombre maximal de thread disponible pour la création du pool de thread
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(processors);
            for (String key: gameTable.keySet()) {
                    executor.execute(() -> findGame(gameTable.get(key)));
            }
            executor.shutdown();

            //On attend que tous les threads se terminent pour continuer l'execution
            var correctlyTerminated = executor.awaitTermination(10, TimeUnit.MINUTES);
            if(!correctlyTerminated){
                result.clear();
                result.add("Il y a un probleme");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
