package donnees;

public class Main {
    public static void main(String[] args){
        if(args.length != 0) {
            PgnHandler.handle(args[0]);
        } else {
            System.out.println("Vous devez specifier l'emplacement du fichier PGN");
            System.out.println("ex: java donnees/Main donnees/data.pgn");
        }

    }
}
