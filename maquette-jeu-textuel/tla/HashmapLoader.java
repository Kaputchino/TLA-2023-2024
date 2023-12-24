package tla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashmapLoader {

    private HashMap<Integer, Lieu> lieux = new HashMap<>();
    private HashMap<String, Effect> settings = new HashMap<>();

    private String title;

    public HashMap<Integer, Lieu> getLieux() {
        return lieux;
    }

    private HashmapLoader() {
        // this is very secret u can't use it ;c
    }

    public static HashMap<Integer, Lieu> getHashMap(Noeud entryPoint) throws Exception {
        HashmapLoader lieuxHistoire = new HashmapLoader();

        if (entryPoint.getTypeDeNoeud() == TypeDeNoeud.first) {
            if (entryPoint.enfant(0).getTypeDeNoeud() == TypeDeNoeud.string) {
                lieuxHistoire.title = entryPoint.enfant(0).getValeur();
            } else {
                throw new Exception ("Titre attendu.");
            }

            if (entryPoint.enfant(1).getTypeDeNoeud() == TypeDeNoeud.param) {
                // traiter setting
            } else {
                throw new Exception ("Parametre attendue.");
            }

            if (entryPoint.enfant(2).getTypeDeNoeud() == TypeDeNoeud.lieuContainer) {
                lieuxHistoire.entryPoint(entryPoint.enfant(2));
            } else {
                throw new Exception ("lieuContainer attendue.");
            }
        }else {
            throw new Exception ("Noeud first attendu.");
        }


        // traitement des erreurs toussa toussa

        return lieuxHistoire.getLieux();
    }

    private List<Proposition> traiterProposition(Noeud n, List<Proposition> propositions) throws Exception {

        int numeroLieu = -1;
        String texte = null;
        Proposition proposition;

        numeroLieu = Integer.parseInt(n.enfant(0).getValeur());
        texte = n.enfant(1).getValeur();

        if (numeroLieu != -1 && texte != null) {
            proposition = new Proposition(texte, numeroLieu);
            propositions.add(proposition);

            if (n.nombreEnfants() == 3) {
                if (n.enfant(2).getTypeDeNoeud() == TypeDeNoeud.facultatif) {
                    traiterFacultatif(n.enfant(2), proposition);
                    return propositions;
                } else if (n.enfant(2).getTypeDeNoeud() == TypeDeNoeud.proposition) {
                    return traiterProposition(n.enfant(2), propositions);
                }

            }

            return propositions;
        }


        throw new Exception("Prosition non complete.");
    }

    private void traiterFacultatif(Noeud n, Proposition proposition) throws Exception {

        Noeud premierEnfant = n.enfant(0);
        if (premierEnfant.getTypeDeNoeud() == TypeDeNoeud.cond) {
                String statement = premierEnfant.enfant(0).getValeur();
                statement = statement.replaceAll("`", "");
                statement = statement.trim();
                proposition.condition = statement;

                // traitement du reste du facultatif
                if (n.nombreEnfants() == 2) {
                    traiterFacultatif(n.enfant(1), proposition);
                    return;
                }
        } else if (premierEnfant.getTypeDeNoeud() == TypeDeNoeud.facultatif) {
            traiterFacultatif(premierEnfant, proposition);
        } else if (premierEnfant.getTypeDeNoeud() == TypeDeNoeud.effet) {
            String nom = premierEnfant.enfant(0).getValeur();
            String operation = premierEnfant.enfant(1).getValeur();
            int valeur = Integer.parseInt(premierEnfant.enfant(2).getValeur());
            proposition.effects.add(new Effect(nom, operation, valeur));

            if (n.nombreEnfants() == 2) {
                traiterFacultatif(n.enfant(1), proposition);
            }
        }


    }

    private void entryPoint(Noeud n) throws Exception {
        for (int i = 0; i < n.nombreEnfants(); i++) {
            Noeud enfant = n.enfant(i);

            if (enfant.getTypeDeNoeud() == TypeDeNoeud.lieu) {
                traiterLieu(enfant);
            } else if (enfant.getTypeDeNoeud() == TypeDeNoeud.lieuContainer) {
                entryPoint(enfant);
            } else {
                throw new Exception("Noeud inconnu.");
            }
        }
    }


    private void traiterLieu(Noeud n) throws Exception {
        int numeroLieu = - 1;
        String texte = null;
        ArrayList<Proposition> propositions = new ArrayList<>();

        numeroLieu = Integer.parseInt(n.enfant(0).getValeur());
        texte = n.enfant(1).getValeur();


        if (  n.enfant(2).getTypeDeNoeud() == TypeDeNoeud.proposition) {
            propositions = (ArrayList<Proposition>) traiterProposition(n.enfant(2), new ArrayList<Proposition>());
        } else {
            throw new Exception("Noeud inconnu dans un noeud de type lieu.");
        }

        if (numeroLieu != -1 && texte != null) {
            lieux.put(
                    numeroLieu, new Lieu(texte, propositions)
            );
        } else {
            System.out.println(n);
            System.out.println(texte);
            System.out.println(numeroLieu);
            throw new Exception("Lieu incomplet");
        }
    }

}
