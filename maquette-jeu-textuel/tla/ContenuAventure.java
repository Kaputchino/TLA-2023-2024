package tla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/*
 * Contenu de l'aventure.
 *
 * La HashMap lieux attribue un numéro à chaque lieu qui compose l'aventure.
 *
 * Ces textes proviennent de l'aventure écrite par de Denis Gerfaud,
 * "Le mystère de la statue maudite", parue dans le numéro 31 (février/mars 1985)
 * du magazine Jeux et Stratégie, page 66 à 69
 */
public class ContenuAventure {

    final public static String titre = "Le mystère de la statue maudite";
    private static File file;

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        ContenuAventure.file = file;
    }

    static Map<Integer, Lieu> init() {
        HashMap<Integer, Lieu> lieux = null;
        AnalyseLexicale analyseLexicale = new AnalyseLexicale();
        AnalyseSyntaxique analyseSyntaxique = new AnalyseSyntaxique();
        List<Token> tokens;
        Noeud entryPoint;
        Scanner scanner = null;
        String data;

        try {
            scanner = new Scanner(file);
            scanner.useDelimiter("\\Z");
            data = scanner.next();
            scanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            tokens = analyseLexicale.analyse(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        /**
         * tokens = List.of(new Token[]{
         * new Token(TypeDeToken.intVal, "1"),
         * new Token(TypeDeToken.stringVal, "Ceci est un lieu"),
         * new Token(TypeDeToken.separateurLigne),
         * new Token(TypeDeToken.tiret),
         * new Token(TypeDeToken.intVal, "0"),
         * new Token(TypeDeToken.stringVal, "Aller en 0"),
         * new Token(TypeDeToken.separateurLigne),
         * new Token(TypeDeToken.finLieu),
         * 
         * new Token(TypeDeToken.intVal, "0"),
         * new Token(TypeDeToken.stringVal, "Ceci est un lieu ddd"),
         * new Token(TypeDeToken.separateurLigne),
         * new Token(TypeDeToken.tiret),
         * new Token(TypeDeToken.intVal, "3"),
         * new Token(TypeDeToken.stringVal, "Aller en 3"),
         * new Token(TypeDeToken.separateurLigne),
         * 
         * new Token(TypeDeToken.tiret),
         * new Token(TypeDeToken.intVal, "0"),
         * new Token(TypeDeToken.stringVal, "Aller en 0"),
         * new Token(TypeDeToken.separateurLigne),
         * new Token(TypeDeToken.finLieu),
         * 
         * new Token(TypeDeToken.intVal, "3"),
         * new Token(TypeDeToken.stringVal, "Ceci est un lieu qwdqwd"),
         * new Token(TypeDeToken.separateurLigne),
         * new Token(TypeDeToken.tiret),
         * new Token(TypeDeToken.intVal, "1"),
         * new Token(TypeDeToken.stringVal, "Aller 1"),
         * new Token(TypeDeToken.separateurLigne),
         * new Token(TypeDeToken.finLieu)
         * });
         **/

        try {
            entryPoint = analyseSyntaxique.analyse(tokens);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            lieux = HashmapLoader.getHashMap(entryPoint);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(lieux);

        return lieux;
    }
}
