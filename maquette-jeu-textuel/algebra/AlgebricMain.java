package algebra;

import java.util.List;

public class AlgebricMain {

	public static void main(String[] args) {
		//testAnalyseLexicale("a < 2 & ( b = 4 & c > a & tag1 | !tag2 )");
		testAnalyseSyntaxique("a < 2 & ( b = 4 & c > a & !tag1 ) & tag2 ");
		// testInterpretation("input a input b print 12+a*(b+2) print 2*3+5 print pow(a+1,b)+2");
		// testInterpretation("input a input c print a+3*c");
		//testInterpretation("print 1+3 print 2 print 3 print 4 print 3*2 print pow(4,3) input a print a print a * 2");
	}

	/*
	effectue l'analyse lexicale de la chaine entree,
	affiche la liste des tokens reconnus
	 */
	private static void testAnalyseLexicale(String entree) {
		System.out.println("test analyse lexicale");
		try {
			List<AlgebricToken> algebricTokens = new AlgebricAnalyseLexicale().analyse(entree);
			for (AlgebricToken t : algebricTokens) {
				System.out.println(t);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println("fin");
	}

	/*
	effectue l'analyse lexicale et syntaxique de la chaine entree
	 */

	private static void testAnalyseSyntaxique(String entree) {
		System.out.println("test analyse syntaxique");
		System.out.println(entree);
		try {
			List<AlgebricToken> algebricTokens = new AlgebricAnalyseLexicale().analyse(entree);
			AlgebricNoeud racine = new AlgebricAnalyseSyntaxique().analyse(algebricTokens);
			AlgebricNoeud.afficheNoeud(racine, 0);
			System.out.println("post sorting");
			racine = new AlgebricSorter().sort(racine);
			AlgebricNoeud.afficheNoeud(racine, 0);
			boolean value = new AlgebricInterpretation().interpreter(racine);
			System.out.println(value);
			AlgebricNoeud.afficheNoeud(racine, 0);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println("hi");
	}

	/*
	effectue l'analyse lexicale et syntaxique de la chaine entree,
	affiche et interprète l'arbre syntaxique abstrait
	 */
	/*
	private static void testInterpretation(String entree) {
		System.out.println("test interpretation");
		try {
			List<AlgebricToken> tokens = new AlgebricAnalyseLexicale().analyse(entree);
			AlgebricNoeud racine = new AlgebricAnalyseSyntaxique().analyse(tokens);
			AlgebricNoeud.afficheNoeud(racine, 0);
			new AlgebricInterpretation().interpreter(racine);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}*/

}
