package query;

import model.Buyer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DetailedBuyerRowMapper implements RowMapper<Buyer>{
    @Override
    public Buyer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Buyer(UUID.fromString(rs.getString("buyer_id")),
                rs.getString("buyer_surname"),
                rs.getString("buyer_middlename"),
                rs.getString("buyer_name"),
                rs.getTimestamp("buyer_date_of_birth").toInstant(),
                rs.getLong("buyer_phone_number"),
                rs.getString("buyer_address"));
    }
}
