package model;

import java.time.Instant;
import java.util.UUID;

public class Recipe implements DbEntity {
    private final UUID id;
    private final Buyer buyer;
    private final User doctor;
    private final String diagnosis;
    private final Instant dateOfDone;
    private final Medicine medicine;

    public Recipe(UUID id, Buyer buyer, User doctor, String diagnosis, Instant dateOfDone, Medicine medicine) {
        this.id = id;
        this.buyer = buyer;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.dateOfDone = dateOfDone;
        this.medicine = medicine;
    }

    public UUID getId() {
        return id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public User getDoctor() {
        return doctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Instant getDateOfDone() {
        return dateOfDone;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    @Override
    public String toString() {
        return id.toString() + " for " + buyer;
    }
}
