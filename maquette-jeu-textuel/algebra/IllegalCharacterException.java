package algebra;

/* l'analyse lexicale a détectée, en entrée, un symbole inconnu (que l'analyse syntaxique n'accepte pas) */
public class IllegalCharacterException extends Exception {
	public IllegalCharacterException(String message) {
		super(message);
	}
}
