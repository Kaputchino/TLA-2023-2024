package algebra;

import java.util.ArrayList;
import java.util.List;

public class AlgebricNoeud {
	private List<AlgebricNoeud> enfants = new ArrayList<>();
	private AlgebricTypeDeNoeud algebricTypeDeNoeud;
	private boolean valeurBoolean;
	private float valeurFloat;
	private String valeurString;

	public String getValeurString() {
		return valeurString;
	}

	public void setValeurString(String valeurString) {
		this.valeurString = valeurString;
	}

	public AlgebricNoeud(AlgebricTypeDeNoeud algebricTypeDeNoeud, float valeur) {
		this.algebricTypeDeNoeud = algebricTypeDeNoeud;
		this.valeurFloat = valeur;
	}
	public AlgebricNoeud(AlgebricTypeDeNoeud algebricTypeDeNoeud, String valeur) {
		this.algebricTypeDeNoeud = algebricTypeDeNoeud;
		this.valeurString = valeur;
	}
	public AlgebricNoeud(AlgebricTypeDeNoeud algebricTypeDeNoeud, boolean valeur) {
		this.algebricTypeDeNoeud = algebricTypeDeNoeud;
		this.valeurBoolean = valeur;
	}

	public AlgebricNoeud(AlgebricTypeDeNoeud cl) {
		this.algebricTypeDeNoeud = cl;
	}

	public void ajout(AlgebricNoeud n) {
		enfants.add(n);
	}

	public AlgebricNoeud enfant(int i) {
		return this.enfants.get(i);
	}

	public int nombreEnfants() {
		return this.enfants.size();
	}

	public AlgebricTypeDeNoeud getTypeDeNoeud() {
		return algebricTypeDeNoeud;
	}


	public String toString() {
		String s = "<";
		if (algebricTypeDeNoeud != null) s = s + algebricTypeDeNoeud;
		s = s + ", " + valeurString+valeurBoolean+ valeurFloat;
		return s + ">";
	}

	/*
	affiche le noeud n d'un arbre syntaxique avec un niveau d'indentation depth,
	et appels récursifs sur les noeuds enfants de n à un niveau d'indendation depth+1
	 */
	public static void afficheNoeud(AlgebricNoeud n, int profondeur) {
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

	public float getValeurFloat() {
		return valeurFloat;
	}

	public void setValeurFloat(float valeurFloat) {
		this.valeurFloat = valeurFloat;
	}

	public List<AlgebricNoeud> getEnfants() {
		return enfants;
	}

	public void setTypeDeNoeud(AlgebricTypeDeNoeud algebricTypeDeNoeud) {
		this.algebricTypeDeNoeud = algebricTypeDeNoeud;
	}

	public void setEnfants(List<AlgebricNoeud> enfants) {
		this.enfants = enfants;
	}

	public AlgebricTypeDeNoeud getAlgebricTypeDeNoeud() {
		return algebricTypeDeNoeud;
	}

}
