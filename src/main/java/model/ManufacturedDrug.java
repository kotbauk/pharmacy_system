
package model;

import java.util.UUID;

public class ManufacturedDrug extends Drug {

    public ManufacturedDrug(UUID id, String unit, int pricePerUnit, String name, Type type, int amount, int minimalAmount) {
        super(id, unit, pricePerUnit, name, type);
    }

    public ManufacturedDrug(Drug drug) {
        super(drug);
    }
}
