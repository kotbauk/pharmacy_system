
package query;

import model.ManufacturedDrug;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManufacturedDrugRowMapper implements RowMapper<ManufacturedDrug> {
    @Override
    public ManufacturedDrug mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ManufacturedDrug(new DetailedDrugRowMapper().mapRow(rs, rowNum));
    }
}
