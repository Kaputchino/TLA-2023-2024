package algebra;

/* l'analyse lexicale a détectée, en entrée, un symbole inconnu (que l'analyse syntaxique n'accepte pas) */
public class AlgebricIllegalCharacterException extends Exception {
	public AlgebricIllegalCharacterException(String message) {
		super(message);
	}
}
