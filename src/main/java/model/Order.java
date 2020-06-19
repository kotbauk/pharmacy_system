package model;

import java.time.Instant;
import java.util.UUID;

public class Order implements DbEntity {
    private final UUID id;
    private final User seller;
    private final User technologist;
    private final Recipe recipe;
    private final Instant dateOfOrder;
    private final Instant dateOfManufacturing;
    private final Instant dateOfReceive;

    public Order(UUID id, User seller, User technologist, Recipe recipe, Instant dateOfOrder, Instant dateOfManufacturing, Instant dateOfReceive) {
        this.id = id;
        this.seller = seller;
        this.technologist = technologist;
        this.recipe = recipe;
        this.dateOfOrder = dateOfOrder;
        this.dateOfManufacturing = dateOfManufacturing;
        this.dateOfReceive = dateOfReceive;
    }

    public UUID getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public User getTechnologist() {
        return technologist;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Instant getDateOfOrder() {
        return dateOfOrder;
    }

    public Instant getDateOfManufacturing() {
        return dateOfManufacturing;
    }

    public Instant getDateOfReceive() {
        return dateOfReceive;
    }
}
