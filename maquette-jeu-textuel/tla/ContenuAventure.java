package tla;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ContenuAventure {

    public static String titre = "Le mystère de la statue maudite";
    public static HashMap<String, Setting> settings;
    public static HashMap<Integer, Lieu> lieux;
    private static File file;

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        ContenuAventure.file = file;
    }

    static void init() {
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

        try {
            entryPoint = analyseSyntaxique.analyse(tokens);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Noeud.afficheNoeud(entryPoint, 1);
        try {
            nodeDecoder hashMaps = nodeDecoder.getHashMap(entryPoint);
            ContenuAventure.lieux = hashMaps.getLieux();
            ContenuAventure.settings = hashMaps.getSettings();
            ContenuAventure.titre = hashMaps.getTitle();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
