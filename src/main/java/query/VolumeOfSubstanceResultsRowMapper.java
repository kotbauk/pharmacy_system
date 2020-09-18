
package query;

import model.VolumeOfSubstanceResult;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VolumeOfSubstanceResultsRowMapper implements RowMapper<VolumeOfSubstanceResult> {
    @Override
    public VolumeOfSubstanceResult mapRow(ResultSet resultSet, int numRow) throws SQLException {
        return new VolumeOfSubstanceResult(resultSet.getInt("volume"));
    }
}
