package tla;

/*
 * Modeliste un effet
 */
public class Flag implements Setting {
    // FLAG-> - intval string < FLAG | epsilon

    int id;
    String name;

    boolean value;

    public Flag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ": " + value;
    }

    @Override
    public float getQuantity() {
        if(value){
            return 1;
        }
        return 0;
    }

    public void setValue(float value) {
        int val = ((int)value)%2;
        this.value = val == 1;
    }

    @Override
    public void addValue(float value) {
        int x = 0;
        if(this.value){
            x = 1;
        }
        x = x + ((int)value)%2;
        if(x == 2){
            x = 0;
        }else if(x == -1){
            x = 1;
        }
        this.value = x == 1;
    }

    @Override
    public void subValue(float value) {
        addValue(-value);
    }
}
