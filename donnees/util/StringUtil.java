package donnees.util;

/**
 * Quelques methodes utilitaires pour gerer les chaines de caractères
 */
public class StringUtil {

    public static String afterSequence(final String str, final String seq) {
        int idx = str.indexOf(seq) + seq.length();
        if (idx == 0) {
            return "";
        }
        return str.substring(idx);
    }


    public static String beforeSequence(final String str, final String seq) {
        int idx = str.indexOf(seq);
        if (idx == -1) {
            return str;
        }
        return str.substring(0, idx);
    }

    public static StringBuilder replaceAll(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length();
            index = builder.indexOf(from, index);
        }
        return builder;
    }

}
