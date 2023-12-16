package tla;

import exception_tla.IncompleteParsingException;
import exception_tla.UnexpectedTokenException;
import exception_tla.UnexpectedTokenException;

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
    private Noeud S() throws UnexpectedTokenException{
        Noeud nvLieu = new Noeud(TypeDeNoeud.lieu);
        
        if (getTypeDeToken() == TypeDeToken.intVal){
            nvLieu.ajout(Lieu());
        } else {
            throw new UnexpectedTokenException("'intVal' attendu");
        }

        /*
         * Lire ##
         */
        if (getTypeDeToken() == TypeDeToken.separateurLigne){
            Token t = lireToken();
			Noeud n = new Noeud(TypeDeNoeud.lieu);
			n.ajout(S_prime());
			return n;
        }

        Noeud nextS = S_prime();
        if (nextS != null) {
			nvLieu.ajout(nextS);
		}

        return nvLieu;
    }

    /*
      S' -> S | ε
     */
    private Noeud S_prime() throws UnexpectedTokenException{
        if (finAtteinte()){
            return null;
        } else {
            Noeud s = new Noeud(TypeDeNoeud.lieu);
            return s;
        }
		
    }

    /*
     * LIEU -> intVal string < PROPOSITION
     */
    private Noeud Lieu() throws UnexpectedTokenException{
        Token t = lireToken();
        /*On lit intval */
		if (t.getTypeDeToken() == TypeDeToken.intVal) {
            /*On lit le token suivant */
			Token t1 = lireToken();
            /*On lit String */
			if (t1.getTypeDeToken() == TypeDeToken.stringVal) {
                /*On lit le token suivant */
                Token t2 = lireToken();
                 /*On lit < */
                    if (t2.getTypeDeToken() == TypeDeToken.separateurLigne) {
                        Noeud n = new Noeud(TypeDeNoeud.proposition);
                        n.ajout(new Noeud(TypeDeNoeud.proposition, t1.getValeur()));
                        return n;
			        }
                    throw new UnexpectedTokenException("< attendu");
            }
			throw new UnexpectedTokenException("String attendu");
		} 
		throw new UnexpectedTokenException("intVal attendu");
    }

    /*
     * PROPOSITION -> - intVal string < F P’
     */
    private Noeud Proposition() throws UnexpectedTokenException{
        Token t = lireToken();
        if (t.getTypeDeToken() == TypeDeToken.tiret) {
            Token t1 = lireToken();
            if (t1.getTypeDeToken() == TypeDeToken.intVal) {
                Token t2 = lireToken();
                    if (t2.getTypeDeToken() == TypeDeToken.stringVal) {
                        Token t3 = lireToken();
                            if (t3.getTypeDeToken() == TypeDeToken.separateurLigne) {
                                Noeud n = F();
                                Noeud pp = P_prime();
                                if (pp != null){
                                    return P_prime(); 
                                }
                                else {
                                    return F();
                                }
		                        
                            }
                            throw new UnexpectedTokenException("< attendu");
                    }
                    throw new UnexpectedTokenException("stringVal attendu");
            }
            throw new UnexpectedTokenException("intVal attendu");
        }
        throw new UnexpectedTokenException("- attendu");
    }
    
    /*
     * P' -> PROPOSITION | ε
     */
    private Noeud P_prime(){
        if (finAtteinte()){
            return null;
        } else {
            Noeud p = new Noeud(TypeDeNoeud.proposition);
            return p;
        }
    }

    /*
     * F -> ε
     */
    private Noeud F(){
        Noeud f = new Noeud(TypeDeNoeud.facultatif);
        if (finAtteinte()){
            return f;
        } else { /*Gerer dans le cas contraire */
            return f;
        }
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
