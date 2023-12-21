package algebra;

import java.util.List;

public class TP3 {

	public static void main(String[] args) {

		testAnalyseLexicale("a < 2 & (b | !c) & d < a & e = 3");
		// testAnalyseSyntaxique("input a input b print 12+a*(b+1) print 2*3+5 print pow(a+1,b)+2");
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
			List<Token> tokens = new AnalyseLexicale().analyse(entree);
			for (Token t : tokens) {
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
	/*
	private static void testAnalyseSyntaxique(String entree) {
		System.out.println("test analyse syntaxique");
		try {
			List<Token> tokens = new AnalyseLexicale().analyse(entree);
			Noeud racine = new AnalyseSyntaxique().analyse(tokens);
			Noeud.afficheNoeud(racine, 0);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		System.out.println();
	}

	/*
	effectue l'analyse lexicale et syntaxique de la chaine entree,
	affiche et interprète l'arbre syntaxique abstrait
	 */
	/*
	private static void testInterpretation(String entree) {
		System.out.println("test interpretation");
		try {
			List<Token> tokens = new AnalyseLexicale().analyse(entree);
			Noeud racine = new AnalyseSyntaxique().analyse(tokens);
			Noeud.afficheNoeud(racine, 0);
			new Interpretation().interpreter(racine);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}*/

}
