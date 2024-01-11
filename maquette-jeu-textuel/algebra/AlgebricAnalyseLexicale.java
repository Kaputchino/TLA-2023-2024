package algebra;

import exception_tla.IllegalCharacterException;

import java.util.ArrayList;
import java.util.List;

public class AlgebricAnalyseLexicale {

	/*
	Table de transition de l'analyse lexicale
	 */
	private static Integer TRANSITIONS[][] = {
			//            espace  and   or  (  	   )   !     chiffe lettre    >      <   =     +    -    *   /  %
			/*  0 */    {      0, 101, 102, 103, 104, 105,       1,      2  , 108, 109, 110, 111, 112, 113 ,114, 115},
			/*  1 */    {    106, 106, 106, 106, 106, 106,       1,    106  , 106, 106, 106, 106, 106, 106, 106, 115},
			/*  2 */    {    107, 107, 107, 107, 107, 107,       2,      2  , 107, 107, 107, 107, 107, 107, 107, 115}
			// 101 acceptation d'un and
			// 102 acceptation d'un or
			// 103 acceptation d'un (
			// 104 acceptation d'un )
			// 105 acceptation d'un !
			// 106 acceptation d'un entier                   (retourArriere)
			// 107 acceptation d'un identifiant ou mot clé   (retourArriere)
			// 108 acceptation d'un >
			// 109 acceptation d'un <
			// 110 acceptation d'un =
			// 111 acceptation d'un +
			// 112 acceptation d'un -
			// 113 acceptation d'un *
			// 114 acceptation d'un /
			// 115 acceptation d'un %

	};

	private String entree;
	private int pos;

	private static final int ETAT_INITIAL = 0;

	/*
	effectue l'analyse lexicale et retourne une liste de AlgebricToken
	 */
	public List<AlgebricToken> analyse(String entree) throws Exception {

		this.entree=entree;
		pos = 0;

		List<AlgebricToken> algebricTokens = new ArrayList<>();

		/* copie des symboles en entrée
		- permet de distinguer les mots-clés des identifiants
		- permet de conserver une copie des valeurs particulières des algebricTokens de type ident et intval
		 */
		String buf = "";

		Integer etat = ETAT_INITIAL;

		Character c;
		do {
			c = lireCaractere();
			Integer e = TRANSITIONS[etat][indiceSymbole(c)];
			if (e == null) {
				System.out.println("pas de transition depuis état " + etat + " avec symbole " + c);
				throw new AlgebricLexicalErrorException("pas de transition depuis état " + etat + " avec symbole " + c);
			}
			// cas particulier lorsqu'un état d'acceptation est atteint
			if (e >= 100) {
				if (e == 101) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.and));
				} else if (e == 102) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.or));
				} else if (e == 103) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.leftPar));
				} else if (e == 104) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.rightPar));
				} else if (e == 105) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.inverse));
				} else if (e == 106) {
					if(buf.length() - buf.replaceAll("g","").length() > 1){
						throw new IllegalCharacterException("trop de '.'");
					}
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.floatVal, buf));

					retourArriere();
				} else if (e == 107) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.ident, buf));
					retourArriere();
				} else if (e == 108) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.sup, buf));
				}
				else if (e == 109) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.inf, buf));
				}
				else if (e == 110) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.equal, buf));
				} else if (e == 111) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.add));
				} else if (e == 112) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.sub));
				}
				else if (e == 113) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.mul));
				}
				else if (e == 114) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.div));
				}else if (e == 115) {
					algebricTokens.add(new AlgebricToken(AlgebricTypeDeToken.modulo));
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
				if (etat>0) buf = buf + c;
			}

		} while (c != null);

		return algebricTokens;
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
	Pour chaque symbole terminal acceptable en entrée de l'analyse syntaxique
	retourne un indice identifiant soit un symbole, soit une classe de symbole :
	 */
	private static int indiceSymbole(Character c) throws AlgebricIllegalCharacterException {
		if (c == null) return 0;
		if (Character.isWhitespace(c)) return 0;
		if (c == '&') return 1;
		if (c == '|') return 2;
		if (c == '(') return 3;
		if (c == ')') return 4;
		if (c == '!') return 5;
		if (Character.isDigit(c)) return 6;
		if (Character.isLetter(c)) return 7;
		if (c == '>') return 8;
		if (c == '<') return 9;
		if (c == '=') return 10;
		if (c == '.') return 6;
		if (c == '+') return 11;
		if (c == '-') return 12;
		if (c == '*') return 13;
		if (c == '/') return 14;
		if (c == '%') return 15;
		return 7;


		//System.out.println("Symbole inconnu : " + c);
		//throw new AlgebricIllegalCharacterException(c.toString());
	}

}
