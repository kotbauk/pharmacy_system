package model;

public enum Role implements HasId<String> {
    ADMIN("ADMIN"),
    PHARMACIST_SELLER("PHARMACIST_SELLER"),
    PHARMACIST_TECHNOLOGIST("PHARMACIST_TECHNOLOGIST"),
    HEAD_OF_PHARMACY("HEAD_OF_PHARMACY"),
    DOCTOR("DOCTOR");

    String id;

    Role(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
