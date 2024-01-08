package tla;

/*
 * Modeliste un effet
 */
public class Item implements Setting {
    // OBJET-> - intval string < OBJET | epsilon

    float quantity;
    String name;

    public Item(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    @Override
    public void setValue(float value) {
        quantity = value;
    }

    @Override
    public void addValue(float value) {
        quantity += value;
    }

    @Override
    public void subValue(float value) {
        addValue(-value);
        if(quantity < 0){
            quantity = 0;
        }
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " x " + quantity;
    }
}

