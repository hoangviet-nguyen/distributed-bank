package bank.servlet;


import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;


public class App {
    public static void main( String[] args ) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.setBaseDir("target");

        String webAppDir = "src/main/webapp";;
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webAppDir).getAbsolutePath());

        File classFiles = new File("target/classes/");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(
            new DirResourceSet(
                resources,
                "/WEB-INF/classes",
                classFiles.getAbsolutePath(),
                "/"
        ));
        ctx.setResources(resources);
        tomcat.getConnector(); // creates the default connector
        tomcat.start();
        tomcat.getServer().await();
    }
}
