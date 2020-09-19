package model;

import java.sql.Timestamp;
import java.util.UUID;

public class Order {
    private final UUID id;
    private final User seller;
    private final User technologist;
    private final Status status;
    private final Timestamp dateOfOrder;
    private final Timestamp dateOfManufacturing;
    private final Timestamp dateOfReceive;

    public Order(UUID id, User seller, User technologist, Status status, Timestamp dateOfOrder, Timestamp dateOfManufacturing, Timestamp dateOfReceive) {
        this.id = id;
        this.seller = seller;
        this.technologist = technologist;
        this.status = status;
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

    public Timestamp getDateOfOrder() {
        return dateOfOrder;
    }

    public Status getStatus() {return status;}

    public Timestamp getDateOfManufacturing() {
        return dateOfManufacturing;
    }

    public Timestamp getDateOfReceive() {
        return dateOfReceive;
    }
}
