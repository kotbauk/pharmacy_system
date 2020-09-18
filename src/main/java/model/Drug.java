package model;

import java.util.UUID;

public class Drug {
    private UUID id;
    private String name;
    private String unit;
    private Type type;
    private int pricePerUnit;

    public Drug(UUID id, String unit, int pricePerUnit, String name, Type type) {
        this.id = id;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
        this.name = name;
        this.type = type;
    }

    public Drug() {

    }
    protected Drug(Drug drug) {
        this.id = drug.id;
        this.name = drug.name;
        this.unit = drug.unit;
        this.type = drug.type;
        this.pricePerUnit = drug.pricePerUnit;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Drug setName(String name) {
        this.name = name;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public Drug setType(Type type) {
        this.type = type;
        return this;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }
}
