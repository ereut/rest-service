package ru.intervale.course.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.dao.IDao;

public class AbstractJDBCDaoImplTest {

    private static IDao<Customer> customerIDao;

    private static Customer customer = new Customer("testUser", "testUser", null,
            null,null, null);
    private static Customer customerForUpdate = new Customer(null, "updatePassword", "Yauheni",
            null, null, null);

    private static Customer insertingCustomer;
    private static int insertingId;

    @BeforeClass
    public static void initData() throws Exception {
        customerIDao = new CustomerJDBCDaoImpl();
        insertingCustomer = customerIDao.persist(customer);
        insertingId = insertingCustomer.getId();
        customerForUpdate.setId(insertingId);
        customer.setPassword(DigestUtils.md2Hex("testUser"));
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