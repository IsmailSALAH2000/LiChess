package serveur;
/*
 *Cette classe permet la gestions des connections de clients
 *au serveur et va permettre de demander le traitement
 *de leur demande 
 */
import java.io.*;
import java.net.*;

class Connexion extends Thread{

    private Socket         socket;
    private BufferedReader reader;
    private PrintWriter    writer;
    private int            numId;

    public  Connexion(int numId, Socket socket){
        this.numId = numId;
        this.socket = socket;
        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new BufferedWriter( 
                                     new OutputStreamWriter(socket.getOutputStream())),true);
        }catch(IOException e){e.printStackTrace();}
        Serveur.pw[numId] = writer;
    }

    public void run(){
        try{
            Serveur.pw[numId].println(Serveur.data.getCommands());
            String requete = reader.readLine();
            if(requete.equals("")){
                requete ="null";
            }
            while (!requete.equals("END")) {
                Serveur.pw[numId].println(Serveur.data.getCommands());
                requete = reader.readLine();
                if (requete.equals("END") || requete.equals("")){ 
                    if(requete.equals("END")){
                         break;
                    }
                }
                /*
                *   Demande de traitement de la demande du client
                **/
                else{
                    Object reponse = Serveur.data.askDataBase(requete);
                    Serveur.pw[numId].println(reponse);
                }
            }
            reader.close();
            writer.close();
            socket.close();
        }catch(IOException e){e.printStackTrace();}
    }
    
}