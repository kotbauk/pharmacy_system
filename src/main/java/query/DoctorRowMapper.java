package query;

import model.HasId;
import model.Role;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DoctorRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(UUID.fromString(rs.getString("doctor_id")),
                HasId.getById(Role.class, rs.getString("doctor_role")),
                rs.getString("doctor_login"),
                rs.getString("doctor_password"),
                rs.getString("doctor_surname"),
                rs.getString("doctor_middlename"),
                rs.getString("doctor_name"));
    }
}
