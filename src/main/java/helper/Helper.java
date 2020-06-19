package helper;

import connection.JdbcConnection;
import model.*;
import query.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Helper {
    private static JdbcConnection connection;

    public static JdbcConnection getConnection() {
        return connection;
    }

    public static void setConnection(JdbcConnection connection) {
        Helper.connection = connection;
    }

    public static User signIn(String login, String password) throws SQLException {
        String sql = "select * from ZYKIN.USERS where login = ? and password = ?";
        List<Object> params = Arrays.asList(login, password);
        List<User> users = AbstractQuery.query(sql, params, new UserRowMapper());
        return users.isEmpty() ? null : users.get(0);
    }

    public static void register(Role role, String login, String password, String surname, String middleName, String name) throws SQLException {
        String sql = "insert into ZYKIN.USERS (id, role, login, password, surname, middlename, name) values (?, ?, ?, ?, ?, ?, ?)";
        List<Object> params = Arrays.asList(UUID.randomUUID(), role, login, password, surname, middleName, name);
        AbstractQuery.query(sql, params);
    }

    public static List<Buyer> getBuyersWithOverdue() throws SQLException {
        String sql = "" +
                "select b.ID,\n" +
                "       b.SURNAME       surname,\n" +
                "       b.MIDDLENAME    middlename,\n" +
                "       b.NAME          name,\n" +
                "       b.DATE_OF_BIRTH date_of_birth,\n" +
                "       b.PHONE_NUMBER  phone_number,\n" +
                "       b.ADDRESS       address\n" +
                "from RECIPE r\n" +
                "         join BUYER B on r.ID_BUYER = B.ID\n" +
                "         join ORDERS O on r.ID = O.ID_RECIPE\n" +
                "where o.DATE_OF_RECEIVE is null\n" +
                "   or r.DATE_OF_DONE < o.DATE_OF_RECEIVE\n" +
                "group by b.ID, b.SURNAME, b.MIDDLENAME, b.NAME, b.DATE_OF_BIRTH, b.PHONE_NUMBER, b.ADDRESS";
        return AbstractQuery.query(sql, new BuyerRowMapper());
    }

    public static List<Order> getOrdersInProduction() throws SQLException {
        String sql = "" +
                "select r.ID                    recipe_id,\n" +
                "       r.DIAGNOSIS             recipe_diagnosis,\n" +
                "       r.DATE_OF_DONE          recipe_date_of_done,\n" +
                "       m.NAME                  medicine_name,\n" +
                "       m.ID                    medicine_id,\n" +
                "       m.TYPE                  medicine_type,\n" +
                "       b.ID                    buyer_id,\n" +
                "       b.SURNAME               buyer_surname,\n" +
                "       b.MIDDLENAME            buyer_middlename,\n" +
                "       b.NAME                  buyer_name,\n" +
                "       b.DATE_OF_BIRTH         buyer_date_of_birth,\n" +
                "       b.PHONE_NUMBER          buyer_phone_number,\n" +
                "       b.ADDRESS               buyer_address,\n" +
                "       u.ID                    seller_id,\n" +
                "       u.ROLE                  seller_role,\n" +
                "       u.LOGIN                 seller_login,\n" +
                "       u.PASSWORD              seller_password,\n" +
                "       u.SURNAME               seller_surname,\n" +
                "       u.MIDDLENAME            seller_middlename,\n" +
                "       u.NAME                  seller_name,\n" +
                "       u2.ID                   technologist_id,\n" +
                "       U2.ROLE                 technologist_role,\n" +
                "       U2.LOGIN                technologist_login,\n" +
                "       U2.PASSWORD             technologist_password,\n" +
                "       U2.SURNAME              technologist_surname,\n" +
                "       U2.MIDDLENAME           technologist_middlename,\n" +
                "       U2.NAME                 technologist_name,\n" +
                "       U3.ID                   doctor_id,\n" +
                "       U3.ROLE                 doctor_role,\n" +
                "       U3.LOGIN                doctor_login,\n" +
                "       U3.PASSWORD             doctor_password,\n" +
                "       U3.SURNAME              doctor_surname,\n" +
                "       U3.MIDDLENAME           doctor_middlename,\n" +
                "       U3.NAME                 doctor_name,\n" +
                "       o.ID                    order_id,\n" +
                "       o.DATE_OF_ORDER         order_date_of_order,\n" +
                "       o.DATE_OF_MANUFACTURING order_date_of_manufacturing,\n" +
                "       o.DATE_OF_RECEIVE       order_date_of_receive\n" +
                "from ORDERS o\n" +
                "         join RECIPE R on o.ID_RECIPE = R.ID\n" +
                "         join BUYER B on R.ID_BUYER = B.ID\n" +
                "         join USERS U on o.ID_SELLER = U.ID\n" +
                "         join USERS U2 on o.ID_TECHNOLOGIST = U2.ID\n" +
                "         join USERS U3 on U3.ID = r.ID_DOCTOR\n" +
                "         join MEDICINE M on R.ID_MEDICINE = M.ID\n" +
                "where o.DATE_OF_MANUFACTURING is null";
        return AbstractQuery.query(sql, new OrderRowMapper());
    }

    public static List<GoodsOnWarehouse> getMedicineInStock(Type type, Integer rowCount) throws SQLException {
        if (rowCount < 0) {
            rowCount = 10;
        }

        String sql;
        List<Object> params;
        if (type != null) {
            sql = "" +
                    "select m.ID           medicine_id,\n" +
                    "       m.NAME         medicine_name,\n" +
                    "       m.TYPE         medicine_type,\n" +
                    "       g.ID           warehouse_id,\n" +
                    "       UNIT           warehouse_unit,\n" +
                    "       PRICE_FOR_UNIT warehouse_price,\n" +
                    "       MEDICINE_COUNT warehouse_medicine_count\n" +
                    "from GOODS_ON_WAREHOUSE g\n" +
                    "         join MEDICINE M on g.ID_MEDICINE = M.ID\n" +
                    "where rownum <= ?\n" +
                    "  and m.type = ?\n" +
                    "order by g.MEDICINE_COUNT ";
            params = Arrays.asList(rowCount, type);
        } else {
            sql = "" +
                    "select m.ID           medicine_id,\n" +
                    "       m.NAME         medicine_name,\n" +
                    "       m.TYPE         medicine_type,\n" +
                    "       g.ID           warehouse_id,\n" +
                    "       UNIT           warehouse_unit,\n" +
                    "       PRICE_FOR_UNIT warehouse_price,\n" +
                    "       MEDICINE_COUNT warehouse_medicine_count\n" +
                    "from GOODS_ON_WAREHOUSE g\n" +
                    "         join MEDICINE M on g.ID_MEDICINE = M.ID\n" +
                    "where rownum <= ?\n" +
                    "order by g.MEDICINE_COUNT";
            params = Collections.singletonList(rowCount);
        }
        return AbstractQuery.query(sql, params, new GoodsOnWarehouseRowMapper());
    }

    public static List<GoodsOnWarehouse> getMedicineInStockOrMinimum() throws SQLException {
        String sql = "" +
                "select m.ID           medicine_id,\n" +
                "       m.NAME         medicine_name,\n" +
                "       m.TYPE         medicine_type,\n" +
                "       g.ID           warehouse_id,\n" +
                "       UNIT           warehouse_unit,\n" +
                "       PRICE_FOR_UNIT warehouse_price,\n" +
                "       MEDICINE_COUNT warehouse_medicine_count\n" +
                "from GOODS_ON_WAREHOUSE g\n" +
                "         join MEDICINE M on g.ID_MEDICINE = M.ID\n" +
                "where MEDICINE_COUNT <= 10\n";
        return AbstractQuery.query(sql, new GoodsOnWarehouseRowMapper());
    }

    public static List<Order> getBuyersByMedicineName(String name) throws SQLException {
        String sql = "" +
                "select r.ID                    recipe_id,\n" +
                "       r.DIAGNOSIS             recipe_diagnosis,\n" +
                "       r.DATE_OF_DONE          recipe_date_of_done,\n" +
                "       m.NAME                  medicine_name,\n" +
                "       m.ID                    medicine_id,\n" +
                "       m.TYPE                  medicine_type,\n" +
                "       b.ID                    buyer_id,\n" +
                "       b.SURNAME               buyer_surname,\n" +
                "       b.MIDDLENAME            buyer_middlename,\n" +
                "       b.NAME                  buyer_name,\n" +
                "       b.DATE_OF_BIRTH         buyer_date_of_birth,\n" +
                "       b.PHONE_NUMBER          buyer_phone_number,\n" +
                "       b.ADDRESS               buyer_address,\n" +
                "       u.ID                    seller_id,\n" +
                "       u.ROLE                  seller_role,\n" +
                "       u.LOGIN                 seller_login,\n" +
                "       u.PASSWORD              seller_password,\n" +
                "       u.SURNAME               seller_surname,\n" +
                "       u.MIDDLENAME            seller_middlename,\n" +
                "       u.NAME                  seller_name,\n" +
                "       u2.ID                   technologist_id,\n" +
                "       U2.ROLE                 technologist_role,\n" +
                "       U2.LOGIN                technologist_login,\n" +
                "       U2.PASSWORD             technologist_password,\n" +
                "       U2.SURNAME              technologist_surname,\n" +
                "       U2.MIDDLENAME           technologist_middlename,\n" +
                "       U2.NAME                 technologist_name,\n" +
                "       U3.ID                   doctor_id,\n" +
                "       U3.ROLE                 doctor_role,\n" +
                "       U3.LOGIN                doctor_login,\n" +
                "       U3.PASSWORD             doctor_password,\n" +
                "       U3.SURNAME              doctor_surname,\n" +
                "       U3.MIDDLENAME           doctor_middlename,\n" +
                "       U3.NAME                 doctor_name,\n" +
                "       o.ID                    order_id,\n" +
                "       o.DATE_OF_ORDER         order_date_of_order,\n" +
                "       o.DATE_OF_MANUFACTURING order_date_of_manufacturing,\n" +
                "       o.DATE_OF_RECEIVE       order_date_of_receive\n" +
                "from ORDERS o\n" +
                "         join RECIPE R on o.ID_RECIPE = R.ID\n" +
                "         join BUYER B on R.ID_BUYER = B.ID\n" +
                "         join USERS U on o.ID_SELLER = U.ID\n" +
                "         join USERS U2 on o.ID_TECHNOLOGIST = U2.ID\n" +
                "         join USERS U3 on U3.ID = r.ID_DOCTOR\n" +
                "         join MEDICINE M on R.ID_MEDICINE = M.ID\n" +
                "where m.NAME = ?";
        final List<Object> params = Collections.singletonList(name);
        return AbstractQuery.query(sql, params, new OrderRowMapper());
    }

    public static List<Order> getBuyersByCategory(Type type) throws SQLException {
        String sql = "" +
                "select r.ID                    recipe_id,\n" +
                "       r.DIAGNOSIS             recipe_diagnosis,\n" +
                "       r.DATE_OF_DONE          recipe_date_of_done,\n" +
                "       m.NAME                  medicine_name,\n" +
                "       m.ID                    medicine_id,\n" +
                "       m.TYPE                  medicine_type,\n" +
                "       b.ID                    buyer_id,\n" +
                "       b.SURNAME               buyer_surname,\n" +
                "       b.MIDDLENAME            buyer_middlename,\n" +
                "       b.NAME                  buyer_name,\n" +
                "       b.DATE_OF_BIRTH         buyer_date_of_birth,\n" +
                "       b.PHONE_NUMBER          buyer_phone_number,\n" +
                "       b.ADDRESS               buyer_address,\n" +
                "       u.ID                    seller_id,\n" +
                "       u.ROLE                  seller_role,\n" +
                "       u.LOGIN                 seller_login,\n" +
                "       u.PASSWORD              seller_password,\n" +
                "       u.SURNAME               seller_surname,\n" +
                "       u.MIDDLENAME            seller_middlename,\n" +
                "       u.NAME                  seller_name,\n" +
                "       u2.ID                   technologist_id,\n" +
                "       U2.ROLE                 technologist_role,\n" +
                "       U2.LOGIN                technologist_login,\n" +
                "       U2.PASSWORD             technologist_password,\n" +
                "       U2.SURNAME              technologist_surname,\n" +
                "       U2.MIDDLENAME           technologist_middlename,\n" +
                "       U2.NAME                 technologist_name,\n" +
                "       U3.ID                   doctor_id,\n" +
                "       U3.ROLE                 doctor_role,\n" +
                "       U3.LOGIN                doctor_login,\n" +
                "       U3.PASSWORD             doctor_password,\n" +
                "       U3.SURNAME              doctor_surname,\n" +
                "       U3.MIDDLENAME           doctor_middlename,\n" +
                "       U3.NAME                 doctor_name,\n" +
                "       o.ID                    order_id,\n" +
                "       o.DATE_OF_ORDER         order_date_of_order,\n" +
                "       o.DATE_OF_MANUFACTURING order_date_of_manufacturing,\n" +
                "       o.DATE_OF_RECEIVE       order_date_of_receive\n" +
                "from ORDERS o\n" +
                "         join RECIPE R on o.ID_RECIPE = R.ID\n" +
                "         join BUYER B on R.ID_BUYER = B.ID\n" +
                "         join USERS U on o.ID_SELLER = U.ID\n" +
                "         join USERS U2 on o.ID_TECHNOLOGIST = U2.ID\n" +
                "         join USERS U3 on U3.ID = r.ID_DOCTOR\n" +
                "         join MEDICINE M on R.ID_MEDICINE = M.ID\n" +
                "where m.type = ?";
        final List<Object> params = Collections.singletonList(type);
        return AbstractQuery.query(sql, params, new OrderRowMapper());
    }

    public static List<Order> getBuyersByMedicineFreqName(String name) throws SQLException {
        String sql = "" +
                "select r.ID                    recipe_id,\n" +
                "       r.DIAGNOSIS             recipe_diagnosis,\n" +
                "       r.DATE_OF_DONE          recipe_date_of_done,\n" +
                "       m.NAME                  medicine_name,\n" +
                "       m.ID                    medicine_id,\n" +
                "       m.TYPE                  medicine_type,\n" +
                "       b.ID                    buyer_id,\n" +
                "       b.SURNAME               buyer_surname,\n" +
                "       b.MIDDLENAME            buyer_middlename,\n" +
                "       b.NAME                  buyer_name,\n" +
                "       b.DATE_OF_BIRTH         buyer_date_of_birth,\n" +
                "       b.PHONE_NUMBER          buyer_phone_number,\n" +
                "       b.ADDRESS               buyer_address,\n" +
                "       u.ID                    seller_id,\n" +
                "       u.ROLE                  seller_role,\n" +
                "       u.LOGIN                 seller_login,\n" +
                "       u.PASSWORD              seller_password,\n" +
                "       u.SURNAME               seller_surname,\n" +
                "       u.MIDDLENAME            seller_middlename,\n" +
                "       u.NAME                  seller_name,\n" +
                "       u2.ID                   technologist_id,\n" +
                "       U2.ROLE                 technologist_role,\n" +
                "       U2.LOGIN                technologist_login,\n" +
                "       U2.PASSWORD             technologist_password,\n" +
                "       U2.SURNAME              technologist_surname,\n" +
                "       U2.MIDDLENAME           technologist_middlename,\n" +
                "       U2.NAME                 technologist_name,\n" +
                "       U3.ID                   doctor_id,\n" +
                "       U3.ROLE                 doctor_role,\n" +
                "       U3.LOGIN                doctor_login,\n" +
                "       U3.PASSWORD             doctor_password,\n" +
                "       U3.SURNAME              doctor_surname,\n" +
                "       U3.MIDDLENAME           doctor_middlename,\n" +
                "       U3.NAME                 doctor_name,\n" +
                "       o.ID                    order_id,\n" +
                "       o.DATE_OF_ORDER         order_date_of_order,\n" +
                "       o.DATE_OF_MANUFACTURING order_date_of_manufacturing,\n" +
                "       o.DATE_OF_RECEIVE       order_date_of_receive\n" +
                "from ORDERS o\n" +
                "         join RECIPE R on o.ID_RECIPE = R.ID\n" +
                "         join BUYER B on R.ID_BUYER = B.ID\n" +
                "         join USERS U on o.ID_SELLER = U.ID\n" +
                "         join USERS U2 on o.ID_TECHNOLOGIST = U2.ID\n" +
                "         join USERS U3 on U3.ID = r.ID_DOCTOR\n" +
                "         join MEDICINE M on R.ID_MEDICINE = M.ID\n" +
                "where m.NAME = ?\n" +
                "order by count(u.ID) desc";
        List<Object> params = Collections.singletonList(name);
        return AbstractQuery.query(sql, params, new OrderRowMapper());
    }

    public static List<Order> getBuyersByFreqCategory(Type type) throws SQLException {
        String sql = "" +
                "select r.ID                    recipe_id,\n" +
                "       r.DIAGNOSIS             recipe_diagnosis,\n" +
                "       r.DATE_OF_DONE          recipe_date_of_done,\n" +
                "       m.NAME                  medicine_name,\n" +
                "       m.ID                    medicine_id,\n" +
                "       m.TYPE                  medicine_type,\n" +
                "       b.ID                    buyer_id,\n" +
                "       b.SURNAME               buyer_surname,\n" +
                "       b.MIDDLENAME            buyer_middlename,\n" +
                "       b.NAME                  buyer_name,\n" +
                "       b.DATE_OF_BIRTH         buyer_date_of_birth,\n" +
                "       b.PHONE_NUMBER          buyer_phone_number,\n" +
                "       b.ADDRESS               buyer_address,\n" +
                "       u.ID                    seller_id,\n" +
                "       u.ROLE                  seller_role,\n" +
                "       u.LOGIN                 seller_login,\n" +
                "       u.PASSWORD              seller_password,\n" +
                "       u.SURNAME               seller_surname,\n" +
                "       u.MIDDLENAME            seller_middlename,\n" +
                "       u.NAME                  seller_name,\n" +
                "       u2.ID                   technologist_id,\n" +
                "       U2.ROLE                 technologist_role,\n" +
                "       U2.LOGIN                technologist_login,\n" +
                "       U2.PASSWORD             technologist_password,\n" +
                "       U2.SURNAME              technologist_surname,\n" +
                "       U2.MIDDLENAME           technologist_middlename,\n" +
                "       U2.NAME                 technologist_name,\n" +
                "       U3.ID                   doctor_id,\n" +
                "       U3.ROLE                 doctor_role,\n" +
                "       U3.LOGIN                doctor_login,\n" +
                "       U3.PASSWORD             doctor_password,\n" +
                "       U3.SURNAME              doctor_surname,\n" +
                "       U3.MIDDLENAME           doctor_middlename,\n" +
                "       U3.NAME                 doctor_name,\n" +
                "       o.ID                    order_id,\n" +
                "       o.DATE_OF_ORDER         order_date_of_order,\n" +
                "       o.DATE_OF_MANUFACTURING order_date_of_manufacturing,\n" +
                "       o.DATE_OF_RECEIVE       order_date_of_receive\n" +
                "from ORDERS o\n" +
                "         join RECIPE R on o.ID_RECIPE = R.ID\n" +
                "         join BUYER B on R.ID_BUYER = B.ID\n" +
                "         join USERS U on o.ID_SELLER = U.ID\n" +
                "         join USERS U2 on o.ID_TECHNOLOGIST = U2.ID\n" +
                "         join USERS U3 on U3.ID = r.ID_DOCTOR\n" +
                "         join MEDICINE M on R.ID_MEDICINE = M.ID\n" +
                "where m.type = ?\n" +
                "order by count(u.ID) desc";
        List<Object> params = Collections.singletonList(type);
        return AbstractQuery.query(sql, params, new OrderRowMapper());
    }
}
