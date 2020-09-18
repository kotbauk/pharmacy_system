
package model;

import java.util.UUID;

public class ReadyDrug extends Drug {
    public ReadyDrug(UUID id, String unit, int pricePerUnit, String name, Type type, int amount, int minimalAmount) {
        super(id, unit, pricePerUnit, name, type);
    }

    public ReadyDrug(Drug drug) {
        super(drug);
    }
}
