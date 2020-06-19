package model;

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
}
