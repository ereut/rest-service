package ru.intervale.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.beans.AbstractEntity;
import ru.intervale.course.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Runner {

    private static void printEntities(List<? extends AbstractEntity> entitiesList) {
        for (AbstractEntity abstractEntity : entitiesList) {
            System.out.println(abstractEntity);
            System.out.println();
        }
    }

    private static Logger log = LoggerFactory.getLogger(Runner.class);

    public static void main(String args[]) {



        try (Connection cn = ConnectionFactory.getConnection()) {
            

        } catch (SQLException | DaoException e) {
            log.error(e.getMessage());
        }

    }
}
