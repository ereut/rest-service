package ru.intervale.course.dao;

import org.junit.Assert;
import org.junit.Test;
import ru.intervale.course.dao.interfaces.ICustomerDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class CustomerJDBCDaoTest {

    @Test
    public void create() throws Exception {
        try (Connection cn =
                     DriverManager.getConnection("jdbc:h2:tcp://localhost/~/customers", "sa","")) {
            cn.setAutoCommit(false);
            ICustomerDao customerJDBCDao = new CustomerJDBCDao(cn);
            int previousSize = customerJDBCDao.getAll().size();
            customerJDBCDao.create("Dmitriy","Duvan",
                    "295563212", "Belarus", "Minsk", "Suhoy street",
                    "23", "142");
            int afterCreationSize = customerJDBCDao.getAll().size();
            Assert.assertEquals(true, afterCreationSize > previousSize);
        }
    }

    @Test
    public void update() throws Exception {
        final String SELECT_ID_QUERY = "SELECT id FROM customers.customers";
        final String updateName = "TestName";
        try (Connection cn =
                     DriverManager.getConnection("jdbc:h2:tcp://localhost/~/customers", "sa","");
             PreparedStatement pst = cn.prepareStatement(SELECT_ID_QUERY)) {
            cn.setAutoCommit(false);
            ICustomerDao customerJDBCDao = new CustomerJDBCDao(cn);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            customerJDBCDao.update(id, updateName,"","",
                    "","","","","");
            Assert.assertEquals(updateName, customerJDBCDao.getEntityById(id).getName());
        }
    }

}