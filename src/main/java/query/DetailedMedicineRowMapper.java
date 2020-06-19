package query;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class DetailedMedicineRowMapper implements RowMapper<Medicine> {
    @Override
    public Medicine mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Medicine(UUID.fromString(rs.getString("medicine_id")),
                rs.getString("medicine_name"),
                HasId.getById(Type.class, rs.getString("medicine_type")));
    }
}
