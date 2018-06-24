package ru.intervale.course.utils;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.File;

public class TomcatUtils {

    public static void runTomcatEmbedded() {
        try {
            String webappDirLocation = "src/main/webapp/";
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);

            StandardContext ctx = (StandardContext) tomcat.addWebapp("/app",
                    new File(webappDirLocation).getAbsolutePath());

            File additionWebInfClasses = new File("target/classes");
            WebResourceRoot resources = new StandardRoot(ctx);
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                    additionWebInfClasses.getAbsolutePath(), "/"));
            ctx.setResources(resources);
            ((StandardJarScanner) ctx.getJarScanner()).setScanAllDirectories(true);

            tomcat.start();
            tomcat.getServer().await();

        } catch (LifecycleException | ServletException e) {
            LoggerFactory.getLogger(TomcatUtils.class).error("Problems with start Tomcat");
        }
    }
}






