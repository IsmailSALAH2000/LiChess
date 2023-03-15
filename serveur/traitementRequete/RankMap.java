package serveur.traitementRequete;
/*
 *Cette classe permet de trouver les 5 plus grandes listes (value)
 *associées aux clés de la hashmap
 */
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Map.Entry;

public class RankMap {
    private ConcurrentHashMap<String, Integer> keyToNbValue; //clé -> taille de la liste value
    private ArrayList result; //stock les résultats finaux
    private ConcurrentHashMap<String, List<String>> keyToValue; //hashmap à classer

    public RankMap(ConcurrentHashMap<String, List<String>> data){
        this.result = new ArrayList<>();
        this.keyToValue = data;
        this.keyToNbValue = new ConcurrentHashMap<>();
    }

    public void findSize(String key,Integer value){
        keyToNbValue.put(key, value);
    }

    public ArrayList rank() {
        try{
                
            //Récupération du nombre maximal de thread disponible pour la création du pool de thread
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(processors);
            for (String key: keyToValue.keySet()) {
                    executor.execute(() -> findSize(key, keyToValue.get(key).size()));
                }
                executor.shutdown();
            
            //On attend que tous les threads se terminent pour continuer l'execution
            var correctlyTerminated = executor.awaitTermination(10, TimeUnit.MINUTES);

            if(correctlyTerminated) {

                List<Entry<String, Integer>> list = new ArrayList<>(keyToNbValue.entrySet());
		        list.sort(Entry.comparingByValue());

                //recuperation des 5 derniers elements de la hashtable
                for(int i=keyToValue.size()-5;i<keyToValue.size();i++){
                    result.add(list.get(i));
                }
            } else {
                System.out.println("Le traitement des données a dépassé le délais");
            }
        }catch (Exception e) {
                e.printStackTrace();
                }

        return result;
        }


}
