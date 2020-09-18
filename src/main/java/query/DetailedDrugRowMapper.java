package query;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DetailedDrugRowMapper implements RowMapper<Drug> {
    @Override
    public Drug mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Drug(UUID.fromString(rs.getString("id_drug")),
                rs.getString("unit"),
                rs.getInt("pricePerUnit"),
                rs.getString("name"),
                HasId.getById(Type.class, rs.getString("drug_type")));

    }
}