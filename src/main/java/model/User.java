package model;

import java.util.UUID;

public class User implements DbEntity{
    final private UUID id;
    final private Role role;
    final private String login;
    final private String password;
    final private String surname;
    final private String middleName;
    final private String name;

    public User(UUID id, Role role, String login, String password, String surname, String middleName, String name) {
        this.id = id;
        this.role = role;
        this.login = login;
        this.password = password;
        this.surname = surname;
        this.middleName = middleName;
        this.name = name;
    }

    public User(User user){
        this(user.id, user.role, user.login, user.password, user.surname, user.middleName, user.name);
    }

    public Role getRole() {
        return role;
    }

    public UUID getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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
