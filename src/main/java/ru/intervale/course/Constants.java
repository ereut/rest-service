package ru.intervale.course;

public class Constants {

    public static final String DATA_BASE_DAO =
            ApplicationContext.getPropertyValue("Ddatabase");
    public final static String DBURL =
            ApplicationContext.getPropertyValue("Ddburl");
    public final static String USER_NAME =
            ApplicationContext.getPropertyValue("Ddbuser");
    public final static String USER_PASSWORD =
            ApplicationContext.getPropertyValue("Ddbpassword");

    public final static String CREATE_CARDS_TABLE_QUERY =
            "CREATE TABLE `customers`.`cards` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `customerId` INT NOT NULL,\n" +
                    "  `pan` VARCHAR(45) NOT NULL,\n" +
                    "  `expiry` DATE NOT NULL,\n" +
                    "  `registerTime` DATETIME NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));\n";

    public final static String CREATE_PAYMENTS_TABLE_QUERY =
            "CREATE TABLE `customers`.`payments` (\n" +
                    "  `id` INT NOT NULL,\n" +
                    "  `cardId` INT NOT NULL,\n" +
                    "  `startTime` DATETIME NOT NULL,\n" +
                    "  `pfinishTime` DATETIME NOT NULL,\n" +
                    "  `value` INT NOT NULL,\n" +
                    "  `currency` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC));";



            ApplicationContext.getPropertyValue("QinsertCustomer");
    public final static String SELECT_CUSTOMER_ID_QUERY =
            ApplicationContext.getPropertyValue("QselectCustomerId");

}
