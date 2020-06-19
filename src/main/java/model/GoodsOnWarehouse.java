package model;

import java.util.UUID;

public class GoodsOnWarehouse implements DbEntity {
    private final UUID id;
    private final String unit;
    private final int priceForUnit;
    private final Medicine medicine;
    private final int medicineCount;

    public GoodsOnWarehouse(UUID id, String unit, int priceForUnit, Medicine medicine, int medicineCount) {
        this.id = id;
        this.unit = unit;
        this.priceForUnit = priceForUnit;
        this.medicine = medicine;
        this.medicineCount = medicineCount;
    }

    public UUID getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public int getPriceForUnit() {
        return priceForUnit;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public int getMedicineCount() {
        return medicineCount;
    }
}
