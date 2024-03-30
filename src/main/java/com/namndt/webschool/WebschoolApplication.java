package com.namndt.webschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableFeignClients(basePackages = "com.namndt.webschool.proxy")
public class WebschoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebschoolApplication.class, args);
    }

}
