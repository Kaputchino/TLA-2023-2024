package algebra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Interpretation {

	// permet la lecture de chaîne au clavier
	private static BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
	private HashMap<String, Integer> listVariables = new HashMap<>();
	private HashMap<String, Boolean> listFlags = new HashMap<>();

	public Interpretation() {
		listVariables.put("a",1);
		listVariables.put("b",4);
		listVariables.put("c",12);
		listFlags.put("tag1",false);
		listFlags.put("tag2",false);

	}

	/*
	interprete le noeud n
	et appel récursif sur les noeuds enfants de n
	 */
	public Boolean interpreter(Noeud n) {
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.equ)){
			boolean value = mathematicalInterpreter(n.enfant(0)) == mathematicalInterpreter(n.enfant(1));
			n.setValeurBoolean(value);
			return value;
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.sup)){
			boolean value = mathematicalInterpreter(n.enfant(0)) > mathematicalInterpreter(n.enfant(1));
			n.setValeurBoolean(value);
			return value;		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.inf)){
			boolean value = mathematicalInterpreter(n.enfant(0)) < mathematicalInterpreter(n.enfant(1));
			n.setValeurBoolean(value);
			return value;		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.or)){
			boolean value1 = interpreter(n.enfant(0));
			boolean value2 = interpreter(n.enfant(1));
			boolean value = value1 || value2;
			n.setValeurBoolean(value);
			return value ;
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.inverse)){
			boolean value = !interpreter(n.enfant(0));
			n.setValeurBoolean(value);
			return value;
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.and)){
			boolean value1 = interpreter(n.enfant(0));
			boolean value2 = interpreter(n.enfant(1));
			boolean value = value1 && value2;
			n.setValeurBoolean(value);
			return value ;
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.ident)){
			boolean value = listFlags.get(n.getValeurString());
			n.setValeurBoolean(value);
			return value;
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.statement)){
			boolean value = interpreter(n.enfant(0));
			n.setValeurBoolean(value);
			return value;
		}
		return interpreter(n.enfant(0));
	}
	public int mathematicalInterpreter(Noeud n){
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.intVal)){
			//System.out.println(n.getValeurInt());
			return n.getValeurInt();
		}
		//System.out.println(listVariables.get(n.getValeurString()));
		return listVariables.get(n.getValeurString());
	}


}
