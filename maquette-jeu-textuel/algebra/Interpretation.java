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

	public Interpretation() {
		/* A COMPLETER */
	}

	/*
	interprete le noeud n
	et appel récursif sur les noeuds enfants de n
	 */
	public Integer interpreter(Noeud n) {

		if(n.getTypeDeNoeud().equals(TypeDeNoeud.statement)){
			for(int i = 0; i < n.nombreEnfants(); i++){

				interpreter(n.enfant(i));


			}
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.kPrint)){
			System.out.println(interpreter(n.enfant(0)));
			return null;
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.intVal)){
			return Integer.parseInt(n.getValeur());
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.add)){
			return interpreter(n.enfant(0)) + interpreter(n.enfant(1));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.multiply)){
			return interpreter(n.enfant(0)) * interpreter(n.enfant(1));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.kPow)){
			return (int)Math.pow(interpreter(n.enfant(0)),interpreter(n.enfant(1)));
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.kInput)){
			System.out.print("un nb svp: ");
			try {
				String variable = n.enfant(0).getValeur();
				String str = stdinReader.readLine();
				listVariables.put(variable,Integer.parseInt(str));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if(n.getTypeDeNoeud().equals(TypeDeNoeud.ident)){
			return listVariables.get(n.getValeur());
		}

		return null;
		/* A COMPLETER */
	}
	public Integer calculateAdd(Noeud n) throws Exception {
		if(n.nombreEnfants() < 2){
			throw new Exception();
		}
		if(n.enfant(0).getTypeDeNoeud().equals(TypeDeNoeud.intVal)){
			if(n.enfant(1).getTypeDeNoeud().equals(TypeDeNoeud.intVal)){
				return Integer.parseInt(n.enfant(0).getValeur()) + Integer.parseInt(n.enfant(1).getValeur());
			}
		}
		System.out.println("problem");
		return -1;
	}

	public Integer calculateMultiply(Noeud n) throws Exception {
		if(n.nombreEnfants() < 2){
			throw new Exception();
		}
		if(n.enfant(0).getTypeDeNoeud().equals(TypeDeNoeud.intVal)){
			if(n.enfant(1).getTypeDeNoeud().equals(TypeDeNoeud.intVal)){
				return Integer.parseInt(n.enfant(0).getValeur()) * Integer.parseInt(n.enfant(1).getValeur());
			}
		}
		System.out.println("problem");
		return -1;
	}

}
