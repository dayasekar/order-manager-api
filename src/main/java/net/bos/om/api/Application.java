package net.bos.om.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String... args) {
        log.info("Starting application");
        SpringApplication.run(Application.class);
        log.info("Started application");
    }
}
