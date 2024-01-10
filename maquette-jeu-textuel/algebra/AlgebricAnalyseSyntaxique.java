package algebra;

import tla.TypeDeNoeud;

import java.util.List;

public class AlgebricAnalyseSyntaxique {

	private int pos;
	private List<AlgebricToken> algebricTokens;

	AlgebricToken lireToken(){
		AlgebricToken algebricToken = algebricTokens.get(pos);
		pos++;
		return algebricToken;
	}

	AlgebricTypeDeToken getTypeDeToken(){

		return algebricTokens.get(pos).getTypeDeToken();
	}

	/*
	effectue l'analyse syntaxique à partir de la liste de algebricTokens
	et retourne le noeud racine de l'arbre syntaxique abstrait
	 */

	public AlgebricNoeud analyse(List<AlgebricToken> algebricTokens) throws Exception {
		pos = 0;
		this.algebricTokens = algebricTokens;
		AlgebricNoeud expr = S();
		if (pos != algebricTokens.size()) {
			System.out.println("L'analyse syntaxique s'est terminé avant l'examen de tous les algebricTokens");
			throw new AlgebricIncompleteParsingException();
		}
		return expr;
	}

	private AlgebricNoeud S() throws AlgebricUnexpectedTokenException {
		AlgebricNoeud nodeStmt = new AlgebricNoeud(AlgebricTypeDeNoeud.statement);

		//S->AD
		if (getTypeDeToken() == AlgebricTypeDeToken.ident || getTypeDeToken() == AlgebricTypeDeToken.floatVal) {
			nodeStmt.ajout(A());
			AlgebricNoeud d = D();
			if(d != null){
				nodeStmt.ajout(d);
			}
			return nodeStmt;
		}
		//S->!SD
		if (getTypeDeToken() == AlgebricTypeDeToken.inverse) {
			AlgebricNoeud algebricNoeud = new AlgebricNoeud(AlgebricTypeDeNoeud.inverse);
			AlgebricToken t = lireToken();
			nodeStmt.ajout(algebricNoeud);
			algebricNoeud.ajout(S());
			AlgebricNoeud d = D();
			if(d != null){
				nodeStmt.ajout(d);
			}
			return nodeStmt;
		}
		//S->(S)D
		if (getTypeDeToken() == AlgebricTypeDeToken.leftPar) {
			lireToken();
			AlgebricNoeud algebricNoeud = S();
			if (getTypeDeToken() == AlgebricTypeDeToken.rightPar) {
				lireToken();
				nodeStmt.ajout(algebricNoeud);
			}
			AlgebricNoeud d = D();
			if(d != null){
				nodeStmt.ajout(d);
			}
			return nodeStmt;

		}else {
			throw new AlgebricUnexpectedTokenException("'input' ou 'print' attendu");
		}
	}
	private AlgebricNoeud A() throws AlgebricUnexpectedTokenException {
		//A->BC
		AlgebricNoeud n = new AlgebricNoeud(AlgebricTypeDeNoeud.statement);
		n.ajout(B());
		AlgebricNoeud c = C();
		if(c != null){
			n.ajout(c);
		}
		return n;
	}
	private AlgebricNoeud B() throws AlgebricUnexpectedTokenException {
		AlgebricNoeud statement = new AlgebricNoeud(AlgebricTypeDeNoeud.statement);
		if (getTypeDeToken() == AlgebricTypeDeToken.floatVal) {
			// production B -> intValE
			AlgebricToken t = lireToken();
			AlgebricNoeud al = new AlgebricNoeud(AlgebricTypeDeNoeud.floatVal, Float.parseFloat(t.getValeur()));
			AlgebricNoeud e = E();
			statement.ajout(al);
			if(e!= null){
				statement.ajout(e);
			}
			return statement;
		}

		if (getTypeDeToken() == AlgebricTypeDeToken.ident) {

			// production B -> identE
			AlgebricToken t = lireToken();
			AlgebricNoeud al = new AlgebricNoeud(AlgebricTypeDeNoeud.ident, t.getValeur());
			statement.ajout(al);
			AlgebricNoeud e = E();
			if(e!= null){
				statement.ajout(e);
			}
			return statement;
		}
		throw new AlgebricUnexpectedTokenException("input'' ou 'print' attendu");

	}
	private AlgebricNoeud C() throws AlgebricUnexpectedTokenException {
		AlgebricNoeud statement = new AlgebricNoeud(AlgebricTypeDeNoeud.statement);
		if(finAtteinte()){
			return null;
		}
		//C-><B
		if (getTypeDeToken() == AlgebricTypeDeToken.inf) {
			AlgebricToken t = lireToken();
			AlgebricNoeud n = new AlgebricNoeud(AlgebricTypeDeNoeud.inf);
			n.ajout(B());
			return n;
		}
		//C->>B
		if (getTypeDeToken() == AlgebricTypeDeToken.sup) {
			AlgebricToken t = lireToken();
			AlgebricNoeud n = new AlgebricNoeud(AlgebricTypeDeNoeud.sup);
			n.ajout(B());
			return n;
		}
		//C->=B
		if (getTypeDeToken() == AlgebricTypeDeToken.equal) {
			AlgebricToken t = lireToken();
			AlgebricNoeud n = new AlgebricNoeud(AlgebricTypeDeNoeud.equ);
			n.ajout(B());
			return n;
		}
		//C->epsilon
		if(getTypeDeToken() == AlgebricTypeDeToken.rightPar || getTypeDeToken() == AlgebricTypeDeToken.or || getTypeDeToken() == AlgebricTypeDeToken.and ){
			return null;
		}
		throw new AlgebricUnexpectedTokenException("input'' ou 'print' attendu");
	}
	private AlgebricNoeud D() throws AlgebricUnexpectedTokenException {
		AlgebricNoeud statement = new AlgebricNoeud(AlgebricTypeDeNoeud.statement);
		//D-> or S
		if(finAtteinte()){
			return null;
		}
		if (getTypeDeToken() == AlgebricTypeDeToken.or) {
			AlgebricToken t = lireToken();
			statement.ajout(new AlgebricNoeud(AlgebricTypeDeNoeud.or));
			statement.ajout(S());
			return statement;
		}
		//D-> and S
		if (getTypeDeToken() == AlgebricTypeDeToken.and) {
			AlgebricToken t = lireToken();
			statement.ajout(new AlgebricNoeud(AlgebricTypeDeNoeud.and));
			statement.ajout(S());
			return statement;
		}
		//D->epsilon
		if (getTypeDeToken() == AlgebricTypeDeToken.inf ||getTypeDeToken() == AlgebricTypeDeToken.rightPar) {
			return null;
		}
		System.out.println(getTypeDeToken());
		throw new AlgebricUnexpectedTokenException("input'' ou 'print' attendu");
	}
	private AlgebricNoeud E() throws AlgebricUnexpectedTokenException {
		if(finAtteinte()){
			return null;
		}
		AlgebricNoeud statement = new AlgebricNoeud(AlgebricTypeDeNoeud.statement);
		if (getTypeDeToken() == AlgebricTypeDeToken.add) {
			AlgebricToken t = lireToken();
			statement.ajout(new AlgebricNoeud(AlgebricTypeDeNoeud.add));
			statement.ajout(B());
			return statement;
		}
		if (getTypeDeToken() == AlgebricTypeDeToken.div) {
			AlgebricToken t = lireToken();
			statement.ajout(new AlgebricNoeud(AlgebricTypeDeNoeud.div));
			statement.ajout(B());
			return statement;
		}
		if (getTypeDeToken() == AlgebricTypeDeToken.mul) {
			AlgebricToken t = lireToken();
			statement.ajout(new AlgebricNoeud(AlgebricTypeDeNoeud.mul));
			statement.ajout(B());
			return statement;
		}
		if (getTypeDeToken() == AlgebricTypeDeToken.sub) {
			AlgebricToken t = lireToken();
			statement.ajout(new AlgebricNoeud(AlgebricTypeDeNoeud.sub));
			statement.ajout(B());
			return statement;
		}
		if (getTypeDeToken() == AlgebricTypeDeToken.modulo) {
			AlgebricToken t = lireToken();
			statement.ajout(new AlgebricNoeud(AlgebricTypeDeNoeud.modulo));
			statement.ajout(B());
			return statement;
		}
		if (getTypeDeToken() == AlgebricTypeDeToken.rightPar || getTypeDeToken() == AlgebricTypeDeToken.sub || getTypeDeToken() == AlgebricTypeDeToken.equal || getTypeDeToken() == AlgebricTypeDeToken.inf || getTypeDeToken() == AlgebricTypeDeToken.and || getTypeDeToken() == AlgebricTypeDeToken.or || getTypeDeToken() == AlgebricTypeDeToken.sup) {
			return null;
		}
		throw new AlgebricUnexpectedTokenException("input'' ou 'print' attendu");
	}

