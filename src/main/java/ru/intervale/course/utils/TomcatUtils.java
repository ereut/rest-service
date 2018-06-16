package ru.intervale.course.utils;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import ru.intervale.course.servlets.CardServlet;
import ru.intervale.course.servlets.CustomerServlet;
import ru.intervale.course.servlets.PaymentTrxServlet;
import ru.intervale.course.servlets.SessionStartServlet;

import java.io.File;

public class TomcatUtils {

    private static final String CUSTOMER_SERVLET_NAME = "CustomerServlet";
    private static final String CARD_SERVLET_NAME = "CardServlet";
    private static final String PAYMENT_TRX_SERVLET_NAME = "PaymentTrxServlet";
    private static final String SESSION_START_SERVLET_NAME = "SessionStartServlet";

    private static final String CUSTOMER_SERVLET_URL_PATTERN = "/customer/*";
    private static final String CARD_SERVLET_URL_PATTERN = "/card/*";
    private static final String PAYMENT_TRX_SERVLET_URL_PATTERN = "/payment/*";
    private static final String SESSION_START_SERVLET_URL_PATTERN = "/session/start";


    public static void runTomcatEmbedded() {
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);
            String contextPath = "/app";
            String docBase = new File(".").getAbsolutePath();
            Context context = tomcat.addContext(contextPath, docBase);

            tomcat.addServlet(contextPath, CUSTOMER_SERVLET_NAME, new CustomerServlet());
            tomcat.addServlet(contextPath, CARD_SERVLET_NAME, new CardServlet());
            tomcat.addServlet(contextPath, PAYMENT_TRX_SERVLET_NAME, new PaymentTrxServlet());
            tomcat.addServlet(contextPath, SESSION_START_SERVLET_NAME, new SessionStartServlet());

            context.addServletMappingDecoded(CUSTOMER_SERVLET_URL_PATTERN, CUSTOMER_SERVLET_NAME);
            context.addServletMappingDecoded(CARD_SERVLET_URL_PATTERN, CARD_SERVLET_NAME);
            context.addServletMappingDecoded(PAYMENT_TRX_SERVLET_URL_PATTERN, PAYMENT_TRX_SERVLET_NAME);
            context.addServletMappingDecoded(SESSION_START_SERVLET_URL_PATTERN, SESSION_START_SERVLET_NAME);

            tomcat.start();
            tomcat.getServer().await();

        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

}
