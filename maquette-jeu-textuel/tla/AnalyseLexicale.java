package tla;

import java.util.ArrayList;
import java.util.List;

public class AnalyseLexicale {

    /*
     * Table de transition de l'analyse lexicale
     */
    private static Integer TRANSITIONS[][] = {
            // espace < - # chiffre caractereAutre
            /* 0 */ { 0, 101, 102, 1, 2, 3 },
            /* 1 */ { 3, 3, 3, 103, 3, 3 },
            /* 2 */ { 104, 104, 104, 2, 104, 104 },
            /* 3 */ { 105, 105, 105, 105, 105, 3 }

            // 101 acceptation d'un <
            // 102 acceptation d'un -
            // 103 acceptation d'un # (retour Arriere)
            // 104 acceptation d'un entier (retourArriere)
            // 105 acceptation d'un caractere (retourArriere)

    };

    private String entree;
    private int pos;

    private static final int ETAT_INITIAL = 0;

    /*
     * effectue l'analyse lexicale et retourne une liste de Token
     */
    public List<Token> analyse(String entree) throws Exception {

        this.entree = entree;
        pos = 0;

        List<Token> tokens = new ArrayList<>();

        /*
         * copie des symboles en entrée
         * - permet de distinguer les mots-clés des identifiants
         * - permet de conserver une copie des valeurs particulières des tokens de type
         * ident et intval
         */
        String buf = "";

        Integer etat = ETAT_INITIAL;

        Character c;
        do {
            c = lireCaractere();
            Integer e = TRANSITIONS[etat][indiceSymbole(c)];
            if (e == null) {
                System.out.println("pas de transition depuis état " + etat + " avec symbole " + c);
                throw new LexicalErrorException("pas de transition depuis état " + etat + " avec symbole " + c);
            }
            // cas particulier lorsqu'un état d'acceptation est atteint
            if (e >= 100) {
                if (e == 101) {
                    tokens.add(new Token(TypeDeToken.separateurLigne));
                } else if (e == 102) {
                    tokens.add(new Token(TypeDeToken.tiret));
                } else if (e == 103) {
                    tokens.add(new Token(TypeDeToken.diese));
                } else if (e == 104) {
                    tokens.add(new Token(TypeDeToken.intVal, buf));
                    retourArriere();
                } else if (e == 105) {
                    tokens.add(new Token(TypeDeToken.stringVal, buf));
                    retourArriere();
                }
                // un état d'acceptation ayant été atteint, retourne à l'état 0
                etat = 0;
                // reinitialise buf
                buf = "";
            } else {
                // enregistre le nouvel état
                etat = e;
                // ajoute le symbole qui vient d'être examiné à buf
                // sauf s'il s'agit un espace ou assimilé
                if (etat > 0)
                    buf = buf + c;
            }

        } while (c != null);

        return tokens;
    }

    private Character lireCaractere() {
        Character c;
        try {
            c = entree.charAt(pos);
            pos = pos + 1;
        } catch (StringIndexOutOfBoundsException ex) {
            c = null;
        }
        return c;
    }

    private void retourArriere() {
        pos = pos - 1;
    }

    /*
     * Pour chaque symbole terminal acceptable en entrée de l'analyse syntaxique
     * retourne un indice identifiant soit un symbole, soit une classe de symbole :
     */
    private static int indiceSymbole(Character c) throws IllegalCharacterException {
        if (c == null)
            return 0;
        if (Character.isWhitespace(c))
            return 0;
        if (c == '+')
            return 1;
        if (c == '*')
            return 2;
        if (c == '(')
            return 3;
        if (c == ')')
            return 4;
        if (c == ',')
            return 5;
        if (Character.isDigit(c))
            return 6;
        if (Character.isLetter(c))
            return 7;
        System.out.println("Symbole inconnu : " + c);
        throw new IllegalCharacterException(c.toString());
    }

}
