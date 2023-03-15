
package donnees.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

/**
 * Cette classe représente un fichier lourd. les lignes sont lu une par une
 * et non charger en un seul coup pour éviter les problèmes de mémoire
 */
public class LargeFile implements Iterable<String>, AutoCloseable {

    private final BufferedReader reader;

    private String nextLine;

    public LargeFile(String filePath) throws Exception {

        reader = new BufferedReader(new FileReader(filePath));
        readNextLine();
    }


    @Override
    public void close() {
        try {
            reader.close();
        } catch (Exception ignored) {
        }
    }


    @Override
    public Iterator<String> iterator() {
        return new FileIterator();
    }

    private void readNextLine() {

        try {
            nextLine = reader.readLine();
        } catch (Exception ex) {
            nextLine = null;
            throw new IllegalStateException("Error reading file", ex);
        }
    }

    private class FileIterator implements Iterator<String> {

        public boolean hasNext() {
            return nextLine != null;
        }

        public String next() {

            String currentLine = nextLine;
            readNextLine();
            return currentLine;
        }

        public void remove() {
        }
    }
}
