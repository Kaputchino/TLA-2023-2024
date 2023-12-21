package algebra;

import java.util.List;

public class AnalyseSyntaxique {

	private int pos;
	private List<Token> tokens;

	Token lireToken(){
		Token token = tokens.get(pos);
		pos++;
		return token;
	}

	TypeDeToken getTypeDeToken(){

		return tokens.get(pos).getTypeDeToken();
	}

	/*
	effectue l'analyse syntaxique à partir de la liste de tokens
	et retourne le noeud racine de l'arbre syntaxique abstrait
	 */
	/**
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

	Traite la dérivation du symbole non-terminal S

	S ->  Statement S'

	 */
/*
	private Noeud S() throws UnexpectedTokenException {

		Noeud nodeStmt = new Noeud(TypeDeNoeud.statement);

		if (getTypeDeToken() == TypeDeToken.kInput ||
				getTypeDeToken() == TypeDeToken.kPrint) {

			nodeStmt.ajout(Statement());
		} else {
			throw new UnexpectedTokenException("'input' ou 'print' attendu");
		}

		Noeud next = S_prime();
		if (next != null) {
			nodeStmt.ajout(next);
		}

		return nodeStmt;
	}

	private Noeud statement() throws UnexpectedTokenException {
		if (getTypeDeToken() == TypeDeToken.leftPar) {

			// production B -> ( Expr )

			lireToken();
			Noeud s = Expr();

			if (getTypeDeToken() == TypeDeToken.rightPar) {
				lireToken();
				return s;
			}
			throw new UnexpectedTokenException(") attendu");
		}
		if (getTypeDeToken() == TypeDeToken.intVal) {

			// production B -> intVal

			Token t = lireToken();
			return new Noeud(TypeDeNoeud.intVal, t.getValeur());
		}

		if (getTypeDeToken() == TypeDeToken.ident) {

			// production B -> ident

			Token t = lireToken();
			return new Noeud(TypeDeNoeud.ident, t.getValeur());
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


	/*
	 * Retourne le prochain token à lire
	 * ET AVANCE au token suivant
	 */

}
