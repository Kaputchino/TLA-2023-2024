package tla;

public class Token {

    private TypeDeToken typeDeToken;
    private String valeur;

    public Token(TypeDeToken typeDeToken, String value) {
        this.typeDeToken = typeDeToken;
        this.valeur = value;
    }

    public Token(TypeDeToken typeDeToken) {
        this.typeDeToken = typeDeToken;
    }

    public TypeDeToken getTypeDeToken() {
        return typeDeToken;
    }

    public String getValeur() {
        return valeur;
    }

    public String toString() {
        return "TODO";
    }
}
