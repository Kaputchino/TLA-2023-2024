package tla;

import java.util.Set;

/*
 * Modeliste un effet
 */
public class Stat implements Setting {
    // STAT-> - intval intval intval string < STAT | epsilon
    int min;
    int def;
    int max;
    String name;

    public Stat(int min, int def, int max, String name) {
        this.min = min;
        this.def = def;
        this.max = max;
        this.name = name;
    }

    public int getMin() {
        return min;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "min=" + min +
                ", def=" + def +
                ", max=" + max +
                ", name='" + name + '\'' +
                '}';
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public float getQuantity() {
        return getDef();
    }
}
