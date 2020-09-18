package query;

import model.HasId;
import model.Order;
import model.Status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timestamp dateOfManufacturing, dateOfReceive;
        return new Order(UUID.fromString(rs.getString("order_id")),
                new SellerRowMapper().mapRow(rs, rowNum),
                new TechnologistRowMapper().mapRow(rs, rowNum),
                HasId.getById(Status.class, rs.getString("status")),
                (dateOfManufacturing = rs.getTimestamp("order_date_of_order")) == null ? null : dateOfManufacturing.toInstant(),
                (dateOfReceive = rs.getTimestamp("order_date_of_manufacturing")) == null ? null : dateOfReceive.toInstant(),
                rs.getTimestamp("order_date_of_receive").toInstant());
    }
}
