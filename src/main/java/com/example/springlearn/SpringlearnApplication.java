package com.example.springlearn;

import com.example.springlearn.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@SpringBootApplication
@Import(WebConfig.class)
public class SpringlearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringlearnApplication.class, args);
    }

}
