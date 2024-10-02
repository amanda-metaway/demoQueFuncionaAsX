package com.example.demo;

import com.example.demo.Service.PetService;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.catalina.Context;
import org.apache.catalina.webresources.StandardRoot;
import java.io.File;

public class DemoApplicationServlet {
    private PetService petService;
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");


        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8081);


        String webappDirLocation = new File("src/main/webapp/").getAbsolutePath() + "/";
        Context contextTomcat = tomcat.addWebapp("/app", webappDirLocation);

        StandardRoot standardRoot = new StandardRoot(contextTomcat);
        contextTomcat.setResources(standardRoot);



        tomcat.start();
        tomcat.getServer().await();
    }
}
