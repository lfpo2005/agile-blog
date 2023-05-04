package dev.fernando.agileblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class AgileBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgileBlogApplication.class, args);
    }

}
