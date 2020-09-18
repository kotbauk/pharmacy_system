
package model;

import java.util.List;

public class InfoAboutDrug {
    private final Type type;
    private final String manufacturingAction;
    private List<String> componentsName;
    private final int pricePerUnit;
    private final int amountOnWarehouse;


    public InfoAboutDrug(Type type, String manufacturingAction, List<String> componentsName, int pricePerUnit, int amountOnWarehouse) {
        this.type = type;
        this.manufacturingAction = manufacturingAction;
        this.componentsName = componentsName;
        this.pricePerUnit = pricePerUnit;
        this.amountOnWarehouse = amountOnWarehouse;
    }

    public InfoAboutDrug(Type type, String manufacturingAction, int pricePerUnit, int amountOnWarehouse) {
        this.type = type;
        this.manufacturingAction = manufacturingAction;
        this.pricePerUnit = pricePerUnit;
        this.amountOnWarehouse = amountOnWarehouse;
    }
}
