package ru.intervale.course.dao;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AbstractJDBCDaoTest {
    @Test
    public void getEntityById() throws Exception {
        final String GET_ID_QUERY = "SELECT id FROM customers.customers";

        try (Connection cn =
                     DriverManager.getConnection("jdbc:h2:tcp://localhost/~/customers", "sa","");
             PreparedStatement pst = cn.prepareStatement(GET_ID_QUERY)) {

            ResultSet rs = pst.executeQuery();
            rs.next();
            int id = rs.getInt(1);
            Assert.assertNotNull(new CustomerJDBCDao(cn).getEntityById(id));

        }
    }

}