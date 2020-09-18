
package query;

import model.Component;
import model.HasId;
import model.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ComponentRowMapper implements RowMapper<Component> {
    @Override
    public Component mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Component(UUID.fromString(resultSet.getString("id")),
                resultSet.getString("name"),
                resultSet.getString("unit"),
                HasId.getById(Type.class, resultSet.getString("type")),
                resultSet.getInt("price"));
    }
}
