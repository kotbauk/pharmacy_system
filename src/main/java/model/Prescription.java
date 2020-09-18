package model;

import java.time.Instant;
import java.util.UUID;

public class Prescription {
    private final UUID id;
    private final Buyer buyer;
    private final String diagnosis;
    private final Instant dateOfDone;

    public Prescription(UUID id, Buyer buyer, String diagnosis, Instant dateOfDone, Drug drug) {
        this.id = id;
        this.buyer = buyer;
        this.diagnosis = diagnosis;
        this.dateOfDone = dateOfDone;
    }

    public UUID getId() {
        return id;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public Instant getDateOfDone() {
        return dateOfDone;
    }

    @Override
    public String toString() {
        return id.toString() + " for " + buyer;
    }
}
