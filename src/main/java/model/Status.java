
package model;

public enum  Status implements HasId<String> {
    AWAITING("AWAITING"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    String id;

    Status(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
