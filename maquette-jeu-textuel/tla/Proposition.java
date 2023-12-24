package tla;

/*
 * Proposition faite au joueur pour poursuivre dans l'aventure.
 * 
 * Une proposition mène à un nouveau lieu, identifié par un numéro.
 */
public class Proposition {
    String texte;
    int numeroLieu;
    String condition = null;

    public Proposition(String texte, int numeroLieu) {
        this.texte = texte;
        this.numeroLieu = numeroLieu;
    }
}
