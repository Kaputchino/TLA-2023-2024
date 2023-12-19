package algebra;

import java.util.ArrayList;
import java.util.List;

public class Noeud {
	private List<Noeud> enfants = new ArrayList<>();
	private TypeDeNoeud typeDeNoeud;
	private String valeur;


	public Noeud(TypeDeNoeud typeDeNoeud, String valeur) {
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

	public String getValeur() {
		return valeur;
	}

	public String toString() {
		String s = "<";
		if (typeDeNoeud != null) s = s + typeDeNoeud;
		if (valeur != null) s = s + ", " + valeur;
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

}
