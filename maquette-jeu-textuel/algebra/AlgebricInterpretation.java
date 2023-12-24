package algebra;

import tla.ContenuAventure;
import tla.Flag;
import tla.Item;
import tla.Setting;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AlgebricInterpretation {

	// permet la lecture de chaîne au clavier
	private static BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

	public AlgebricInterpretation() {
		/** pour les tests
		listVariables.put("a",1);
		listVariables.put("b",4);
		listVariables.put("c",12);
		listFlags.put("tag1",false);
		listFlags.put("tag2",false);**/

	}

	/*
	interprete le noeud n
	et appel récursif sur les noeuds enfants de n
	 */
	public Boolean interpreter(AlgebricNoeud n) {
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.equ)){
			boolean value = mathematicalInterpreter(n.enfant(0)) == mathematicalInterpreter(n.enfant(1));
			n.setValeurBoolean(value);
			return value;
		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.sup)){
			boolean value = mathematicalInterpreter(n.enfant(0)) > mathematicalInterpreter(n.enfant(1));
			n.setValeurBoolean(value);
			return value;		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.inf)){
			boolean value = mathematicalInterpreter(n.enfant(0)) < mathematicalInterpreter(n.enfant(1));
			n.setValeurBoolean(value);
			return value;		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.or)){
			boolean value1 = interpreter(n.enfant(0));
			boolean value2 = interpreter(n.enfant(1));
			boolean value = value1 || value2;
			n.setValeurBoolean(value);
			return value ;
		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.inverse)){
			boolean value = !interpreter(n.enfant(0));
			n.setValeurBoolean(value);
			return value;
		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.and)){
			boolean value1 = interpreter(n.enfant(0));
			boolean value2 = interpreter(n.enfant(1));
			boolean value = value1 && value2;
			n.setValeurBoolean(value);
			return value ;
		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.ident)){
			Setting st =ContenuAventure.settings.get(n.getValeurString());
			Flag flag = (Flag)st;
			boolean value = flag.isValue();
			n.setValeurBoolean(value);
			return value;
		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.statement)){
			boolean value = interpreter(n.enfant(0));
			n.setValeurBoolean(value);
			return value;
		}
		return interpreter(n.enfant(0));
	}
	public int mathematicalInterpreter(AlgebricNoeud n){
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.intVal)){
			//System.out.println(n.getValeurInt());
			return n.getValeurInt();
		}
		//System.out.println(listVariables.get(n.getValeurString()));
		Setting st = ContenuAventure.settings.get(n.getValeurString());
		Item ob = (Item) st;
		return ob.getQuantity();
	}


}
