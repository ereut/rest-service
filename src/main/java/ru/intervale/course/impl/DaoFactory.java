package ru.intervale.course.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.intervale.course.ApplicationContext;

import java.util.MissingResourceException;

public class DaoFactory {

    private static Logger log = LoggerFactory.getLogger(DaoFactory.class);

    private enum DaoEnum {
        H2 {
            @Override
            IDao getDaoIml() {
                return new H2DaoImpl();
            }
        }, MY_SQL {
            @Override
            IDao getDaoIml() {
                return new MySQLDaoImpl();
            }
        };

       abstract IDao getDaoIml();
    }

    public static IDao getDaoFromFactory() {
        String dao;
        try {
            dao = ApplicationContext.getPropertyValue("Ddatabase");
        } catch (MissingResourceException e) {
            final String DEFAULT_DATABASE_VALUE = "my_sql";
            dao = DEFAULT_DATABASE_VALUE;
            log.debug("Key Ddatabase was not found, set default value {}",
                    DEFAULT_DATABASE_VALUE);
        }
        return DaoEnum.valueOf(dao.toUpperCase()).getDaoIml();

    }

}
