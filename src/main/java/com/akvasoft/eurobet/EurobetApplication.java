package com.akvasoft.eurobet;

import com.akvasoft.eurobet.config.Scrape;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EurobetApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EurobetApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }
}

