package model;

public enum GoodsType {
    MANUFACTURED_DRUG("MANUFACTURED_DRUG"),
    READY_DRUG("READY_DRUG"),
    REAGENT("REAGENT");

    private String typeName;

    private GoodsType(String typeName) {
        this.typeName = typeName;
    }
}
