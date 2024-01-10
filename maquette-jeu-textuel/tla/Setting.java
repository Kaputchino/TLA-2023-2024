package tla;

/*
 * Modeliste un effet
 */
public interface Setting {
    float getQuantity();
    void setValue(float value);
    void addValue(float value);
    void subValue(float value);
}
