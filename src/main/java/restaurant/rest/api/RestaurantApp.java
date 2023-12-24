package restaurant.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EntityScan("restaurant.rest.api.model")
@ImportResource("classpath:/spring/security.xml")
public class RestaurantApp {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApp.class);
    }
}