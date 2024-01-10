package tla;

/*
 * Modeliste un effet
 */
public class Effet {
    String variable;
    String operation;
    int valeur;

    public Effet(String variable, String operation, int valeur) {
        this.variable = variable;
        this.operation = operation;
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "Effet{" +
                "variable='" + variable + '\'' +
                ", operation='" + operation + '\'' +
                ", valeur=" + valeur +
                '}';
    }

}
