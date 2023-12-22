package algebra;

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
                if(n.enfant(0).getTypeDeNoeud() == TypeDeNoeud.or && n.enfant(1).getTypeDeNoeud() == TypeDeNoeud.statement){
                    n.getEnfants().remove(0);
                    n.setTypeDeNoeud(TypeDeNoeud.or);
                }
            }
        }
        if(n.nombreEnfants() > 1){
            if (n.enfant(1).nombreEnfants() > 0){
                if(n.enfant(1).enfant(0).getTypeDeNoeud() == TypeDeNoeud.and){
                    n.setTypeDeNoeud(TypeDeNoeud.and);
                    n.enfant(1).getEnfants().remove(0);
                }
                if(n.enfant(1).enfant(0).getTypeDeNoeud() == TypeDeNoeud.or){
                    n.setTypeDeNoeud(TypeDeNoeud.or);
                    n.enfant(1).getEnfants().remove(0);
                }
            }
        }
        if(n.nombreEnfants() == 1){
            if(n.getTypeDeNoeud() == TypeDeNoeud.statement){
                if(n.enfant(0).getTypeDeNoeud() == TypeDeNoeud.statement){
                    n = n.enfant(0);
                }
            }
        }
        return n;
    }
}
