package ru.intervale.course.utils;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import ru.intervale.course.Constants;
import ru.intervale.course.filters.AddCardFilter;
import ru.intervale.course.filters.DeleteCustomerFilter;
import ru.intervale.course.servlets.*;

import javax.servlet.http.HttpServlet;
import java.io.File;

public class TomcatUtils {

    private final static String APPLICATION_CONTEXT_PATH = "/app";

    private static void addServletWithMapping(String urlPattern, HttpServlet servlet) {
        TOMCAT.addServlet(APPLICATION_CONTEXT_PATH, servlet.getClass().getSimpleName(), servlet);
        CONTEXT.addServletMappingDecoded(urlPattern, servlet.getServletName());
    }

    public static void runTomcatEmbedded() {

    }


    /*

     try {
            addServletWithMapping("/customer/add", new AddCustomerServlet());
            addServletWithMapping("/customer/update", new UpdateCustomerServlet());
            addServletWithMapping("/customer/delete", new DeleteCustomerServlet());
            addServletWithMapping("/customer/*", new GetCustomerServlet());

            addServletWithMapping("/card/add", new AddCardServlet());
            addServletWithMapping("/card/update", new UpdateCardServlet());
            addServletWithMapping("/card/delete", new DeleteCardServlet());
            addServletWithMapping("/card/*", new GetCardServlet());

            addServletWithMapping("/payment/add", new AddPaymentTrxServlet());
            addServletWithMapping("/payment/delete", new DeletePaymentTrxServlet());
            addServletWithMapping("/payment/*", new GetPaymentTrxServlet());

            addServletWithMapping("/session/start", new StartSessionServlet());
            addServletWithMapping("/session/finish", new FinishSessionServlet());
            TOMCAT.start();
            TOMCAT.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }

            FilterDef addCardFilterDef = new FilterDef();
            addCardFilterDef.setFilterName(AddCardFilter.class.getSimpleName());
            addCardFilterDef.setFilterClass(AddCardFilter.class.getName());
            context.addFilterDef(addCardFilterDef);

            FilterMap addCardFilterMap = new FilterMap();
            addCardFilterMap.setFilterName(AddCardFilter.class.getSimpleName());
            addCardFilterMap.addURLPattern(Constants.ADD_CARD_SERVLET_URL_PATTERN);
            context.addFilterMap(addCardFilterMap);

            ////////

            FilterDef deleteCustomerFilterDef = new FilterDef();
            deleteCustomerFilterDef.setFilterName(DeleteCustomerFilter.class.getSimpleName());
            deleteCustomerFilterDef.setFilterClass(DeleteCustomerFilter.class.getName());
            context.addFilterDef(deleteCustomerFilterDef);

            FilterMap deleteCustomerFilterMap = new FilterMap();
            deleteCustomerFilterMap.setFilterName(DeleteCustomerFilter.class.getSimpleName());
            deleteCustomerFilterMap.addURLPattern(Constants.DELETE_CUSTOMER_SERVLET_URL_PATTERN);
            context.addFilterMap(deleteCustomerFilterMap);

*/






}
