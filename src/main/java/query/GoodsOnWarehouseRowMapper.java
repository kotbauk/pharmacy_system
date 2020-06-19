package query;

import model.GoodsOnWarehouse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GoodsOnWarehouseRowMapper implements RowMapper<GoodsOnWarehouse> {
    @Override
    public GoodsOnWarehouse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new GoodsOnWarehouse(UUID.fromString(rs.getString("warehouse_id")),
                rs.getString("warehouse_unit"),
                rs.getInt("warehouse_prise"),
                new DetailedMedicineRowMapper().mapRow(rs, rowNum),
                rs.getInt("warehouse_medicine_count"));
    }
}
