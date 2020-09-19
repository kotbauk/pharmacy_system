
package model;

import java.util.UUID;

public class Component {
    private final UUID id;
    private String name;
    private String unit;
    private Type type;
    private int pricePerUnit;

    public Component(UUID id, String name, String unit, Type type, int pricePerUnit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.type = type;
        this.pricePerUnit = pricePerUnit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public Type getType() {
        return type;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }
}
