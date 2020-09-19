
package model;

import java.util.UUID;

public class DrugsWithAmountResults {
    private Drug drug;
    private int amount;

    public DrugsWithAmountResults(Drug drug, int amount) {
        this.drug = drug;
        this.amount = amount;
    }
    public UUID getId() {
        return this.drug.getId();
    }

    public String getName() {
        return this.drug.getName();
    }

    public Type getType() {
        return this.drug.getType();
    }

    public String getUnit() {
        return this.drug.getUnit();
    }

    public int getPricePerUnit() {
        return this.drug.getPricePerUnit();
    }
    public int getAmount() {
        return this.amount;
    }
}
