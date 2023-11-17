package restaurant.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"restaurant.rest.api.model", "restaurant.rest.api.repository.**"})
public class RestaurantApp {
    public static void main(String[] args) {
        SpringApplication.run(RestaurantApp.class);
    }
}