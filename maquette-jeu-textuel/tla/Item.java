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
        return "Item{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}

