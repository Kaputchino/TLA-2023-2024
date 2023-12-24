package tla;

/*
 * Modeliste un effet
 */
public class Object implements Setting {
    // OBJET-> - intval string < OBJET | epsilon

    int quantity;
    String name;

    public Object(int quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
        return "Object{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}

