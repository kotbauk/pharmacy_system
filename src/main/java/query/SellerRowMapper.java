package query;

import model.HasId;
import model.Role;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SellerRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(UUID.fromString(rs.getString("seller_id")),
                HasId.getById(Role.class, rs.getString("seller_role")),
                rs.getString("seller_surname"),
                rs.getString("seller_middlename"),
                rs.getString("seller_name"));
    }
}
