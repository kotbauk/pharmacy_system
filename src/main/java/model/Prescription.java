package model;

import java.time.Instant;
import java.util.UUID;

public class Prescription {
    private final UUID id;
    private final Buyer buyer;
    private final String diagnosis;
    private final Drug drug;
    private final String doctorName;
    private final int amountDrug;

    public Prescription(UUID id, Buyer buyer, String diagnosis, Drug drug, String doctorName, int amountDrug) {
        this.id = id;
        this.buyer = buyer;
        this.diagnosis = diagnosis;
        this.drug = drug;
        this.doctorName = doctorName;
        this.amountDrug = amountDrug;

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


    @Override
    public String toString() {
        return id.toString() + " for " + buyer;
    }
}
