package tla;

import exception_tla.IllegalCharacterException;
import exception_tla.LexicalErrorException;

import java.util.ArrayList;
import java.util.List;

/*
 * MULARD Andreas 
 * BERTHEL Gabriel
 * LAGASSE Adrian 
 * YOUSRI Célia
 */

public class AnalyseLexicale {

    /*
     * Table de transition de l'analyse lexicale
     */
    private static Integer TRANSITIONS[][] = {
          // espace < - # chiffre caractere Autre_lettre :
            /* 0 */ { 0, 101, 102, 5, 2, 3, 4, 4 },
            /* 1 */ { 3, 3, 3, 103, 3, 3, 3, 3 },
            /* 2 */ { 104, 104, 104, 104, 2, 104, 106, 106 },
            /* 3 */ { 3, 105, 105, 105, 105, 3, 106, 106},
            /* 4 */ { 106, 106, 106, 106, 4, 4, 4, 4 },
            /* 5 */ { 106, 107, 106, 103, 106, 5, 5, 106 },
            /* 6 {106, 106, 106, 103, 106, 106, 106 } */

            // 101 acceptation d'un <
            // 102 acceptation d'un -
            // 103 acceptation d'un #
            // 104 acceptation d'un entier (retourArriere)
            // 105 acceptation d'un caractere (retourArriere)
            // 106 acceptation d'une lettre (retourArriere)

            // 107 acceptation d'un objet, stats ou flag en fonction du buffer
            // 108 acceptation d'un : 
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

        Character c = 'a';
        do {
            boolean first = c == '\n';
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
                    if (first) {
                        tokens.add(new Token(TypeDeToken.tiret));
                    }
                } else if (e == 103) {
                    tokens.add(new Token(TypeDeToken.finLieu));
                } else if (e == 104) {
                    tokens.add(new Token(TypeDeToken.intVal, buf));
                    retourArriere();
                } else if (e == 105) {
                    String buf2 = buf.replaceAll("\\s", "");
                    if (!buf2.equals("\n") && !buf2.equals("")) {
                        tokens.add(new Token(TypeDeToken.stringVal, buf));
                    }
                    retourArriere();
                } else if (e == 106) {
                    if(buf.equals("conds")){
                        tokens.add(new Token(TypeDeToken.cond));
                    }
                    else if(buf.equals("effets")){
                        tokens.add(new Token(TypeDeToken.effet));
                    }
                    else if (!buf.equals(" ")) {
                        tokens.add(new Token(TypeDeToken.stringVal, buf));
                    }
                    retourArriere();
                } else if(e == 107){
                    if(buf.equals("#stats")){
                        tokens.add(new Token(TypeDeToken.stat));
                    }
                    else if(buf.equals("#flags")){
                        tokens.add(new Token(TypeDeToken.flag));
                    }
                    else if(buf.equals("#objets")){
                        tokens.add(new Token(TypeDeToken.objet));
                    }
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

        return fixMultiLine(tokens);
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

    private List<Token> fixMultiLine(List<Token> tokens) {
        List<Token> fixedToken = new ArrayList<>();
        boolean previous = false;
        Token save = null;
        for (Token token : tokens) {
            if (token.getTypeDeToken() != TypeDeToken.stringVal) {
                fixedToken.add(token);
                previous = false;
            } else {
                if (!previous) {
                    previous = true;
                    save = token;
                    fixedToken.add(save);
                } else {
                    save.merge(token);
                }
            }
        }
        return fixedToken;
    }

    /*
     * Pour chaque symbole terminal acceptable en entrée de l'analyse syntaxique
     * retourne un indice identifiant soit un symbole, soit une classe de symbole :
     */
    private static int indiceSymbole(Character c) throws IllegalCharacterException {
        if (c == null)
            return 0;
        if (c == ' ') {
            return 5;
        }
        if (Character.isWhitespace(c))
            return 0;
        if (c == '<')
            return 1;
        if (c == '-')
            return 2;
        if (c == '#')
            return 3;
        if (c == ':')
            return 4;
        if (Character.isDigit(c))
            return 4;
        if (Character.isLetter(c))
            return 6;

        return 5;
    }

}