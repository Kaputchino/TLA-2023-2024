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

	private Noeud S() throws UnexpectedTokenException {
		Noeud nodeStmt = new Noeud(TypeDeNoeud.statement);

		//S->AD
		if (getTypeDeToken() == TypeDeToken.ident || getTypeDeToken() == TypeDeToken.intVal) {
			nodeStmt.ajout(A());
			Noeud d = D();
			if(d != null){
				nodeStmt.ajout(d);
			}
		}
		//S->!SD
		if (getTypeDeToken() == TypeDeToken.inverse) {
			Noeud noeud = new Noeud(TypeDeNoeud.inverse);
			Token t = lireToken();
			nodeStmt.ajout(noeud);
			noeud.ajout(S());
			Noeud d = D();
			if(d != null){
				nodeStmt.ajout(d);
			}
		}
		//S->(S)D
		if (getTypeDeToken() == TypeDeToken.leftPar) {
			Noeud noeud = S();
			lireToken();
			if (getTypeDeToken() == TypeDeToken.rightPar) {
				lireToken();
				nodeStmt.ajout(noeud);
			}
			Noeud d = D();
			if(d != null){
				nodeStmt.ajout(d);
			}

		}else {
			throw new UnexpectedTokenException("'input' ou 'print' attendu");
		}
		return nodeStmt;
	}
	private Noeud A() throws UnexpectedTokenException {
		//A->BC
		Noeud n = new Noeud(TypeDeNoeud.statement);
		n.ajout(B());
		Noeud c = C();
		if(c != null){
			n.ajout(c);
		}
		return n;
	}
	private Noeud B() throws UnexpectedTokenException {
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
		throw new UnexpectedTokenException("input'' ou 'print' attendu");

	}
	private Noeud C() throws UnexpectedTokenException {
		Noeud statement = new Noeud(TypeDeNoeud.statement);
		//C-><B
		if (getTypeDeToken() == TypeDeToken.inf) {
			Token t = lireToken();
			statement.ajout(new Noeud(TypeDeNoeud.inf));
			return B();
		}
		//C->>B
		if (getTypeDeToken() == TypeDeToken.sup) {
			Token t = lireToken();
			statement.ajout(new Noeud(TypeDeNoeud.sup));
			return B();
		}
		//C->=B
		if (getTypeDeToken() == TypeDeToken.equal) {
			Token t = lireToken();
			statement.ajout(new Noeud(TypeDeNoeud.equ));
			return B();
		}
		//C->epsilon
		if(getTypeDeToken() == TypeDeToken.rightPar){
			return null;
		}
		throw new UnexpectedTokenException("input'' ou 'print' attendu");
	}
	private Noeud D() throws UnexpectedTokenException {

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
			throw new UnexpectedTokenException("input'' ou 'print' attendu");
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
