package helper;

import connection.JdbcConnection;
import model.*;
import query.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

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

    //1
    public static List<Buyer> getBuyersWithOverdue() throws SQLException {
        String sql = "" +
                "select b.ID,\n" +
                "       b.SURNAME       surname,\n" +
                "       b.MIDDLENAME    middlename,\n" +
                "       b.NAME          name,\n" +
                "       b.DATE_OF_BIRTH date_of_birth,\n" +
                "       b.PHONE_NUMBER  phone_number,\n" +
                "       b.ADDRESS       address\n" +
                "from PRESCRIPTION r\n" +
                "         join BUYER B on r.ID_BUYER = B.ID\n" +
                "         join ORDERS O on r.ID = O.ID_RECIPE\n" +
                "where o.DATE_OF_RECEIVE is null\n" +
                "   or r.DATE_OF_DONE < o.DATE_OF_RECEIVE\n" +
                "group by b.ID, b.SURNAME, b.MIDDLENAME, b.NAME, b.DATE_OF_BIRTH, b.PHONE_NUMBER, b.ADDRESS";
        return AbstractQuery.query(sql, new BuyerRowMapper());
    }

    //13
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

    //TODO 12
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

    //1
    public static List<Buyer> getAllOverdueBuyers() throws SQLException {
        String sqlRequest = "select b.ID, b.NAME b.SURNAME,b.MIDDLENAME, b.PHONE_NUMBER, b.ADDRESS,b.DATE_OF_BIRTH\n" +
                "from BUYER b where b.ID in\n" +
                "    (select p.ID_BUYER from PRESCRIPTIONS p where p.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where md.NAME=?)\n" +
                "    and p.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_ORDER between ? and ?) );";
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, new DetailedBuyerRowMapper());
        return  buyers;
    }

    //1
    public static List<Integer> getCountOfAllOverdueBuyers() throws SQLException {
        String sqlRequest = "select count(*) as count_of_buyer from\n" +
                "    (select p.ID_BUYER from PRESCRIPTIONS p where p.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where sysdate>DATE_OF_RECEIVE));";
        List<Integer> countOfAwaitingBuyers = AbstractQuery.query(sqlRequest, new CountOfBuyerRowMapper());
        return countOfAwaitingBuyers;
    }

    //2
    public static List<Buyer> getAwaitingBuyerByDrugName(String nameDrug) throws SQLException {
        String sqlRequest = "select b.ID, b.SURNAME,b.MIDDLENAME, b.PHONE_NUMBER, b.ADDRESS,b.DATE_OF_BIRTH\n" +
                "from BUYER b where b.ID in\n" +
                "(select pr.ID_BUYER from PRESCRIPTIONS pr where pr.ID_PRESCRIPT in\n" +
                "(select o.ID_PRESCRIPT from ORDERS o where o.STATUS=('AWAITING')))";
        List<Object> params = Collections.singletonList(nameDrug);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new DetailedBuyerRowMapper());
        return buyers;
    }

    //2
    public static List<Integer> getCountOfAwaitingBuyerByDrugName(String nameDrug ) throws SQLException {
        String sqlRequest = "select count() as count_of_buyer from\n" +
                "(select pr.ID_BUYER from PRESCRIPTIONS pr where pr.ID_PRESCRIPT in\n" +
                "(select o.ID_PRESCRIPT from ORDERS o where o.STATUS=('AWATING')))";
        List<Object> params = Collections.singletonList(nameDrug);
        List<Integer> countOfAwaitingBuyers = AbstractQuery.query(sqlRequest, params, new CountOfBuyerRowMapper());
        return countOfAwaitingBuyers;
    }

    //2
    public static List<Buyer> getAwaitingBuyerByDrugType(Type type) throws SQLException {
        String sqlRequest = "select b.ID,b.SURNAME,b.MIDDLENAME,b.PHONE_NUMBER, b.ADDRESS,b.DATE_OF_BIRTH\n" +
                "from BUYER b where b.ID in\n" +
                "    (select pr.ID_BUYER from PRESCRIPTIONS pr where pr.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where DRUG_TYPE=?)\n" +
                "    and pr.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.STATUS=('AWAITING')))";
        List<Object> params = Collections.singletonList(type);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new DetailedBuyerRowMapper());
        return buyers;
    }

    //2
    public static List<Integer> getCountOfAwaitingBuyerByDrugType(Type type) throws SQLException {
        String sqlRequest = "select count() as count_of_buyer from\n" +
                "(select pr.ID_BUYER from PRESCRIPTIONS pr where pr.ID_DRUG in\n" +
                "    (select md.ID_DRUG from MANUFACTURED_DRUG md where DRUG_TYPE=?)\n" +
                "and pr.ID_PRESCRIPT in\n" +
                "    (select o.ID_PRESCRIPT from ORDERS o where o.STATUS=('AWATING)))";
        List<Object> params = Collections.singletonList(type);
        List<Integer> countOfAwaitingBuyers = AbstractQuery.query(sqlRequest, params, new CountOfBuyerRowMapper());
        return countOfAwaitingBuyers;
    }

    //3
    public static List<Drug> getMostUsedSubstances() {

        String requestManufacturedDrugs = "" +
                "select d.name FROM MANUFACTURED_DRUG d" +
                "    JOIN GOODS_ON_WAREHOUSE GOW on d.ID_GOOD = GOW.ID_GOOD" +
                "    JOIN  SALES S on GOW.ID_GOOD = S.ID_GOOD WHERE ROWNUM = 10 GROUP BY d.name ORDER BY COUNT(S.ID_GOOD) DESC;";

        List<Drug> drugsList = null;
        try {
            drugsList.addAll(AbstractQuery.query(requestManufacturedDrugs, new ManufacturedDrugRowMapper()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return drugsList;

    }

    //3
    public static List<Drug> getMostUsedSubstancesByType(Type type) throws SQLException {

        String requestManufacturedDrugs = "" +
                "select d.name FROM MANUFACTURED_DRUG d" +
                "    JOIN GOODS_ON_WAREHOUSE GOW on d.ID_GOOD = GOW.ID_GOOD" +
                "    JOIN  SALES S on GOW.ID_GOOD = S.ID_GOOD WHERE d.type = ? WHERE ROWNUM = 10 GROUP BY d.name ORDER BY COUNT(S.ID_GOOD) DESC;";

        List<Object> params = Collections.singletonList(type);
        List<Drug> drugsList = AbstractQuery.query(requestManufacturedDrugs, params, new DetailedDrugRowMapper());

        return drugsList;

    }

    //4
    public static List<VolumeOfSubstanceResult> getVolumeOfSubstance(String name, Timestamp beginDate, Timestamp endDate) throws SQLException {

        String sqlRequest = "" +
                "select sum(c.AMOUNT) AS volume from COMPOSITION c where c.ID_DRUG in" +
                "(select p.ID_DRUG from PRESCRIPTIONS p where p.ID_PRESCRIPT in" +
                "(select  o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_MANUFACTURING  between =? and =?))" +
                "and c.ID_COMPONENT in" +
                "    (select com.ID_COMPONENT from COMPONENTS com);";
        List<Object> params = Arrays.asList(beginDate, endDate);

        List<VolumeOfSubstanceResult> volumeOfSubstanceResult =
                AbstractQuery.query(sqlRequest, params, new VolumeOfSubstanceResultsRowMapper());

        return volumeOfSubstanceResult;
    }

    //5
    public static List<Buyer> getBuyersOrderedSpecificDrugInPeriod(String drugName, Timestamp beginDate, Timestamp endDate) throws SQLException {
        String sqlRequest = "select b.ID,b.SURNAME,b.MIDDLENAME,b.PHONE_NUMBER, b.ADDRESS,b.DATE_OF_BIRTH\n" +
                "from BUYER b where b.ID in\n" +
                "    (select p.ID_BUYER from PRESCRIPTIONS p where p.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where md.NAME=?)\n" +
                "    and p.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_ORDER between ? and ?) );";
        List<Object> params = Arrays.asList(drugName, beginDate, endDate);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new DetailedBuyerRowMapper());
        return buyers;

    }
    //5
    public static List<Integer> getCountOfBuyersOrderedSpecificDrugInPeriod(String drugName, Timestamp beginDate, Timestamp endDate) throws SQLException{
        String sqlRequest = "select count() as count_of_b\n" +
                "from (select p.ID_BUYER from PRESCRIPTIONS p\n" +
                "    where p.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where md.NAME=?)\n" +
                "    and p.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_ORDER between ? and ?));";
        List<Object> params = Arrays.asList(drugName, beginDate, endDate);
        List<Integer> countOfBuyers = AbstractQuery.query(sqlRequest, params, new CountOfBuyerRowMapper());
        return countOfBuyers;
    }

    //5
    public static List<Buyer> getBuyersOrderedSpecificDrugTypeInPeriod(String drugName, Timestamp beginDate, Timestamp endDate) throws SQLException {
        String sqlRequest = "select b.ID,b.SURNAME,b.MIDDLENAME,b.PHONE_NUMBER, b.ADDRESS,b.DATE_OF_BIRTH\n" +
                "from BUYER b where b.ID in\n" +
                "    (select p.ID_BUYER from PRESCRIPTIONS p where p.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where md.NAME=?)\n" +
                "    and p.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_ORDER between ? and ?) );";
        List<Object> params = Arrays.asList(drugName, beginDate, endDate);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new DetailedBuyerRowMapper());
        return buyers;

    }
    //5
    public static List<Integer> getCountOfBuyersOrderedSpecificTypeDrugInPeriod(String drugName, Timestamp beginDate, Timestamp endDate) throws SQLException{
        String sqlRequest = "select count() as count_of_b\n" +
                "from (select p.ID_BUYER from PRESCRIPTIONS p\n" +
                "    where p.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where md.NAME=?)\n" +
                "    and p.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_ORDER between ? and ?));";
        List<Object> params = Arrays.asList(drugName, beginDate, endDate);
        List<Integer> countOfBuyers = AbstractQuery.query(sqlRequest, params, new CountOfBuyerRowMapper());
        return countOfBuyers;
    }

    //6
    public static List<Drug> getAllDrugsWithMinimalAMountOrEmpted() throws SQLException {
        String sqlRequest = "select * from\n" +
                "(select DRUG_TYPE, ID_DRUG,NAME,PRICE_PER_UNIT,UNIT\n" +
                "    from READY_DRUG rd where rd.ID_GOOD in\n" +
                "        (select gow.ID_GOOD from GOODS_ON_WAREHOUSE gow where gow.AMOUNT<=gow.MINIMAL_AMOUNT))\n" +
                "union\n" +
                "(select DRUG_TYPE, ID_DRUG,NAME,PRICE_PER_UNIT,UNIT\n" +
                "    from MANUFACTURED_DRUG md where md.ID_GOOD in\n" +
                "        (select gow.ID_GOOD from GOODS_ON_WAREHOUSE gow where gow.AMOUNT<=gow.MINIMAL_AMOUNT));";
        List<Drug> drugs = AbstractQuery.query(sqlRequest, new DetailedDrugRowMapper());
        return drugs;
    }

    //7
    public static List<Drug> getAllDrugWithMinimalAmount() throws SQLException {
        String sqlRequest = "SELECT MFD.name, MFD.type from MANUFACTURED_DRUG MFD\n" +
                "JOIN GOODS_ON_WAREHOUSE G ON MFD.ID_GOOD = G.ID_GOOD WHERE G.AMOUNT >= G.MINIMAL_AMOUNT;";
        List<Drug> drugs = AbstractQuery.query(sqlRequest, new DrugWithMinimalAmountRowMapper());
        return drugs;
    }

    //7
    public static List<Drug> getDrugWithMinimalAmountByType(Type type) throws SQLException {
        String sqlRequest = "SELECT MFD.name, MFD.type from MANUFACTURED_DRUG MFD\n" +
                "JOIN GOODS_ON_WAREHOUSE G ON MFD.ID_GOOD = G.ID_GOOD WHERE G.TYPE = ? AND G.AMOUNT >= G.MINIMAL_AMOUNT;";
        List<Drug> drugs = AbstractQuery.query(sqlRequest, new DrugWithMinimalAmountRowMapper());
        return drugs;
    }

    //8
    public static List<Order> getOrdersInProduction() throws SQLException {
        String sqlRequest = "select\n" +
                "o.id_orders, o.id_prescript, o.date_of_order, o.date_of_manufacturing, o.date_of_receive, o.price, o.status,\n" +
                "t.id, t.role, t.surname, t.middlename, t.name,\n" +
                "s.id, s.role, s.surname, s.middlename, s.name\n" +
                "    from \n" +
                "         (select\n" +
                "              o.id_orders, o.id_prescript, o.date_of_order, o.date_of_manufacturing, o.date_of_receive, o.price, o.status,\n" +
                "              o.ID_TECHNOLOGIST,o.ID_SELLER\n" +
                "          from ORDERS o where o.STATUS=('IN_PROGRESS')) o\n" +
                "        join\n" +
                "            (select id, role, surname, middlename, name from USERS ) t\n" +
                "        on t.ID=o.ID_TECHNOLOGIST\n" +
                "        join\n" +
                "            (select id, role, surname, middlename, name from USERS ) s\n" +
                "         on s.ID=o.ID_SELLER;";
        List<Order> orders = AbstractQuery.query(sqlRequest, new OrderRowMapper());

        return orders;
    }

    //9
    public static List<Component> getComponentsNeededForOrder() throws SQLException {
        String sqlRequest = "" +
                "select com.name as name, com.type as type, com.ID_COMPONENT as id, com.UNIT as unit, com.PRICE_PER_UNIT as price\n" +
                "from components com\n" +
                "where com.id_component in\n" +
                "(select c.id_component\n" +
                "from composition c\n" +
                "where c.id_drug in\n" +
                "      (select p.id_drug\n" +
                "       from prescriptions p\n" +
                "       where p.id_prescript in\n" +
                "             (select o.id_prescript from orders o where sysdate <= date_of_manufacturing)));";

        List<Component> components = AbstractQuery.query(sqlRequest, new ComponentRowMapper());

        return components;
    }

    //9
    public static List<Integer> getAmountOfAllComponentsNeededForOrder() throws SQLException {
        String sqlRequest = "select count(*) as amount\n" +
                "from\n" +
                "(select c.id_component\n" +
                "from composition c\n" +
                "where c.id_drug in\n" +
                "      (select p.id_drug\n" +
                "       from prescriptions p\n" +
                "       where p.id_prescript in\n" +
                "             (select o.id_prescript from orders o where sysdate <= date_of_manufacturing)));\n";
        return AbstractQuery.query(sqlRequest, new AmountOfAllComponentsNeededForOrderRowMapper());
    }

    //10
    public static List<Technologies> getAllTechnologiesForSpecificDrugType(Type type) throws SQLException {
        String sqlRequest = "select t.PRODUCTION_ACTION as action, t.PRODUCTION_TIME as production_time, t.it.ID_TECHNOLOGY as id from TECHNOLOGIES t" +
                "where t.ID_DRUG in\n" +
                "(select md.ID_DRUG from MANUFACTURED_DRUG md where md.DRUG_TYPE=?);";

        List<Object> params = Collections.singletonList(type);
        List<Technologies> technologies = AbstractQuery.query(sqlRequest, params, new TechnologiesRowMapper());
        return technologies;
    }

    //10
    public static List<Technologies> getAllTechnologiesForSpecificDrugName(String name) throws SQLException {
        String sqlRequest = "select t.PRODUCTION_ACTION, t.PRODUCTION_TIME,t.ID_TECHNOLOGY from TECHNOLOGIES t where t.ID_DRUG in\n" +
                "(select md.ID_DRUG from MANUFACTURED_DRUG md where md.name =? );";

        List<Object> params = Collections.singletonList(name);
        List<Technologies> technologies = AbstractQuery.query(sqlRequest, params, new TechnologiesRowMapper());
        return technologies;
    }

    //10
    public static List<Technologies> getAllTechnologiesForDrugInProduction() throws SQLException {
        String sqlRequest = "select t.PRODUCTION_ACTION, t.PRODUCTION_TIME,t.ID_TECHNOLOGY\n" +
                "       from TECHNOLOGIES t where t.ID_DRUG in\n" +
                "        (select p.id_drug\n" +
                "              from prescriptions p\n" +
                "              where p.id_prescript in\n" +
                "                    (select o.id_prescript from orders o\n" +
                "                    where sysdate <= date_of_manufacturing));";

        List<Technologies> technologies = AbstractQuery.query(sqlRequest, new TechnologiesRowMapper());

        return technologies;
    }

    //11
    public static List<InfoAboutDrugWithItsComponents> getInfoAboutDrugsWithItsComponents(String drugName) {
        String sqlRequest = "";

        return null;
    }

    //12
    public static List<Buyer> getBuyerBySpecificDrugsTypeFreqOrdering(Type type) throws SQLException {
        String sqlRequest = "select count(b.ID) as count,\n" +
                "       b.ID, b.SURNAME, b.MIDDLENAME, b.NAME, b.DATE_OF_BIRTH, b.PHONE_NUMBER, b.ADDRESS\n" +
                "from BUYER b where b.ID in\n" +
                "(select p.ID_BUYER from PRESCRIPTIONS p where p.ID_DRUG in\n" +
                "(select md.ID_DRUG from MANUFACTURED_DRUG md where md.DRUG_TYPE =?))\n" +
                "and ROWNUM=10\n" +
                "order by count(b.id) desc;";

        List<Object> params = Collections.singletonList(type);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new BuyerRowMapper());

        return buyers;
    }

    //12
    public static List<Buyer> getBuyerBySpecificDrugFreqOrdering(String drugName) throws SQLException {
        String sqlRequest = "select count(b.ID) as count,\n" +
                "       b.ID, b.SURNAME, b.MIDDLENAME, b.NAME, b.DATE_OF_BIRTH, b.PHONE_NUMBER, b.ADDRESS\n" +
                "from BUYER b where b.ID in\n" +
                "   (select p.ID_BUYER from PRESCRIPTIONS p where p.ID_DRUG in\n" +
                "       (select md.ID_DRUG from MANUFACTURED_DRUG md where md.name =?))\n" +
                "    and ROWNUM=10\n" +
                "order by count(b.id) desc";

        List<Object> params = Collections.singletonList(drugName);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new BuyerRowMapper());

        return buyers;
    }

    //13
    public static List<InfoAboutDrug> getInfoAboutSpecificDrug(String drugName) throws SQLException {
        String sqlRequest = "SELECT DI.type, DI.name, DI.price, DI.action, DI.AMOUNT FROM DRUG_INFO DI WHERE DI.name = ?";

        List<Object> params = Collections.singletonList(drugName);
        List<InfoAboutDrug> infoAboutDrugs = AbstractQuery.query(sqlRequest, params, new InfoAboutDrugRowMapper());
        return infoAboutDrugs;
    }
    //13
    public static List<Component> getAllComponentsByDrugName(String drugName) throws SQLException {
        String sqlRequest = "SELECT C.ID_COMPONENT, C.NAME as name, C.PRICE_PER_UNIT as price, C.UNIT as unit, C.TYPE as type FROM COMPONENTS C\n" +
                "    WHERE ID_COMPONENT IN \n" +
                "          (SELECT CMPST.ID_COMPONENT FROM COMPOSITION CMPST WHERE CMPST.ID_DRUG IN\n" +
                "                (SELECT MAN_DRUG.ID_DRUG FROM MANUFACTURED_DRUG MAN_DRUG WHERE MAN_DRUG.NAME =?));";

        List<Object> params = Collections.singletonList(drugName);
        List<Component> components = AbstractQuery.query(sqlRequest, params, new ComponentRowMapper());
        return components;
    }

}