	/*

	Traite la dérivation du symbole non-terminal S

	S ->  Statement S'

	 */
/*
	private AlgebricNoeud S() throws AlgebricUnexpectedTokenException {

		AlgebricNoeud nodeStmt = new AlgebricNoeud(AlgebricTypeDeNoeud.statement);

		if (getTypeDeToken() == AlgebricTypeDeToken.kInput ||
				getTypeDeToken() == AlgebricTypeDeToken.kPrint) {

			nodeStmt.ajout(Statement());
		} else {
			throw new AlgebricUnexpectedTokenException("input'' ou 'print' attendu");
		}

		AlgebricNoeud next = S_prime();
		if (next != null) {
			nodeStmt.ajout(next);
		}

		return nodeStmt;
	}

	private AlgebricNoeud statement() throws AlgebricUnexpectedTokenException {
		if (getTypeDeToken() == AlgebricTypeDeToken.leftPar) {

			// production B -> ( Expr )

			lireToken();
			AlgebricNoeud s = Expr();

			if (getTypeDeToken() == AlgebricTypeDeToken.rightPar) {
				lireToken();
				return s;
			}
			throw new AlgebricUnexpectedTokenException(") attendu");
		}
		if (getTypeDeToken() == AlgebricTypeDeToken.intVal) {

			// production B -> intVal

			AlgebricToken t = lireToken();
			return new AlgebricNoeud(AlgebricTypeDeNoeud.intVal, t.getValeur());
		}

		if (getTypeDeToken() == AlgebricTypeDeToken.ident) {

			// production B -> ident

			AlgebricToken t = lireToken();
			return new AlgebricNoeud(AlgebricTypeDeNoeud.ident, t.getValeur());
	}

	/*

	méthodes utilitaires

	 */

	private boolean finAtteinte() {
		return pos >= algebricTokens.size();
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
