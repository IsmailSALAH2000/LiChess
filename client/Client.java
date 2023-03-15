package client;
/*
 * Nom de Classe : Client
 * 
 * Descrition : Gère la partie réseau côté client du projet, permet la création
 *              d'un client
 * 
 */

import java.io.*;
import java.net.*; 

public class Client {
    static int port = 8080;
    static String ip = "127.0.0.1";
    static boolean arret = false;

    public static void main(String[] args) throws Exception {
        if (args.length!=0) ip = args[0];
        Socket socket = new Socket(ip, port);
        BufferedReader reader = new BufferedReader(
                          new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(new BufferedWriter(
                             new OutputStreamWriter(socket.getOutputStream())),true);
        Saisie saisie = new Saisie(writer);
        saisie.start();

        String reponse;
        while(!arret) {
            /*tant que l'utilisateur n'a pas signaler la fin de la communication
             *on récupère la réponse du serveur à la requête
             */
            String menu = reader.readLine();
            System.out.println(menu);
            reponse = reader.readLine();
            System.out.println(reponse);
        }
        reader.close();
        writer.close();
        socket.close();
    }
}
