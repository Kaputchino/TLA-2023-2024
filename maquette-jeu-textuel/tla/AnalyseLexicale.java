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
            //                      0       1       2       3       4       5       6       7       8
            //                      blank   §       -       #       int     space   str     :       `
            /* 0         */         { 0,    101,    102,    4,      1,      2,      3,      3,      6         },
            /* 1 dep int */         { 104,  104,    104,    104,    1,      104,    106,    106,    3         },
            /* 2 dep espace*/       { 2,    105,    105,    105,    105,    2,      106,    106,    6         },
            /* 3 dep str */         { 106,  106,    106,    106,    3,      3 ,     3,      5,      3         },
            /* 4 dep # */           { 3 ,   107,    3,      103,    3,      107,    4,      3,      3         },
            /* 5 dep : */           { 3,    106,    106,    107,    3,      107,    5,      3,      6         },
            /* 6 dep ` */           { 6,    108,      6,      3,    6,      6,      6,      3,      6         },

            // condition: `statement` : ou espace, ou blank.
            // 101 acceptation d'un §
            // 102 acceptation d'un -
            // 103 acceptation d'un #
            // 104 acceptation d'un entier (retourArriere)
            // 105 acceptation d'un caractere (retourArriere)
            // 106 acceptation d'une lettre (retourArriere)
            // 107 - acceptation d'un identifiant (retourArriere):
            // =====> #objet, #stat, #flag, condition:, effet:, #add, #sub, #set\
            // 108 acceptation d'un statement
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
                    if (!buf.equals(" ")) {
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
                    else if(buf.equals("#objects")){
                        tokens.add(new Token(TypeDeToken.objet));
                    }
                    else if(buf.equals("#add")){
                        tokens.add(new Token(TypeDeToken.condAdd));
                    }
                    else if(buf.equals("#sub")){
                        tokens.add(new Token(TypeDeToken.condSub));
                    }
                    else if(buf.equals("#set")){
                        tokens.add(new Token(TypeDeToken.condSet));
                    }
                    else if(buf.equals("condition:")){
                        tokens.add(new Token(TypeDeToken.cond));
                    }
                    else if(buf.equals("effect:")){
                        tokens.add(new Token(TypeDeToken.effet));
                    }

                    retourArriere();
                }  else if (e == 108) {
                    tokens.add(new Token(TypeDeToken.statement, buf));
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
        if (Character.isWhitespace(c))
            if (c == ' ') return 5;
            else return 0;
        if (c == '§')
            return 1;
        if (c == '-')
            return 2;
        if (c == '#')
            return 3;
        if (c == '`')
            return 8;
        if (Character.isDigit(c))
            return 4;
        if (Character.isLetter(c))
            return 6;
        if (c == ':')
            return 7;

        return 5;
    }

}