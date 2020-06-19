package query;

import java.sql.ResultSet;
import java.sql.SQLException;

interface RowMapper<T> {
    T mapRow(ResultSet rs, int rowNum) throws SQLException;
}
