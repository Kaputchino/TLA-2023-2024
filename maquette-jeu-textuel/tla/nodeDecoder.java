package tla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class nodeDecoder {

    private HashMap<Integer, Lieu> lieux = new HashMap<>();
    private HashMap<String, Setting> settings = new HashMap<>();

    private String title;

    public HashMap<Integer, Lieu> getLieux() {
        return lieux;
    }

    public String getTitle() {
        return title;
    }

    public HashMap<String, Setting> getSettings() {
        return settings;
    }

    private nodeDecoder() {
        // this is very secret u can't use it ;c
    }

    public static nodeDecoder getHashMap(Noeud entryPoint) throws Exception {
        nodeDecoder hashmaps = new nodeDecoder();

        if (entryPoint.getTypeDeNoeud() == TypeDeNoeud.first) {
            if (entryPoint.enfant(0).getTypeDeNoeud() == TypeDeNoeud.string) {
                hashmaps.title = entryPoint.enfant(0).getValeur();
            } else {
                throw new Exception ("Titre attendu.");
            }

            if (entryPoint.enfant(1).getTypeDeNoeud() == TypeDeNoeud.param) {
                hashmaps.entryPointSetting(entryPoint.enfant(1));
            } else {
                throw new Exception ("Parametre attendue.");
            }

            if (entryPoint.enfant(2).getTypeDeNoeud() == TypeDeNoeud.lieuContainer) {
                hashmaps.entryPoint(entryPoint.enfant(2));
            } else {
                throw new Exception ("lieuContainer attendue.");
            }
        }else {
            throw new Exception ("Noeud first attendu.");
        }

        return hashmaps;
    }

    private void entryPointSetting(Noeud n) {
        Noeud stat = n.enfant(0);
        Noeud object = n.enfant(1);
        Noeud flag = n.enfant(2);

        traiterStats(stat);
        traiterObjects(object);
        traiterFlags(flag);
    }

    private void traiterStats(Noeud n) {
        if (n.nombreEnfants() >= 3) {
            int min = Integer.parseInt(n.enfant(0).getValeur());
            int def = Integer.parseInt(n.enfant(1).getValeur());
            int max = Integer.parseInt(n.enfant(2).getValeur());
            String name = n.enfant(3).getValeur();
            Setting stat = new Stat(min, def, max, name);
            settings.put(name, stat);

            if (n.nombreEnfants() == 5) {
                traiterStats(n.enfant(4));
            }
        }
    }

    private void traiterObjects(Noeud n) {
        if (n.nombreEnfants() >= 2) {
            int qte = Integer.parseInt(n.enfant(0).getValeur());
            String name = n.enfant(1).getValeur();
            Setting objet = new Item(qte, name);
            settings.put(name, objet);

            if (n.nombreEnfants() == 3) {
                traiterObjects(n.enfant(2));
            }
        }
    }

    private void traiterFlags(Noeud n) {
        if (n.nombreEnfants() >= 2) {
            int id = Integer.parseInt(n.enfant(0).getValeur());
            String name = n.enfant(1).getValeur();
            Setting flag = new Flag(id, name);
            settings.put(name, flag);

            if (n.nombreEnfants() == 3) {
                traiterFlags(n.enfant(2));
            }
        }
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

        for (int i = 0; i < n.nombreEnfants(); i++) {
            Noeud current = n.enfant(i);

            if (current.getTypeDeNoeud() == TypeDeNoeud.condition) {
                String statement = current.enfant(0).getValeur();
                statement = statement.replaceAll("`", "");
                statement = statement.trim();
                proposition.condition = statement;
            } else if (current.getTypeDeNoeud() == TypeDeNoeud.facultatif) {
                traiterFacultatif(n.enfant(i), proposition);
                return;
            } else if (current.getTypeDeNoeud() == TypeDeNoeud.effet) {
                String nom = current.enfant(0).getValeur();
                String operation = current.enfant(1).getValeur();
                int valeur = Integer.parseInt(current.enfant(2).getValeur());
                proposition.effets.add(new Effet(nom, operation, valeur));
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
