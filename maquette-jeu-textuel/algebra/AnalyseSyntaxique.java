package algebra;

import java.util.List;

public class AnalyseSyntaxique {

	private int pos;
	private List<Token> tokens;

	/*
	effectue l'analyse syntaxique à partir de la liste de tokens
	et retourne le noeud racine de l'arbre syntaxique abstrait
	 */
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
