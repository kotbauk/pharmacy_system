
package query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AmountOfAllComponentsNeededForOrderRowMapper implements RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("amount");
    }
}
