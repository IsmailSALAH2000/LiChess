package serveur;
/*
 * Nom de Classe : Serveur
 * 
 * Descrition : Gère la partie réseau côté serveur du projet, permet la création
 *              du serveur
 * 
 */

import java.io.*;
import java.net.*;

import serveur.traitementRequete.TraitementRequete;

public class Serveur {

    static int           port = 8080;
    static final int     MAX_CONNECTIONS = 4;
    static PrintWriter[] pw;
    static int           numId = 0;
    static TraitementRequete data = new TraitementRequete();

    public static void main(String[] args) throws Exception {
        pw = new PrintWriter[MAX_CONNECTIONS];
        ServerSocket serveur = new ServerSocket(port);
        System.out.println("SOCKET ECOUTE CREE => "+serveur);

        /*tant que l'on ne dépasse pas 4 connections on crééer un nouveau thread
         *qui gère la connection avec le client
         */
        while(numId <= MAX_CONNECTIONS){
            Socket socket = serveur.accept();
            Connexion c = new Connexion(numId, socket);
            System.out.println("NOUVELLE CONNEXION - SOCKET =>"+socket);
            numId++;
            c.start();
        }
        serveur.close();
    }
}
