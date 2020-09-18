
package query;

import model.HasId;
import model.InfoAboutDrug;
import model.Type;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoAboutDrugRowMapper implements RowMapper<InfoAboutDrug> {

    @Override
    public InfoAboutDrug mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new InfoAboutDrug(HasId.getById(Type.class, rs.getString("type")),
                rs.getString("action"),
                rs.getInt("price"),
                rs.getInt("amount"));
    }
}
