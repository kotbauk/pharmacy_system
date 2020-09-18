package model;

import java.time.Instant;
import java.util.UUID;

public class Buyer {
    final private UUID id;
    final private String surname;
    final private String middleName;
    final private String name;
    final private Instant dateOfBirth;
    final private Long phoneNumber;
    final private String address;

    public Buyer(UUID id, String surname, String middleName, String name, Instant dateOfBirth, Long phoneNumber, String adress) {
        this.id = id;
        this.surname = surname;
        this.middleName = middleName;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = adress;
    }

    @Override
    public String toString() {
        return surname + " " + name + " " + middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getName() {
        return name;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }
}
