package model;

import java.util.UUID;

public class Technologies {
    private final UUID id;
    private int productionTime;
    private String productionAction;

    public UUID getId() {
        return id;
    }

    public int getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(int productionTime) {
        this.productionTime = productionTime;
    }

    public String getProductionAction() {
        return productionAction;
    }

    public void setProductionAction(String productionAction) {
        this.productionAction = productionAction;
    }

    public Technologies(UUID idSales, int productionTime, String productionAction) {
        this.id = idSales;
        this.productionTime = productionTime;
        this.productionAction = productionAction;
    }
}
