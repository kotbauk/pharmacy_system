package model;

import java.util.UUID;

public class User {
    private final UUID id;
    final private Role role;
    final private String surname;
    final private String middleName;
    final private String name;

    public User(UUID id, Role role, String surname, String middleName, String name) {
        this.id = id;
        this.role = role;
        this.surname = surname;
        this.middleName = middleName;
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public UUID getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public String toString() {
        return surname + " " + name + " " + middleName;
    }
}
