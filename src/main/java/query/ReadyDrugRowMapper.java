
package query;

import model.ReadyDrug;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadyDrugRowMapper implements RowMapper<ReadyDrug> {
    @Override
    public ReadyDrug mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ReadyDrug(new DetailedDrugRowMapper().mapRow(rs, rowNum));
    }
}
