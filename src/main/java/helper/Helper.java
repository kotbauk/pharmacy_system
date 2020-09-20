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
    public static List<Buyer> getAllOverdueBuyers() throws SQLException {
        String sqlRequest = "select b.ID as buyer_id, b.SURNAME as buyer_surname, b.MIDDLENAME as buyer_middlename, b.NAME as buyer_name, " +
                "b.PHONE_NUMBER as buyer_phone_number, b.ADDRESS as buyer_address, b.DATE_OF_BIRTH as buyer_date_of_birth " +
                "from BUYER b where b.ID in " +
                "(select p.ID_BUYER from PRESCRIPTIONS p where p.ID_PRESCRIPT in " +
                "(select o.ID_PRESCRIPT from ORDERS o where sysdate > DATE_OF_RECEIVE and o.status = ('DONE')))";
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, new DetailedBuyerRowMapper());
        return buyers;
    }

    //2
    public static List<Buyer> getAllAwaitingBuyers() throws SQLException {
        String sqlRequest = "select b.ID as buyer_id, b.NAME as buyer_name, b.SURNAME as buyer_surname, b.MIDDLENAME as buyer_middlename, \n" +
                "b.PHONE_NUMBER as buyer_phone_number, b.ADDRESS as buyer_address, b.DATE_OF_BIRTH as buyer_date_of_birth\n" +
                "from BUYER b where b.ID in\n" +
                "(select pr.ID_BUYER from PRESCRIPTIONS pr where pr.ID_PRESCRIPT in\n" +
                "(select o.ID_PRESCRIPT from ORDERS o where o.STATUS=('AWAITING')))";
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, new DetailedBuyerRowMapper());
        return buyers;
    }


    //2
    public static List<Buyer> getAwaitingBuyerByDrugType(Type type) throws SQLException {
        String sqlRequest = "select b.ID as buyer_id, b.NAME as buyer_name, b.SURNAME as buyer_surname, b.MIDDLENAME as buyer_middlename,\n" +
                "b.PHONE_NUMBER as buyer_phone_number, b.ADDRESS as buyer_address, b.DATE_OF_BIRTH as buyer_date_of_birth\n" +
                "from BUYER b where b.ID in\n" +
                "    (select pr.ID_BUYER from PRESCRIPTIONS pr where pr.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where DRUG_TYPE =?)\n" +
                "    and pr.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.STATUS=('AWAITING')))";
        List<Object> params = Collections.singletonList(type);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new DetailedBuyerRowMapper());
        return buyers;
    }


    //3
    public static List<DrugsWithAmountResults> getMostUsedDrugs() throws SQLException {
        String requestManufacturedDrugs = "select  D.ID_DRUG as drug_id, D.name, D.DRUG_TYPE as drug_type," +
                "D.PRICE_PER_UNIT as pricePerUnit, D.UNIT as unit, COUNT(S.ID_GOOD) as amount FROM" +
                "(SELECT * FROM MANUFACTURED_DRUG MD\n" +
                "UNION ALL\n" +
                "SELECT * FROM READY_DRUG RD) D\n" +
                "JOIN GOODS_ON_WAREHOUSE GOW on D.ID_GOOD = GOW.ID_GOOD\n" +
                "JOIN  SALES S on GOW.ID_GOOD = S.ID_GOOD WHERE ROWNUM = 10 " +
                "GROUP BY  D.ID_DRUG, D.name, D.DRUG_TYPE, D.PRICE_PER_UNIT, D.UNIT ORDER BY COUNT(S.ID_GOOD) DESC";

        List<DrugsWithAmountResults> drugsList = AbstractQuery.query(requestManufacturedDrugs, new DrugsWithAmountRowMapper());
        return drugsList;

    }

    //3
    public static List<DrugsWithAmountResults> getMostUsedDrugByType(Type type) throws SQLException {
        String sqlRequest = "select  D.ID_DRUG as drug_id, D.name, D.DRUG_TYPE as drug_type," +
                "D.PRICE_PER_UNIT as pricePerUnit, D.UNIT as unit, COUNT(S.ID_GOOD) as amount FROM" +
                "(SELECT * FROM MANUFACTURED_DRUG MD\n" +
                "UNION ALL\n" +
                "SELECT * FROM READY_DRUG RD) D\n" +
                "JOIN GOODS_ON_WAREHOUSE GOW on D.ID_GOOD = GOW.ID_GOOD\n" +
                "JOIN  SALES S on GOW.ID_GOOD = S.ID_GOOD WHERE ROWNUM = 10 AND drug_type = ? " +
                "GROUP BY  D.ID_DRUG, D.name, D.DRUG_TYPE, D.PRICE_PER_UNIT, D.UNIT ORDER BY COUNT(S.ID_GOOD) DESC";


        List<Object> params = Collections.singletonList(type);
        List<DrugsWithAmountResults> drugsList = AbstractQuery.query(sqlRequest, params, new DrugsWithAmountRowMapper());
        return drugsList;

    }

    //4
    public static VolumeOfSubstanceResult getVolumeOfSubstance(String name, Timestamp beginDate, Timestamp endDate) throws SQLException {

        String sqlRequest = "select sum(c.AMOUNT) AS volume from COMPOSITION c where c.ID_DRUG in\n" +
                "(select p.ID_DRUG from PRESCRIPTIONS p where p.ID_PRESCRIPT in\n" +
                "(select  o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_MANUFACTURING >= ? and o.DATE_OF_MANUFACTURING <= ? ))\n" +
                "and c.ID_COMPONENT in (select com.ID_COMPONENT from COMPONENTS com WHERE com.NAME = ?)";
        List<Object> params = Arrays.asList(name, beginDate, endDate);

        List<VolumeOfSubstanceResult> volumeOfSubstanceResult =
                AbstractQuery.query(sqlRequest, params, new VolumeOfSubstanceResultsRowMapper());

        return volumeOfSubstanceResult.get(0);
    }

    //5
    public static List<Buyer> getBuyersOrderedSpecificDrugInPeriod(String drugName, Timestamp beginDate, Timestamp endDate) throws SQLException {
        String sqlRequest = "select b.ID,b.SURNAME,b.MIDDLENAME,b.PHONE_NUMBER, b.ADDRESS,b.DATE_OF_BIRTH\n" +
                "from BUYER b where b.ID in\n" +
                "    (select p.ID_BUYER from PRESCRIPTIONS p where p.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where md.NAME = ?)\n" +
                "    and p.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_ORDER >= ? and o.DATE_OF_ORDER <= ?) )";
        List<Object> params = Arrays.asList(drugName, beginDate, endDate);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new DetailedBuyerRowMapper());
        return buyers;

    }


    //5
    public static List<Buyer> getBuyersOrderedSpecificDrugTypeInPeriod(Type type, Timestamp beginDate, Timestamp endDate) throws SQLException {
        String sqlRequest = "select b.ID,b.SURNAME,b.MIDDLENAME,b.PHONE_NUMBER, b.ADDRESS,b.DATE_OF_BIRTH\n" +
                "from BUYER b where b.ID in\n" +
                "    (select p.ID_BUYER from PRESCRIPTIONS p where p.ID_DRUG in\n" +
                "        (select md.ID_DRUG from MANUFACTURED_DRUG md where md.NAME=?)\n" +
                "    and p.ID_PRESCRIPT in\n" +
                "        (select o.ID_PRESCRIPT from ORDERS o where o.DATE_OF_ORDER between ? and ?) )";
        List<Object> params = Arrays.asList(type, beginDate, endDate);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new DetailedBuyerRowMapper());
        return buyers;

    }


    //6
    public static List<Drug> getAllDrugsWithMinimalAMountOrEmpted() throws SQLException {
        String sqlRequest = "select DRUG_TYPE, ID_DRUG,NAME,PRICE_PER_UNIT as pricePerUnit,UNIT\n" +
                "        from READY_DRUG rd where rd.ID_GOOD in\n" +
                "            (select gow.ID_GOOD from GOODS_ON_WAREHOUSE gow where gow.AMOUNT<=gow.MINIMAL_AMOUNT)\n" +
                "    union\n" +
                "    select DRUG_TYPE, ID_DRUG,NAME,PRICE_PER_UNIT,UNIT\n" +
                "        from MANUFACTURED_DRUG md where md.ID_GOOD in\n" +
                "            (select gow.ID_GOOD from GOODS_ON_WAREHOUSE gow where gow.AMOUNT<=gow.MINIMAL_AMOUNT)";
        List<Drug> drugs = AbstractQuery.query(sqlRequest, new DetailedDrugRowMapper());
        return drugs;
    }

    //7
    public static List<Drug> getAllDrugWithMinimalAmount() throws SQLException {
        String sqlRequest = "SELECT MFD.name, MFD.DRUG_TYPE as type from MANUFACTURED_DRUG MFD\n" +
                "                JOIN GOODS_ON_WAREHOUSE G ON MFD.ID_GOOD = G.ID_GOOD WHERE G.AMOUNT >= G.MINIMAL_AMOUNT";
        List<Drug> drugs = AbstractQuery.query(sqlRequest, new DrugWithMinimalAmountRowMapper());
        return drugs;
    }

    //7
    public static List<Drug> getDrugWithMinimalAmountByType(Type type) throws SQLException {
        String sqlRequest = "SELECT MFD.name, MFD.DRUG_TYPE as type from MANUFACTURED_DRUG MFD\n" +
                "JOIN GOODS_ON_WAREHOUSE G ON MFD.ID_GOOD = G.ID_GOOD WHERE MFD.DRUG_TYPE = ? AND G.AMOUNT >= G.MINIMAL_AMOUNT";
        List<Object> params = Collections.singletonList(type);
        List<Drug> drugs = AbstractQuery.query(sqlRequest, params, new DrugWithMinimalAmountRowMapper());
        return drugs;
    }

    //8
    public static List<Order> getOrdersInProduction() throws SQLException {
        String sqlRequest = "select\n" +
                "o.id_orders as order_id, o.date_of_order as order_date_of_order, o.date_of_manufacturing as order_date_of_manufacturing, " +
                "o.date_of_receive as order_date_of_receive,  o.status as status, \n" +
                "t.id as technologist_id, t.role as technologist_role, t.surname as technologist_surname, t.middlename as technologist_middlename, t.name as technologist_name,\n" +
                "s.id as seller_id,  s.role as seller_role, s.surname as seller_surname, s.middlename as seller_middlename, s.name as seller_name\n" +
                "    from \n" +
                "         (select\n" +
                "              o.id_orders,  o.date_of_order, o.date_of_manufacturing, o.date_of_receive, o.status,\n" +
                "              o.ID_TECHNOLOGIST,o.ID_SELLER\n" +
                "          from ORDERS o where o.STATUS=('IN_PROGRESS')) o\n" +
                "        join\n" +
                "            (select id, role, surname, middlename, name from USERS ) t\n" +
                "        on t.ID=o.ID_TECHNOLOGIST\n" +
                "        join\n" +
                "            (select id, role, surname, middlename, name from USERS ) s\n" +
                "         on s.ID=o.ID_SELLER";
        List<Order> orders = AbstractQuery.query(sqlRequest, new OrderRowMapper());

        return orders;
    }

    //9
    public static List<Component> getComponentsNeededForOrders() throws SQLException {
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
                "             (select o.id_prescript from orders o where sysdate <= date_of_manufacturing)))";

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
                "             (select o.id_prescript from orders o where sysdate <= date_of_manufacturing)))\n";
        return AbstractQuery.query(sqlRequest, new AmountOfAllComponentsNeededForOrderRowMapper());
    }

    //10
    public static List<Technologies> getAllTechnologiesForSpecificDrugType(Type type) throws SQLException {
        String sqlRequest = "select t.PRODUCTION_ACTION as production, t.PRODUCTION_TIME as production_time, t.ID_TECHNOLOGY as id from TECHNOLOGIES t " +
                "where t.ID_DRUG in\n" +
                "(select md.ID_DRUG from MANUFACTURED_DRUG md where md.DRUG_TYPE=?)";

        List<Object> params = Collections.singletonList(type);
        List<Technologies> technologies = AbstractQuery.query(sqlRequest, params, new TechnologiesRowMapper());
        return technologies;
    }

    //10
    public static List<Technologies> getAllTechnologiesForSpecificDrugName(String name) throws SQLException {
        String sqlRequest = "select t.PRODUCTION_ACTION as production, t.PRODUCTION_TIME as production_time, t.ID_TECHNOLOGY as id " +
                "from TECHNOLOGIES t where t.ID_DRUG in\n" +
                "(select md.ID_DRUG from MANUFACTURED_DRUG md where md.name =? )";

        List<Object> params = Collections.singletonList(name);
        List<Technologies> technologies = AbstractQuery.query(sqlRequest, params, new TechnologiesRowMapper());
        return technologies;
    }

    //10
    public static List<Technologies> getAllTechnologiesForDrugInProduction() throws SQLException {
        String sqlRequest = "select t.PRODUCTION_ACTION as production, t.PRODUCTION_TIME as production_time, t.ID_TECHNOLOGY as id\n" +
                "       from TECHNOLOGIES t where t.ID_DRUG in\n" +
                "        (select p.id_drug\n" +
                "              from prescriptions p\n" +
                "              where p.id_prescript in\n" +
                "                    (select o.id_prescript from orders o\n" +
                "                    where sysdate <= date_of_manufacturing))";

        List<Technologies> technologies = AbstractQuery.query(sqlRequest, new TechnologiesRowMapper());

        return technologies;
    }

    //11
    public static List<InfoAboutDrugWithItsComponents> getInfoAboutDrugsWithItsComponents(String drugName) throws SQLException {
        String sqlRequest = "SELECT COMPONENTS.NAME as name, COMPONENTS.PRICE_PER_UNIT*CMPSTN.AMOUNT as component_price, CMPSTN.AMOUNT as amount FROM COMPONENTS\n" +
                "    JOIN (SELECT CMPSTN.ID_COMPONENT, CMPSTN.AMOUNT FROM COMPOSITION CMPSTN WHERE ID_DRUG IN\n" +
                "                    (SELECT MND.ID_DRUG FROM MANUFACTURED_DRUG MND WHERE MND.NAME = ?))" +
                "CMPSTN ON CMPSTN.ID_COMPONENT = COMPONENTS.ID_COMPONENT";

        List<Object> params = Collections.singletonList(drugName);
        List<InfoAboutDrugWithItsComponents> infoAboutDrugWithItsComponents = AbstractQuery.query(sqlRequest, params,
                new InfoAboutDrugWithItsComponentsRowMapper());

        return infoAboutDrugWithItsComponents;
    }

    //11
    public static Double getPriceOfSpecificDrug(String drugName) throws SQLException {
        String sqlRequest = " SELECT MND.PRICE_PER_UNIT as price FROM MANUFACTURED_DRUG MND WHERE MND.NAME = ?";
        List<Object> params = Collections.singletonList(drugName);
        List<Double> price = AbstractQuery.query(sqlRequest, params, new DrugPriceRowMapper());
        return price.get(0);
    }

    //12
    public static List<Buyer> getBuyerBySpecificDrugsTypeFreqOrdering(Type type) throws SQLException {
        String sqlRequest = "select count(b.ID) as count,\n" +
                "       b.ID, b.SURNAME, b.MIDDLENAME, b.NAME, b.DATE_OF_BIRTH, b.PHONE_NUMBER, b.ADDRESS\n" +
                "from BUYER b where b.ID in\n" +
                "(select p.ID_BUYER from PRESCRIPTIONS p where p.ID_DRUG in\n" +
                "(select md.ID_DRUG from MANUFACTURED_DRUG md where md.DRUG_TYPE =?))\n" +
                "and ROWNUM=10\n" +
                "group by b.ID, b.SURNAME, b.MIDDLENAME, b.NAME, b.DATE_OF_BIRTH, b.PHONE_NUMBER, b.ADDRESS " +
                "order by count(b.id) desc";

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
                "group by b.ID, b.SURNAME, b.MIDDLENAME, b.NAME, b.DATE_OF_BIRTH, b.PHONE_NUMBER, b.ADDRESS " +
                "order by count(b.id) desc";

        List<Object> params = Collections.singletonList(drugName);
        List<Buyer> buyers = AbstractQuery.query(sqlRequest, params, new BuyerRowMapper());

        return buyers;
    }

    //13
    public static InfoAboutDrug getInfoAboutSpecificDrug(String drugName) throws SQLException {
        String sqlRequest = "SELECT DI.type, DI.name, DI.price, DI.action, DI.AMOUNT FROM DRUG_INFO DI WHERE DI.name = ?";

        List<Object> params = Collections.singletonList(drugName);
        List<InfoAboutDrug> infoAboutDrugs = AbstractQuery.query(sqlRequest, params, new InfoAboutDrugRowMapper());
        return infoAboutDrugs.get(0);
    }

    //13
    public static List<Component> getAllComponentsByDrugName(String drugName) throws SQLException {
        String sqlRequest = "SELECT C.ID_COMPONENT as id, C.NAME as name, C.PRICE_PER_UNIT as price, C.UNIT as unit, C.TYPE as type FROM COMPONENTS C\n" +
                "    WHERE ID_COMPONENT IN \n" +
                "          (SELECT CMPST.ID_COMPONENT FROM COMPOSITION CMPST WHERE CMPST.ID_DRUG IN\n" +
                "                (SELECT MAN_DRUG.ID_DRUG FROM MANUFACTURED_DRUG MAN_DRUG WHERE MAN_DRUG.NAME =?))";

        List<Object> params = Collections.singletonList(drugName);
        List<Component> components = AbstractQuery.query(sqlRequest, params, new ComponentRowMapper());
        return components;
    }

}