package ru.intervale.course;

import ru.intervale.course.database.MySQLConnection;
import ru.intervale.course.database.Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Runner {

    public static void main(String args[]) {



        try (Connection cn = MySQLConnection.getMySQLConnection()) {
           PreparedStatement pst = cn.prepareStatement(Constants.CREATE_CUSTOMERS_TABLE_QUERY);
           pst.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        };

    }
}
