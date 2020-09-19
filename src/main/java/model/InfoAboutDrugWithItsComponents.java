
package model;

public class InfoAboutDrugWithItsComponents {
    private String componentName;
    private double price;
    private int amount;

    public InfoAboutDrugWithItsComponents(String componentName, double price, int amount) {
        this.componentName = componentName;
        this.price = price;
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public String getComponentName() {
        return componentName;
    }

    public int getAmount() {
        return amount;
    }
}
