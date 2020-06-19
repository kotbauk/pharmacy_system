package query;

import model.HasId;
import model.Role;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TechnologistRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(UUID.fromString(rs.getString("technologist_id")),
                HasId.getById(Role.class, rs.getString("technologist_role")),
                rs.getString("technologist_login"),
                rs.getString("technologist_password"),
                rs.getString("technologist_surname"),
                rs.getString("technologist_middlename"),
                rs.getString("technologist_name"));
    }
}
