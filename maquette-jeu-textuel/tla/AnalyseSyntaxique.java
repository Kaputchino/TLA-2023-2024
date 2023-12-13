package tla;

import exception_tla.IncompleteParsingException;

import java.util.List;

public class AnalyseSyntaxique {
    private int pos;
    private List<Token> tokens;

    public Noeud analyse(List<Token> tokens) throws Exception {
        pos = 0;
        this.tokens = tokens;
        Noeud expr = S();
        if (pos != tokens.size()) {
            System.out.println("L'analyse syntaxique s'est terminé avant l'examen de tous les tokens");
            throw new IncompleteParsingException();
        }
        return expr;
    }

    /*
     * S -> LIEU ## S'
     */
    private Noeud S(){
        Noeud nvNoeud = new Noeud(TypeDeNoeud.statement)
        return null;
    }

    /*
     * S' -> S | ε
     */
    private Noeud S_prime(){
        return null;
    }

    /*
     * LIEU -> intVal string < PROPOSITION
     */
    private Noeud Lieu(){
        return null;
    }

    /*
     * PROPOSITION -> - intVal string < F P’
     */
    private Noeud Proposition(){
        return null;
    }
    
    /*
     * P' -> PROPOSITION | ε
     */
    private Noeud P_prime(){
        return null;
    }

    /*
     * F -> ε
     */
    private Noeud F(){
        return null;
    }

    /*

	méthodes utilitaires

	 */

    private boolean finAtteinte() {
        return pos >= tokens.size();
    }

    /*
     * Retourne la classe du prochain token à lire
     * SANS AVANCER au token suivant
     */
    private TypeDeToken getTypeDeToken() {
        if (pos >= tokens.size()) {
            return null;
        } else {
            return tokens.get(pos).getTypeDeToken();
        }
    }

    /*
     * Retourne le prochain token à lire
     * ET AVANCE au token suivant
     */
    private Token lireToken() {
        if (pos >= tokens.size()) {
            return null;
        } else {
            Token t = tokens.get(pos);
            pos++;
            return t;
        }
    }

}
