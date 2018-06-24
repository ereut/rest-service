package ru.intervale.course.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.DaoException;
import ru.intervale.course.dao.IDao;
import ru.intervale.course.utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractJDBCDaoImplTest {

    private static Connection cn;

    private static Connection getConnection() throws DaoException {
        if (cn == null) {
            try {
                cn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/customers", "sa", "");
                DatabaseUtils.runLiquibase(cn);
                cn.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DaoException("Problems with connection to database during tests ", e);
            }
        }
        return cn;
    }

    private static IDao<Customer> customerIDao;

    private static Customer customer = new Customer("testUser", "testUser", null,
            null,null, null);
    private static Customer customerForUpdate = new Customer(null, "updatePassword", "Yauheni",
            null, null, null);

    private static Customer insertingCustomer;
    private static int insertingId;

    @BeforeClass
    public static void initData() throws Exception {
        customerIDao = new CustomerJDBCDaoImpl(getConnection());
        insertingCustomer = customerIDao.persist(customer);
        insertingId = insertingCustomer.getId();
        customerForUpdate.setId(insertingId);
        customer.setPassword(DigestUtils.md2Hex("testUser"));
    }

    @AfterClass
    public static void closeConnection() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Test
    public void persist() throws Exception {
        Assert.assertNotNull(insertingCustomer);
        Assert.assertEquals(true, customer.equals(insertingCustomer));
    }

    @Test
    public void getEntityById() throws Exception {
        Assert.assertEquals(true, customer.equals(customerIDao.getEntityById(insertingId)));
    }

    @Test
    public void update() throws Exception {
        Assert.assertEquals(true, customerIDao.update(customerForUpdate));
    }

}