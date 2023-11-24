package restaurant.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "restaurant.rest.api.repository.**")
public class RestaurantApp {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApp.class);
    }
}