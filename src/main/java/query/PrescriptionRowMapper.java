package query;

import model.Prescription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PrescriptionRowMapper implements RowMapper<Prescription> {
    @Override
    public Prescription mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Prescription(UUID.fromString(rs.getString("recipe_id")),
                new DetailedBuyerRowMapper().mapRow(rs, rowNum),
                rs.getString("recipe_diagnosis"),
                rs.getTimestamp("recipe_date_of_done").toInstant());
    }
}
