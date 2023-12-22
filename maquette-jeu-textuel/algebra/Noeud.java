package algebra;

import java.util.ArrayList;
import java.util.List;

public class Noeud{
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
		this.valeurInt = valeur;
	}
	public Noeud(TypeDeNoeud typeDeNoeud, String valeur) {
		this.typeDeNoeud = typeDeNoeud;
		this.valeurString = valeur;
	}
	public Noeud(TypeDeNoeud typeDeNoeud, boolean valeur) {
		this.typeDeNoeud = typeDeNoeud;
		this.valeurBoolean = valeur;
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
		s = s + ", " + valeurString+valeurBoolean+valeurInt;
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

	public List<Noeud> getEnfants() {
		return enfants;
	}

	public void setTypeDeNoeud(TypeDeNoeud typeDeNoeud) {
		this.typeDeNoeud = typeDeNoeud;
	}
}
