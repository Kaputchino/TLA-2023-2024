package tla;

/*
 * Modeliste un effet
 */
public class Effect {
    String variable;
    String operation;
    int valeur;

    public Effect(String variable, String operation, int valeur) {
        this.variable = variable;
        this.operation = operation;
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "Effect{" +
                "variable='" + variable + '\'' +
                ", operation='" + operation + '\'' +
                ", valeur=" + valeur +
                '}';
    }
}
