package query;

import model.HasId;
import model.Role;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(UUID.fromString(rs.getString("id")),
                HasId.getById(Role.class, rs.getString("role")),
                rs.getString("surname"),
                rs.getString("middlename"),
                rs.getString("name"));
    }
}
