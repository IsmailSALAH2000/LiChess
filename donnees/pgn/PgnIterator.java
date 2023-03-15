
package donnees.pgn;

import donnees.chess.Game;
import donnees.util.LargeFile;

import java.util.Iterator;


/**
 * Un iterateur permetttant de parcourir tous les jeux une fois que celles ci ont été lu dans le fichier d'entrée
 */
public class PgnIterator implements Iterable<Game>, AutoCloseable {

    private final Iterator<String> pgnLines;

    private Game game;


    public PgnIterator(String filename) throws Exception {

        this(new LargeFile(filename));
    }

    public PgnIterator(LargeFile file) {

        this.pgnLines = file.iterator();
        loadNextGame();
    }

    @Override
    public Iterator<Game> iterator() {
        return new GameIterator();
    }


    @Override
    public void close() {

        if (pgnLines instanceof LargeFile) {
            ((LargeFile) (pgnLines)).close();
        }
    }

    private void loadNextGame() {

        game = GameLoader.loadNextGame(pgnLines);
    }

    private class GameIterator implements Iterator<Game> {

        public boolean hasNext() {

            return game != null;
        }

        public Game next() {

            Game current = game;
            loadNextGame();
            return current;
        }

        public void remove() {
        }
    }
}
