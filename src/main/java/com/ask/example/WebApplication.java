package com.ask.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan("com.ask.example")
public class WebApplication {

    public static void main(String[] args) {
        final ConfigurableApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args);

        String[] allBeans = applicationContext.getBeanDefinitionNames();
        for(String bean : allBeans) {
            if (bean.toLowerCase().endsWith("webmvcautoconfiguration"))
                System.out.println(bean);
        }
        System.out.println("Total number of beans "+applicationContext.getBeanDefinitionCount());

    }
}
