package Myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class WebApplication{
    // Tomcat需要主类有一个无参构造器


    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}
