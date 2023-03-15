package donnees.pgn;

import donnees.util.StringUtil;

import java.util.regex.Pattern;

/**
 * Classe utilitaire permettant dedecouper les données lu dans le fichier d'entrée en propriétés compréhensibles par l'application
 */
public class PgnProperty {

    public final static String UTF8_BOM = "\uFEFF";

    private final static Pattern propertyPattern = Pattern.compile("\\[.* \".*\"]");

    public String name;

    public String value;


    public PgnProperty(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public static boolean isProperty(String line) {
        return propertyPattern.matcher(line).matches();
    }


    /**
     * Cette methode cré une propriété à partir de la string lu dans le fichier
     * @param line
     * @return
     */
    public static PgnProperty parsePgnProperty(String line) {
        try {

            String l = line.replace("[", "");
            l = l.replace("]", "");
            l = l.replace("\"", "");

            return new PgnProperty(StringUtil.beforeSequence(l, " "),
                    StringUtil.afterSequence(l, " "));
        } catch (Exception ignored) {}

        return null;
    }
}
