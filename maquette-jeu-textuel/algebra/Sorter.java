package algebra;

import java.util.Collection;
import java.util.Collections;

public class Sorter {
    public Noeud sort(Noeud n){
        for(int i = 0; i < n.nombreEnfants(); i++){
            n.getEnfants().set(i,sort(n.enfant(i)));
        }
        if(n.nombreEnfants() > 1){
            if(n.enfant(1).getTypeDeNoeud() == TypeDeNoeud.inf || n.enfant(1).getTypeDeNoeud() == TypeDeNoeud.sup
            || n.enfant(1).getTypeDeNoeud() == TypeDeNoeud.equ || n.enfant(1).getTypeDeNoeud() == TypeDeNoeud.or
                    || n.enfant(1).getTypeDeNoeud() == TypeDeNoeud.and  ){
                n.enfant(1).ajout(n.enfant(0));
                n.getEnfants().remove(0);
                Collections.reverse(n.enfant(0).getEnfants());
            }
            if(n.nombreEnfants() > 1){
                if(n.enfant(0).getTypeDeNoeud() == TypeDeNoeud.and && n.enfant(1).getTypeDeNoeud() == TypeDeNoeud.statement){
                    n.getEnfants().remove(0);
                    n.setTypeDeNoeud(TypeDeNoeud.and);
                }
            }
        }

        return n;
    }
}
