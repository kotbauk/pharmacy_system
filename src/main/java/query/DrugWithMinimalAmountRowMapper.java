
package query;

import model.Drug;
import model.HasId;
import model.Type;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DrugWithMinimalAmountRowMapper implements RowMapper<Drug> {
    @Override
    public Drug mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Drug().
                setName(resultSet.getString("name")).
                setType(HasId.getById(Type.class, resultSet.getString("type")));
    }
}
