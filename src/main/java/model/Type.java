package model;

import java.util.Arrays;
import java.util.List;

public enum Type implements HasId<String> {
    PILLS("PILLS"),
    OINTMENTS("OINTMENTS"),
    TINCTURES("TINCTURES"),
    POTIONS("POTIONS"),
    POWDERS("POWDERS"),
    SOLUTE("SOLUTE");

    String id;

    Type(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public static List<Type> getTypesOfReadyDrug() {
        return Arrays.asList(Type.PILLS, Type.TINCTURES, Type.OINTMENTS);
    }

    public static List<Type> getTypesOfManufacturedDrug() {
        return Arrays.asList(Type.SOLUTE, Type.OINTMENTS, Type.POWDERS, Type.POTIONS);
    }

}
