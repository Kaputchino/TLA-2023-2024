package tla;

/*
 * Modeliste un effet
 */
public class Flag implements Setting {
    // FLAG-> - intval string < FLAG | epsilon

    int id;
    int name;

    public Flag(int id, int name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
