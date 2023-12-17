package tla;

import exception_tla.IncompleteParsingException;
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
        Noeud nvLieu = new Noeud(TypeDeNoeud.lieuContainer);
        
        if (getTypeDeToken() == TypeDeToken.intVal){
            nvLieu.ajout(Lieu());
        } else {
            throw new UnexpectedTokenException("'intVal' attendu");
        }

        /*
         * Lire ##
         */
        if (getTypeDeToken() == TypeDeToken.finLieu){
            Token t = lireToken();
			Noeud n = S_prime();
            if(n != null){
                nvLieu.ajout(n);
            }
			return nvLieu;
        }
        throw new UnexpectedTokenException("'##' attendu");
    }

    /*
      S' -> S | ε
     */
    private Noeud S_prime() throws UnexpectedTokenException{
        if (finAtteinte()){
            return null;
        } else {
            return S();
        }
		
    }

    /*
     * LIEU -> intVal string < PROPOSITION
     */
    private Noeud Lieu() throws UnexpectedTokenException{
        Noeud noeud = new Noeud(TypeDeNoeud.lieu);
        Token t = lireToken();
        /*On lit intval */
		if (t.getTypeDeToken() == TypeDeToken.intVal) {
            /*On lit le token suivant */
			Token t1 = lireToken();
            noeud.ajout(new Noeud(TypeDeNoeud.intVal,""+t.getValeur()));
            /*On lit String */
			if (t1.getTypeDeToken() == TypeDeToken.stringVal) {
                /*On lit le token suivant */
                Token t2 = lireToken();
                noeud.ajout(new Noeud(TypeDeNoeud.string,t1.getValeur()));
                 /*On lit < */
                    if (t2.getTypeDeToken() == TypeDeToken.separateurLigne) {
                        noeud.ajout(Proposition());
                        return noeud;
			        }
                    System.out.println("ca glitch");
                System.out.println(pos);

                System.out.println(t2.getTypeDeToken()+" "+t2.getValeur());
                    System.out.println(t1);
                    throw new UnexpectedTokenException("< attendu");
            }
            System.out.println(pos);
			throw new UnexpectedTokenException("String attendu");
		} 
		throw new UnexpectedTokenException("intVal attendu");
    }

    /*
     * PROPOSITION -> - intVal string < F P’
     */
    private Noeud Proposition() throws UnexpectedTokenException{
        Token t = lireToken();
        Noeud noeud = new Noeud(TypeDeNoeud.proposition);
        if (t.getTypeDeToken() == TypeDeToken.tiret) {
            Token t1 = lireToken();
            if (t1.getTypeDeToken() == TypeDeToken.intVal) {
                noeud.ajout(new Noeud(TypeDeNoeud.intVal, "" + t1.getValeur()));
                Token t2 = lireToken();
                    if (t2.getTypeDeToken() == TypeDeToken.stringVal) {
                        Token t3 = lireToken();
                        noeud.ajout(new Noeud(TypeDeNoeud.string, "" + t2.getValeur()));
                        if (t3.getTypeDeToken() == TypeDeToken.separateurLigne) {
                                Noeud f = F();
                                Noeud pp = P_prime();
                                if (f != null){
                                    noeud.ajout(f);
                                }
                                if (pp != null){
                                    noeud.ajout(pp);
                                }
                                return noeud;
                            }
                            System.out.println(t2.getTypeDeToken()+" "+t2.getValeur());
                            System.out.println(t1);
                            throw new UnexpectedTokenException("< attendu");
                    }
                    System.out.println(t2.getTypeDeToken()+" "+t2.getValeur());
                    System.out.println(t1);
                    throw new UnexpectedTokenException("stringVal attendu");
            }
            throw new UnexpectedTokenException("intVal attendu");
        }
        System.out.println(t);
        System.out.println(t.getValeur());
        System.out.println(pos);
        System.out.println(tokens.size());
        System.out.println(tokens.get(pos - 3));
        if(!t.getValeur().equals("\n")){
            throw new UnexpectedTokenException("- attendu");
        }
        return null;
    }
    
    /*
     * P' -> PROPOSITION | ε
     */
    private Noeud P_prime() throws UnexpectedTokenException {
        if (finAtteinte()){
            return null;
        }
        if(getTypeDeToken() == TypeDeToken.finLieu){
            return null;
        }
        return Proposition();
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
