
package query;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountOfBuyerRowMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("count_of_buyer");
    }
}
