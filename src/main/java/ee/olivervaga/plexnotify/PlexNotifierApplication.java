package ee.olivervaga.plexnotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlexNotifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlexNotifierApplication.class, args);
    }
}
