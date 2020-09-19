
package query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DrugPriceRowMapper implements RowMapper<Double> {
    @Override
    public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getDouble("price");
    }
}
