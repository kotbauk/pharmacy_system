package model;

import java.util.UUID;

public class Medicine implements DbEntity {
    private final UUID id;
    private final String name;
    private final Type type;

    public Medicine(UUID id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
