package com.akvasoft.eurobet;

import com.akvasoft.eurobet.config.Scrape;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EurobetApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurobetApplication.class, args);
        Scrape scrape = new Scrape();
        try {
            scrape.scrapeOld();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

