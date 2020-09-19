
package model;


public class InfoAboutDrug {
    private final Type type;
    private final String manufacturingAction;
    private final int pricePerUnit;
    private final int amountOnWarehouse;

    public InfoAboutDrug(Type type, String manufacturingAction, int pricePerUnit, int amountOnWarehouse) {
        this.type = type;
        this.manufacturingAction = manufacturingAction;
        this.pricePerUnit = pricePerUnit;
        this.amountOnWarehouse = amountOnWarehouse;
    }

    public Type getType() {
        return type;
    }

    public String getManufacturingAction() {
        return manufacturingAction;
    }

    public int getPricePerUnit() {
        return pricePerUnit;
    }

    public int getAmountOnWarehouse() {
        return amountOnWarehouse;
    }
}
