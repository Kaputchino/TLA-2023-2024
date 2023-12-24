package tla;

/*
 * Modeliste un effet
 */
public class Flag implements Setting {
    // FLAG-> - intval string < FLAG | epsilon

    int id;
    String name;

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
}
