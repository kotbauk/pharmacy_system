package query;

import connection.JdbcConnection;
import helper.Helper;
import model.Role;
import model.Type;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

abstract public class AbstractQuery {
    private static final JdbcConnection connection = Helper.getConnection();

    public static <E> List<E> query(String sql, List<Object> params, RowMapper<E> rowMapper) throws SQLException {
        Connection conn = getConnection().getConnection();

        PreparedStatement preStatement = conn.prepareStatement(sql);
        ResultSet resultSet = prepareStatement(preStatement, params).executeQuery();
        int row = 0;
        List<E> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(rowMapper.mapRow(resultSet, row));
            row++;
        }
        return result;

    }


    public static <E> List<E> query(String sql, RowMapper<E> rowMapper) throws SQLException {
        return query(sql, Collections.emptyList(), rowMapper);
    }

    public static void query(String sql) {
        query(sql, Collections.emptyList());
    }

    public static void query(String sql, List<Object> params) {
        Connection conn = getConnection().getConnection();
        try {
            PreparedStatement preStatement = conn.prepareStatement(sql);
            prepareStatement(preStatement, params);
            preStatement.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement prepareStatement(PreparedStatement statement, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            Object param = params.get(i);
            if (param instanceof UUID) {
                statement.setString(i + 1, ((UUID) param).toString());
            } else if (param instanceof String) {
                statement.setString(i + 1, (String) param);
            } else if (param instanceof Integer) {
                statement.setInt(i + 1, (Integer) param);
            } else if (param instanceof Timestamp) {
                statement.setTimestamp(i + 1, (Timestamp) param);
            } else if (param instanceof Role) {
                statement.setString(i + 1, ((Role) param).getId());
            }else if (param instanceof Type) {
                statement.setString(i + 1, ((Type) param).getId());
            } else {
                throw new UnsupportedOperationException("Unsupported parameter type in preparedStatement");
            }
        }
        return statement;
    }

    public static JdbcConnection getConnection() {
        return AbstractQuery.connection;
    }

}