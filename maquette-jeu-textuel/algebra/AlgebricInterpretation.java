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
		// pour les tests
		/**
		listVariables.put("a",1.0f);
		listVariables.put("b",4.0f);
		listVariables.put("c",12.0f);
		listFlags.put("tag1",false);
		listFlags.put("tag2",false);//**/

	}

	/*
	interprete le noeud n
	et appel récursif sur les noeuds enfants de n
	 */
	public Boolean interpreter(AlgebricNoeud n) {
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.equ)){
			float x = mathematicalInterpreter(n.enfant(0));
			float y = mathematicalInterpreter(n.enfant(1));
			boolean value = (x == y);
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
			return value;
		}

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
	public Float mathematicalInterpreter(AlgebricNoeud n){
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.floatVal)){
			//System.out.println(n.getValeurInt());
			return n.getValeurFloat();
		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.add)){
			//System.out.println(n.getValeurInt());
			n.setValeurFloat(mathematicalInterpreter(n.enfant(0)) + mathematicalInterpreter(n.enfant(1)));
			return n.getValeurFloat();
		}if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.sub)){
			//System.out.println(n.getValeurInt());
			return mathematicalInterpreter(n.enfant(0)) - mathematicalInterpreter(n.enfant(1));
		}if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.mul)){
			//System.out.println(n.getValeurInt());
			return mathematicalInterpreter(n.enfant(0)) * mathematicalInterpreter(n.enfant(1));
		}if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.div)){
			//System.out.println(n.getValeurInt());
			return mathematicalInterpreter(n.enfant(0)) / mathematicalInterpreter(n.enfant(1));
		}
		if(n.getTypeDeNoeud().equals(AlgebricTypeDeNoeud.statement)){
			//System.out.println(n.getValeurInt());
			n.setValeurFloat(mathematicalInterpreter(n.enfant(0)));
			return n.getValeurFloat();
		}
		//System.out.println(listVariables.get(n.getValeurString()));
		Setting st = ContenuAventure.settings.get(n.getValeurString());
		Item ob = (Item) st;
		return ob.getQuantity();

	}


}
