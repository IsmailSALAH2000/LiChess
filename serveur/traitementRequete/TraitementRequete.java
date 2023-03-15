/*
 * Cette classe permet de récupérer les différente base de données et 
 * de traiter les requetes du client
 */
package serveur.traitementRequete;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import donnees.chess.*;
import donnees.FileHandler;

public class TraitementRequete {
    ConcurrentHashMap<String, List<String>> openNameToPlayers; //ouverture -> liste des joueurs l'ayant utilisées
    ConcurrentHashMap<String, List<String>> playerToGameTable; //joueur -> liste des id des jeux qu'il a joué
    ConcurrentHashMap<String, LightGame>    gameTable; // id du jeu -> version allégé du jeu
    ConcurrentHashMap<String, Player>       playerTable; //pseudo du joueur -> joueur

    public TraitementRequete(){
        this.openNameToPlayers = (ConcurrentHashMap<String, List<String>>) FileHandler.loadFile("/LiChessV2/openNameToPlayers.data");
        this.playerToGameTable = (ConcurrentHashMap<String, List<String>>) FileHandler.loadFile("/LiChessV2/playersToGame.data");
        this.gameTable = (ConcurrentHashMap<String, LightGame>) FileHandler.loadFile("/LiChessV2/games.data");
        this.playerTable = (ConcurrentHashMap<String, Player>) FileHandler.loadFile("/LiChessV2/players.data");
    }
    
    /*
     *Méthode permettant de traiter la requête client
     *les calculs sont délégués à d'autres classes
     *si ils sont plus compliqués
     */ 
    public Object askDataBase(String requete){
        StringTokenizer st = new StringTokenizer(requete," "); //on "découpe" la requete à chaque espace
        String commande;
        commande = st.nextToken(); //on récupère la commande voulue
        switch(commande){
            case "AllGames" : {commande = st.nextToken(); 
                               return playerToGameTable.get(commande);
                               
            }
            case "GetEloPlayer": { commande = st.nextToken();
                                   Player  p = playerTable.get(commande);
                                   if(p!=(null))
                                     return p.getElo();
                                   else 
                                     return "Nous n'avons pas trouver le joueur";

            }
            case "GetGameById"  : { commande = st.nextToken();
                                    LightGame lg = gameTable.get(commande);
                                    if(lg != null)
                                        return "Black player: "+lg.getBlackPlayer() + "\n White player: " 
                                        + lg.getWhitePlayer() + "\n Ouverture: " + lg.getOpening() + "\n Résultat: " 
                                        + lg.getResult();
                                    else
                                        return "Nous n'avons pas trouver la partie";
            }
            case "MostPlayedOpens" : {RankMap rank = new RankMap(openNameToPlayers); 
                                      return rank.rank();
            }
            case "MostPlayedPlayers" : {RankMap rank = new RankMap(playerToGameTable);
                                        return rank.rank();
            }
            case "GetGamesByPlayers" : {String player1 = st.nextToken();
                                        String player2 = st.nextToken();
                                        GetPlayersGames fg = new GetPlayersGames(player1, player2, gameTable);
                                        return fg.findGames();

            }
           case "GetPlayerOpponents" : {String player = st.nextToken();
                                        List<String> ls = playerToGameTable.get(player);
                                        if(!ls.isEmpty()){
                                            SearchOpponent so = new SearchOpponent(gameTable, player, ls);
                                            return so.search();
                                            }
                                            else
                                                return "Nous n'avons pas trouver la partie";
            }
            default : return "Nous avons rencontrer un probleme, êtes-vous sur d'avoir entré la bonne commande ?";
        }
    }

    /*Méthode permettant d'afficher le panel de commande*/
    public String getCommands(){
        return "\nPour obtenir toutes les parties(id) d'un joueur: AllGames nomJoueur\n" +
               "Pour obtenir l'elo d'un joueur: GetEloPlayer nomJoueur\n"+
               "Pour obtenir une partie à partir d'un id: GetGamesById partieId\n"+
               "Pour obtenir les 5 joueurs ayant realises le plus de partie: MostPlayedPlayer\n"+
               "Pour obtenir les 5 ouverture les plus jouees: MostPlayedOpens\n"+
               "Pour obtenir les parties entre deux joueurs: GetGamesByPlayers joueur1 joueur2\n"+
               "Pour obtenir les adversaires de toutes les parties d'un joueur: GetPlayerOpponents joueur\n"+
               "Pour signaler que vous avez fini vos recherches: END\n";
    }
}
