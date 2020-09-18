package query;

import model.GoodsOnWarehouse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GoodsOnWarehouseRowMapper implements RowMapper<GoodsOnWarehouse> {
    @Override
    public GoodsOnWarehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GoodsOnWarehouse(UUID.fromString(rs.getString("id_good")),
                rs.getInt("amount"),
                rs.getInt("minimal_amount"));
    }
}
