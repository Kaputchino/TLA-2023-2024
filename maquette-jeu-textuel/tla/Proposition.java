package tla;

import java.util.ArrayList;
import java.util.List;

/*
 * Proposition faite au joueur pour poursuivre dans l'aventure.
 * 
 * Une proposition mène à un nouveau lieu, identifié par un numéro.
 */
public class Proposition {
    String texte;
    int numeroLieu;
    String condition = null;

    List<Effet> effets;

    public Proposition(String texte, int numeroLieu) {
        this.texte = texte;
        this.numeroLieu = numeroLieu;
        this.effets = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Proposition{" +
                "texte='" + texte + '\'' +
                ", numeroLieu=" + numeroLieu +
                ", condition='" + condition + '\'' +
                ", effets=" + effets +
                '}';
    }
}
