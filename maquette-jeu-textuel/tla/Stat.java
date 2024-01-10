package tla;

import java.util.Set;

/*
 * Modeliste un effet
 */
public class Stat implements Setting {
    // STAT-> - intval intval intval string < STAT | epsilon
    int min;
    int value;
    int max;
    String name;

    public Stat(int min, int value, int max, String name) {
        this.min = min;
        this.value = value;
        this.max = max;
        this.name = name;
    }

    public int getMin() {
        return min;
    }

    @Override
    public String toString() {
        return name + ": " + value + " (min: " + min + ", max: " + max + ")";
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getvalue() {
        return value;
    }

    public void setvalue(int value) {
        this.value = value;
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
        return getvalue();
    }

    @Override
    public void setValue(float value) {
        value = (int) value;
    }

    @Override
    public void addValue(float value) {
        this.value += (int)value;
    }

    @Override
    public void subValue(float value) {
        addValue(-value);
        if(value < 0){
            value = 0;
        }
    }
}
