package ru.intervale.course;

public class Constants {

    public static final String DATA_BASE_DAO =
            ApplicationContext.getPropertyValue("Ddatabase");
    public static final String MY_SQL_DRIVAER_CLASS_NAME =
            ApplicationContext.getPropertyValue("DmySQLDriverClassName");
    public final static String DBURL =
            ApplicationContext.getPropertyValue("Ddburl");
    public final static String USER_NAME =
            ApplicationContext.getPropertyValue("Ddbuser");
    public final static String USER_PASSWORD =
            ApplicationContext.getPropertyValue("Ddbpassword");

    public final static String CREATE_CUSTOMERS_TABLE_QUERY =
            ApplicationContext.getPropertyValue("QcreateCustomersTable");
    public final static String INSERT_CUSTOMER_QUERY =
            ApplicationContext.getPropertyValue("QinsertCustomer");
    public final static String SELECT_CUSTOMER_ID_QUERY =
            ApplicationContext.getPropertyValue("QselectCustomerId");

}
