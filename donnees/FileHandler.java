package donnees;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe utilitaire permettant de gerer l'enregistrement et le chargement des hashtables sur le disque
 */
public class FileHandler {

    public static void saveDataToFile(ConcurrentHashMap<String, ?> hashMap, String fileName) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(hashMap);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static ConcurrentHashMap<String, ?> loadFile(String fileName) {
        ConcurrentHashMap<String, ?> hashMap;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            hashMap = (ConcurrentHashMap<String, ?>) in.readObject();
            in.close();
            fileIn.close();
            return hashMap;
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
            return null;
        }
    }
}
