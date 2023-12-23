package algebra;

import java.util.Collections;

public class AlgebricSorter {
    public AlgebricNoeud sort(AlgebricNoeud n){
        for(int i = 0; i < n.nombreEnfants(); i++){
            n.getEnfants().set(i,sort(n.enfant(i)));
        }
        if(n.nombreEnfants() > 1){
            if(n.enfant(1).getTypeDeNoeud() == AlgebricTypeDeNoeud.inf || n.enfant(1).getTypeDeNoeud() == AlgebricTypeDeNoeud.sup
            || n.enfant(1).getTypeDeNoeud() == AlgebricTypeDeNoeud.equ || n.enfant(1).getTypeDeNoeud() == AlgebricTypeDeNoeud.or
                    || n.enfant(1).getTypeDeNoeud() == AlgebricTypeDeNoeud.and  ){
                n.enfant(1).ajout(n.enfant(0));
                n.getEnfants().remove(0);
                Collections.reverse(n.enfant(0).getEnfants());
            }
            if(n.nombreEnfants() > 1){
                if(n.enfant(0).getTypeDeNoeud() == AlgebricTypeDeNoeud.and && n.enfant(1).getTypeDeNoeud() == AlgebricTypeDeNoeud.statement){
                    n.getEnfants().remove(0);
                    n.setTypeDeNoeud(AlgebricTypeDeNoeud.and);
                }
                if(n.enfant(0).getTypeDeNoeud() == AlgebricTypeDeNoeud.or && n.enfant(1).getTypeDeNoeud() == AlgebricTypeDeNoeud.statement){
                    n.getEnfants().remove(0);
                    n.setTypeDeNoeud(AlgebricTypeDeNoeud.or);
                }
            }
        }
        if(n.nombreEnfants() > 1){
            if (n.enfant(1).nombreEnfants() > 0){
                if(n.enfant(1).enfant(0).getTypeDeNoeud() == AlgebricTypeDeNoeud.and){
                    n.setTypeDeNoeud(AlgebricTypeDeNoeud.and);
                    n.enfant(1).getEnfants().remove(0);
                }
                if(n.enfant(1).enfant(0).getTypeDeNoeud() == AlgebricTypeDeNoeud.or){
                    n.setTypeDeNoeud(AlgebricTypeDeNoeud.or);
                    n.enfant(1).getEnfants().remove(0);
                }
            }
        }
        if(n.nombreEnfants() == 1){
            if(n.getTypeDeNoeud() == AlgebricTypeDeNoeud.statement){
                if(n.enfant(0).getTypeDeNoeud() == AlgebricTypeDeNoeud.statement){
                    n = n.enfant(0);
                }
            }
        }
        return n;
    }
}
