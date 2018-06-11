package ru.intervale.course.dao;

import org.junit.*;
import ru.intervale.course.beans.Customer;
import ru.intervale.course.impl.CustomerJDBCDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractJDBCDaoImplTest {

    private static Connection cn;

    private static Connection getConnection() throws DaoException {
        if (cn == null) {
            try {
                cn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/customers", "sa", "");
                cn.setAutoCommit(false);
            } catch (SQLException e) {
                throw new DaoException("Problems with connection to database during tests ", e);
            }
        }
        return cn;
    }


    private static IDao<Customer> customerIDao;
    private static Customer customer = new Customer("Yuri","Strel'chenia","298254789",
                                    "Belarus", "Gomel", "Golovackiy street",
                                    "136", "15");
    private static Customer customerForUpdate = new Customer("Olia","Minnaya","293256987",
                                             "Belarus", "Minsk", "Pushkin street",
                                             "22", "12");
    private static Customer insertingCustomer;
    private static int insertingId;

   @BeforeClass
   public static void initData() throws Exception {
       customerIDao = new CustomerJDBCDaoImpl(getConnection());
       insertingCustomer = customerIDao.persist(customer);
       insertingId = insertingCustomer.getId();
       customerForUpdate.setId(insertingId);
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
       Assert.assertEquals(true, customerForUpdate.equals(customerIDao.getEntityById(insertingId)));

    }

}