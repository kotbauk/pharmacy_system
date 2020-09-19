
package query;

import model.InfoAboutDrugWithItsComponents;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoAboutDrugWithItsComponentsRowMapper implements RowMapper<InfoAboutDrugWithItsComponents> {
    @Override
    public InfoAboutDrugWithItsComponents mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new InfoAboutDrugWithItsComponents(rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("amount"));
    }
}
