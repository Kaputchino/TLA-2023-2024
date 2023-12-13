package tla;

import java.util.List;

public class AnalyqeSyntaxique {
    private int pos;
    private List<Token> tokens;

    public Noeud analyse(List<Token> tokens) throws Exception {
        pos = 0;
        this.tokens = tokens;
        return null;
    }

    private Noeud S(){
        return null;
    }
    private Noeud S_prime(){
        return null;
    }
    private Noeud Lieu(){
        return null;
    }
    private Noeud Proposition(){
        return null;
    }
    private Noeud P_prime(){
        return null;
    }
    private Noeud F(){
        return null;
    }
}
