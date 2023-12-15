import tla.Lieu;
import tla.Noeud;
import tla.Proposition;
import tla.TypeDeNoeud;

import java.util.ArrayList;
import java.util.HashMap;

public class HashmapLoader {

    private HashMap<Integer, Lieu> lieux = new HashMap<>();

    public HashMap<Integer, Lieu> getLieux() {
        return lieux;
    }

    public static HashMap<Integer, Lieu> getHashMap(Noeud noeudHistoire) throws Exception {
        HashmapLoader lieuxHistoire = new HashmapLoader();
        lieuxHistoire.entryPoint(noeudHistoire);

        // traitement des erreurs toussa toussa

        return lieuxHistoire.getLieux();
    }

    // noeud histoire -> decompose en lieu
    public void entryPoint(Noeud n) throws Exception {

        if (n.getTypeDeNoeud() != TypeDeNoeud.histoire) {
            throw new Exception("Noeud racine inconnu.");
        }

        for (int i = 0; i < n.nombreEnfants(); i++) {
            if (n.getTypeDeNoeud() != TypeDeNoeud.lieu) {
                throw new Exception("Erreur. Noeud lieu attendu");
            }

            traiterLieu(n);
        }
    }

    private Proposition traiterProposition(Noeud n) throws Exception {
        int numeroLieu = -1;
        String texte = null;

        for (int i = 0; i < n.nombreEnfants(); i++) {
            Noeud enfant = n.enfant(i);

            if (enfant.getTypeDeNoeud() == TypeDeNoeud.intVal) {
                numeroLieu = Integer.parseInt(enfant.getValeur());
            } else if (enfant.getTypeDeNoeud() == TypeDeNoeud.string) {
                texte = enfant.getValeur();
            }

        }

        if (numeroLieu != -1 && texte != null) {
            return new Proposition(texte, numeroLieu);
        }

        throw new Exception("Prosition non complete.");
    }


    private Noeud traiterLieu(Noeud n) throws Exception {
        int numeroLieu = - 1;
        String texte = null;
        ArrayList<Proposition> propositions = new ArrayList<>();

        for (int i = 0; i < n.nombreEnfants(); i++) {
            Noeud enfant = n.enfant(i);

            if (enfant.getTypeDeNoeud() == TypeDeNoeud.intVal) {
                numeroLieu = Integer.parseInt(enfant.getValeur());
            } else if (enfant.getTypeDeNoeud() == TypeDeNoeud.string) {
                texte = enfant.getValeur();
            } else if (enfant.getTypeDeNoeud() == TypeDeNoeud.proposition) {
                propositions.add(
                        traiterProposition(enfant)
                );
            } else {
                throw new Exception("Noeud inconnu dans un noeud de type lieu.");
            }
        }

        if (numeroLieu != -1 && texte != null) {
            lieux.put(
                    numeroLieu, new Lieu(texte, propositions)
            );
        }

        throw new Exception("Lieu incomplet");
    }

}
