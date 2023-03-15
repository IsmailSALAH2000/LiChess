package client;
/*
 * Cette classe sert à gérer la saisie clavier d'un utilisateur
 * et à l'envoyer au serveur
 */

import java.io.*;

class Saisie extends Thread {
    private BufferedReader entree;
    private PrintWriter writer;

    public Saisie(PrintWriter writer) {
        entree = new BufferedReader(new InputStreamReader(System.in));
        this.writer = writer;
    }

    public void run() {
        String requete;

        /*Tant que l'on ne signale pas la fin des requetes on continue à récuperer
         *les nouvelles requetes
         */
        try{
            
            do{
                requete = entree.readLine();
                writer.println(requete);
            }while(!requete.equals("END"));
        }catch(IOException e){e.printStackTrace();}
        Client.arret = true;
    }
}
