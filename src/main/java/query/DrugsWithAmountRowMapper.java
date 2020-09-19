
package query;

import model.DrugsWithAmountResults;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DrugsWithAmountRowMapper implements RowMapper<DrugsWithAmountResults> {
    @Override
    public DrugsWithAmountResults mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DrugsWithAmountResults(new DetailedDrugRowMapper().mapRow(rs, rowNum),
                rs.getInt("amount"));
    }
}
