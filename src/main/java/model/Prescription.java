package model;

import java.time.Instant;
import java.util.UUID;

public class Prescription {
    private final UUID id;
    private final Buyer buyer;
    private final User doctor;
    private final String diagnosis;
    private final Instant dateOfDone;
    private final Drug drug;

    public Prescription(UUID id, Buyer buyer, User doctor, String diagnosis, Instant dateOfDone, Drug drug) {
        this.id = id;
        this.buyer = buyer;
        this.doctor = doctor;
        this.diagnosis = diagnosis;
        this.dateOfDone = dateOfDone;
        this.drug = drug;
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

    public Drug getMedicine() {
        return drug;
    }

    @Override
    public String toString() {
        return id.toString() + " for " + buyer;
    }
}
