package ru.intervale.course.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Operations {

    private static Logger log = LoggerFactory.getLogger(Operations.class);

    public static void insertCustomer(Connection cn, String name, String surname,
                                      String telephoneNumber, String country,
                                      String city, String street, String homeNumber,
                                      String flatNumber) throws SQLException {
        if (!containsCustomer(cn, name, surname, telephoneNumber, country, city,
                street, homeNumber, flatNumber)) {
            PreparedStatement pst = cn.prepareStatement(Constants.INSERT_CUSTOMER_QUERY);
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, telephoneNumber);
            pst.setString(4, country);
            pst.setString(5, city);
            pst.setString(6, street);
            pst.setString(7, homeNumber);
            pst.setString(8, flatNumber);
            pst.executeUpdate();
        } else {
            log.error("Customer {} {} is already register", name, surname);
        }
    }

    public static boolean containsCustomer(Connection cn, String name, String surname,
    String telephoneNumber, String country,
    String city, String street, String homeNumber,
    String flatNumber) throws SQLException {
        PreparedStatement pst = cn.prepareStatement(Constants.SELECT_CUSTOMER_ID_QUERY);
        pst.setString(1, name);
        pst.setString(2, surname);
        pst.setString(3, telephoneNumber);
        pst.setString(4, country);
        pst.setString(5, city);
        pst.setString(6, street);
        pst.setString(7, homeNumber);
        pst.setString(8, flatNumber);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public static int selectCustomerId(Connection cn, String name, String surname,
                                       String telephoneNumber, String country,
                                       String city, String street, String homeNumber,
                                       String flatNumber) throws SQLException {
            PreparedStatement pst = cn.prepareStatement(Constants.SELECT_CUSTOMER_ID_QUERY);
            pst.setString(1, name);
            pst.setString(2, surname);
            pst.setString(3, telephoneNumber);
            pst.setString(4, country);
            pst.setString(5, city);
            pst.setString(6, street);
            pst.setString(7, homeNumber);
            pst.setString(8, flatNumber);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1);
        }

}


