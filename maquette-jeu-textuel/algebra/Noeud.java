package algebra;

import java.util.ArrayList;
import java.util.List;

public class Noeud implements Comparable<Noeud>{
	private List<Noeud> enfants = new ArrayList<>();
	private TypeDeNoeud typeDeNoeud;
	private boolean valeurBoolean;
	private int valeurInt;
	private String valeurString;

	public String getValeurString() {
		return valeurString;
	}

	public void setValeurString(String valeurString) {
		this.valeurString = valeurString;
	}

	public Noeud(TypeDeNoeud typeDeNoeud, int valeur) {
		this.typeDeNoeud = typeDeNoeud;
		this.valeur = valeur;
	}

	public Noeud(TypeDeNoeud cl) {
		this.typeDeNoeud = cl;
	}

	public void ajout(Noeud n) {
		enfants.add(n);
	}

	public Noeud enfant(int i) {
		return this.enfants.get(i);
	}

	public int nombreEnfants() {
		return this.enfants.size();
	}

	public TypeDeNoeud getTypeDeNoeud() {
		return typeDeNoeud;
	}


	public String toString() {
		String s = "<";
		if (typeDeNoeud != null) s = s + typeDeNoeud;
		s = s + ", " + valeur;
		return s + ">";
	}

	/*
	affiche le noeud n d'un arbre syntaxique avec un niveau d'indentation depth,
	et appels récursifs sur les noeuds enfants de n à un niveau d'indendation depth+1
	 */
	static void afficheNoeud(Noeud n, int profondeur) {
		String s = "";
		// identation
		for(int i=0;i<profondeur;i++) s = s + "  ";
		// informations sur le noeud
		int nbNoeudsEnfants =  n.nombreEnfants();
		s = s + n.toString() + (" (" + nbNoeudsEnfants + " noeuds enfants)");
		System.out.println(s);
		// appels récursifs
		for(int i=0;i<nbNoeudsEnfants;i++) {
			afficheNoeud(n.enfant(i), profondeur + 1);
		}
	}

	public boolean isValeurBoolean() {
		return valeurBoolean;
	}

	public void setValeurBoolean(boolean valeurBoolean) {
		this.valeurBoolean = valeurBoolean;
	}

	public int getValeurInt() {
		return valeurInt;
	}

	public void setValeurInt(int valeurInt) {
		this.valeurInt = valeurInt;
	}

	/**
	 * Compares this object with the specified object for order.  Returns a
	 * negative integer, zero, or a positive integer as this object is less
	 * than, equal to, or greater than the specified object.
	 *
	 * <p>The implementor must ensure {@link Integer#signum
	 * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
	 * all {@code x} and {@code y}.  (This implies that {@code
	 * x.compareTo(y)} must throw an exception if and only if {@code
	 * y.compareTo(x)} throws an exception.)
	 *
	 * <p>The implementor must also ensure that the relation is transitive:
	 * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
	 * {@code x.compareTo(z) > 0}.
	 *
	 * <p>Finally, the implementor must ensure that {@code
	 * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
	 * == signum(y.compareTo(z))}, for all {@code z}.
	 *
	 * @param o the object to be compared.
	 * @return a negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than the specified object.
	 * @throws NullPointerException if the specified object is null
	 * @throws ClassCastException   if the specified object's type prevents it
	 *                              from being compared to this object.
	 * @apiNote It is strongly recommended, but <i>not</i> strictly required that
	 * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
	 * class that implements the {@code Comparable} interface and violates
	 * this condition should clearly indicate this fact.  The recommended
	 * language is "Note: this class has a natural ordering that is
	 * inconsistent with equals."
	 */
	@Override
	public int compareTo(Noeud o) {
		return Integer.compare(valeur, o.valeur);
	}
}
