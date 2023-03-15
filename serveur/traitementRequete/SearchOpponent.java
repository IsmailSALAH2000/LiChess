package serveur.traitementRequete;

/*
 *Cette classe permet de gérer la recherche des adversaires de toutes
 * les parties d'un joueur
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import donnees.chess.*;

public class SearchOpponent{
    private ConcurrentHashMap<String, LightGame> gameTable; //on récupère la Hashmap qui associe id -> partie allegée
    private ArrayList result; //Liste contenant les résultats de la recherche
    private List<String> games; //liste des parties joué par le joueur
    private String player; //joueur

    public SearchOpponent(ConcurrentHashMap<String, LightGame> gameTable, String player, List<String> games){
        this.result = new ArrayList<>();
        this.player = player;
        this.gameTable = gameTable;
        this.games = games;
    }

    /*Fonction permettant de remplir la liste de résultat*/ 
    public void findOpponents(LightGame lg){
        if(lg.getBlackPlayer().equals(player)){
            result.add(lg.getWhitePlayer());
        }
        else{
            result.add(lg.getBlackPlayer());
        }
    }

    public ArrayList search(){
        try {
            //Récupération du nombre maximal de thread disponible pour la création du pool de thread
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(processors);

            for(String id : games){ //pour chaque partie du joueur on cherche son adversaire
                executor.execute(() -> findOpponents(gameTable.get(id)));
            }
            executor.shutdown();

            //On attend que tous les threads se terminent pour continuer l'execution
            var correctlyTerminated = executor.awaitTermination(10, TimeUnit.MINUTES);
            if(correctlyTerminated){
                return result;
            }
            else{
                result.clear();
                result.add("Il y a un probleme");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}