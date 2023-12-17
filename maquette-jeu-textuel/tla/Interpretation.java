package tla;

import java.util.HashMap;

public class Interpretation {
    private HashMap<String, Lieu> lieux = new HashMap<String, Lieu>();

    public Interpretation(HashMap<String, Lieu> lieux) {
        this.lieux = lieux;
    }

    /*
     * interprete le noeud n
     * et appel récursif sur les noeuds enfants de n
     */
    public String interpreter(Noeud n) throws Exception {
        switch (n.getTypeDeNoeud()) {
            case TypeDeNoeud.intVal: // pas oublier de cast en int
                return n.getValeur();
            case TypeDeNoeud.proposition:
                return interpreter(n.enfant(0));
            case TypeDeNoeud.lieu:
                System.out.println(lieux.get(n.getValeur()).description); // à traduire en swing

                for (Proposition proposition : lieux.get(n.getValeur()).propositions) {
                    System.out.println(proposition.texte);
                }

                // faire un système de choix en swing

                return null;
            case TypeDeNoeud.string:
                return n.getValeur();
            case TypeDeNoeud.facultatif: // pas compris
                return null;
            default:
                throw new Exception();
        }
    }
}
