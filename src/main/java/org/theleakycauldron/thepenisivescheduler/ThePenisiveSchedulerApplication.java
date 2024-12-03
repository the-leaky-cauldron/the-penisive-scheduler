package org.theleakycauldron.thepenisivescheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ThePenisiveSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThePenisiveSchedulerApplication.class, args);
    }

}
