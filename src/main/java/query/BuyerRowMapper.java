package query;

import model.Buyer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BuyerRowMapper implements RowMapper<Buyer> {
    @Override
    public Buyer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Buyer(UUID.fromString(rs.getString("id")),
                rs.getString("surname"),
                rs.getString("middlename"),
                rs.getString("name"),
                rs.getTimestamp("date_of_birth").toInstant(),
                rs.getLong("phone_number"),
                rs.getString("address"));
    }
}
