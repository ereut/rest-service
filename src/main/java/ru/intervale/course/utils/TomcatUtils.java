package ru.intervale.course.utils;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import ru.intervale.course.servlets.*;

import java.io.File;

public class TomcatUtils {

    private static final String ADD_CUSTOMER_SERVLET_NAME = "AddCustomerServlet";
    private static final String UPDATE_CUSTOMER_SERVLET_NAME = "UpdateCustomerServlet";
    private static final String DELETE_CUSTOMER_SERVLET_NAME = "DeleteCustomerServlet";
    private static final String GET_CUSTOMER_SERVLET_NAME = "GetCustomerServlet";

    private static final String ADD_CARD_SERVLET_NAME = "AddCardServlet";
    private static final String UPDATE_CARD_SERVLET_NAME = "UpdateCardServlet";
    private static final String DELETE_CARD_SERVLET_NAME = "DeleteCardServlet";
    private static final String GET_CARD_SERVLET_NAME = "GetCardServlet";

    private static final String ADD_PAYMENT_SERVLET_NAME = "AddPaymentTrxServlet";
    private static final String DELETE_PAYMENT_SERVLET_NAME = "DeletePaymentTrxServlet";
    private static final String GET_PAYMENT_SERVLET_NAME = "GetPaymentTrxServlet";

    private static final String ADD_CUSTOMER_SERVLET_URL_PATTERN = "/customer/add";
    private static final String UPDATE_CUSTOMER_SERVLET_URL_PATTERN = "/customer/update";
    private static final String DELETE_CUSTOMER_SERVLET_URL_PATTERN = "/customer/delete";
    private static final String GET_CUSTOMER_SERVLET_URL_PATTERN = "/customer/*";

    private static final String ADD_CARD_SERVLET_URL_PATTERN = "/card/add";
    private static final String UPDATE_CARD_SERVLET_URL_PATTERN = "/card/update";
    private static final String DELETE_CARD_SERVLET_URL_PATTERN = "/card/delete";
    private static final String GET_CARD_SERVLET_URL_PATTERN = "/card/*";

    private static final String ADD_PAYMENT_SERVLET_URL_PATTERN = "/payment/add";
    private static final String DELETE_PAYMENT_SERVLET_URL_PATTERN = "/payment/delete";
    private static final String GET_PAYMENT_SERVLET_URL_PATTERN = "/payment/*";

    public static void runTomcatEmbedded() {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);
            String contextPath = "/app";
            String docBase = new File(".").getAbsolutePath();
            Context context = tomcat.addContext(contextPath, docBase);

            tomcat.addServlet(contextPath, ADD_CUSTOMER_SERVLET_NAME, new AddCustomerServlet());
            tomcat.addServlet(contextPath, UPDATE_CUSTOMER_SERVLET_NAME, new UpdateCustomerServlet());
            tomcat.addServlet(contextPath, DELETE_CUSTOMER_SERVLET_NAME, new DeleteCustomerServlet());
            tomcat.addServlet(contextPath, GET_CUSTOMER_SERVLET_NAME, new GetCustomerServlet());

            context.addServletMappingDecoded(ADD_CUSTOMER_SERVLET_URL_PATTERN, ADD_CUSTOMER_SERVLET_NAME);
            context.addServletMappingDecoded(UPDATE_CUSTOMER_SERVLET_URL_PATTERN, UPDATE_CUSTOMER_SERVLET_NAME);
            context.addServletMappingDecoded(DELETE_CUSTOMER_SERVLET_URL_PATTERN, DELETE_CUSTOMER_SERVLET_NAME);
            context.addServletMappingDecoded(GET_CUSTOMER_SERVLET_URL_PATTERN, GET_CUSTOMER_SERVLET_NAME);

            tomcat.addServlet(contextPath, ADD_CARD_SERVLET_NAME, new AddCardServlet());
            tomcat.addServlet(contextPath, UPDATE_CARD_SERVLET_NAME, new UpdateCardServlet());
            tomcat.addServlet(contextPath, DELETE_CARD_SERVLET_NAME, new DeleteCardServlet());
            tomcat.addServlet(contextPath, GET_CARD_SERVLET_NAME, new GetCardServlet());

            context.addServletMappingDecoded(ADD_CARD_SERVLET_URL_PATTERN, ADD_CARD_SERVLET_NAME);
            context.addServletMappingDecoded(UPDATE_CARD_SERVLET_URL_PATTERN, UPDATE_CARD_SERVLET_NAME);
            context.addServletMappingDecoded(DELETE_CARD_SERVLET_URL_PATTERN, DELETE_CARD_SERVLET_NAME);
            context.addServletMappingDecoded(GET_CARD_SERVLET_URL_PATTERN, GET_CARD_SERVLET_NAME);

            tomcat.addServlet(contextPath, ADD_PAYMENT_SERVLET_NAME, new AddPaymentTrxServlet());
            tomcat.addServlet(contextPath, DELETE_PAYMENT_SERVLET_NAME, new DeletePaymentTrxServlet());
            tomcat.addServlet(contextPath, GET_PAYMENT_SERVLET_NAME, new GetPaymentTrxServlet());

            context.addServletMappingDecoded(ADD_PAYMENT_SERVLET_URL_PATTERN, ADD_PAYMENT_SERVLET_NAME);
            context.addServletMappingDecoded(DELETE_PAYMENT_SERVLET_URL_PATTERN, DELETE_PAYMENT_SERVLET_NAME);
            context.addServletMappingDecoded(GET_PAYMENT_SERVLET_URL_PATTERN, GET_PAYMENT_SERVLET_NAME);

            tomcat.start();
            tomcat.getServer().await();

        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

}
