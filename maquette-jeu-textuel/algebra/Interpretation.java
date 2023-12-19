package algebra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Interpretation {

	// permet la lecture de chaîne au clavier
	private static BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
	int sum = 0;
	private HashMap<String, Integer> listVariables = new HashMap<>();
	private HashMap<String, Boolean> listFlags = new HashMap<>();


	public Interpretation() {
		/* A COMPLETER */
	}

	/*
	interprete le noeud n
	et appel récursif sur les noeuds enfants de n
	 */
	public Boolean interpreter(Noeud n) {
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.statement)){
			for(int i = 0; i < n.nombreEnfants(); i++){
				interpreter(n.enfant(i));
			}
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.equ)){
			return mathematicalInterpreter(n.enfant(0)) == mathematicalInterpreter(n.enfant(1));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.sup)){
			return mathematicalInterpreter(n.enfant(0)) > mathematicalInterpreter(n.enfant(1));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.inf)){
			return mathematicalInterpreter(n.enfant(0)) < mathematicalInterpreter(n.enfant(1));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.or)){
			return interpreter(n.enfant(0)) || interpreter(n.enfant(1));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.inverse)){
			return !interpreter(n.enfant(0));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.and)){
			return interpreter(n.enfant(0)) && interpreter(n.enfant(1));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.ident)){
			return listFlags.get(n.getValeurString());
		}
		return null;
	}
	public int mathematicalInterpreter(Noeud n){
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.intVal)){
			return n.getValeurInt();
		}
		return listVariables.get(n.getValeurString());
	}


}
