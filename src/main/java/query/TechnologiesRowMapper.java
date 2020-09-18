
package query;

import model.Technologies;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TechnologiesRowMapper implements RowMapper<Technologies> {
    @Override
    public Technologies mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Technologies(UUID.fromString(rs.getString("id")),
                rs.getInt("production_time"),
                rs.getString("production"));
    }
}
