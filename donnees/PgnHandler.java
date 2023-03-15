package donnees;

import donnees.chess.*;
import donnees.pgn.PgnIterator;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PgnHandler {
    public static void handle(String path) {

        try {
            File file = new File(path);
            if(!file.exists()) {
                throw new Exception("Le fichier "+file.getName()+" n'existe pas");
            }

            HashMapGenerator hashMapService = new HashMapGenerator();

            //Récupération du nombre maximal de thread disponible pour la création du pool de thread
            int processors = Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(processors);

            PgnIterator games = new PgnIterator(file.getAbsolutePath());

            for (Game game: games) {
                executor.execute(() -> hashMapService.computeGameHashmap(game));
            }

            executor.shutdown();

            //On attend que tous les threads se terminent pour continuer l'execution
            var correctlyTerminated = executor.awaitTermination(10, TimeUnit.MINUTES);

            if(correctlyTerminated) {
                System.out.println("Le traitement a bien terminé");

                //On enregistre les hashtables sur le disque.
                hashMapService.saveHashMaps();
            } else {
                System.out.println("Le traitement des données a dépassé le délais");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
