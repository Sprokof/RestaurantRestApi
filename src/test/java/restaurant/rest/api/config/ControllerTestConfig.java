package restaurant.rest.api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"restaurant.rest.api.repository.**", "restaurant.rest.api.service", "restaurant.rest.api.web"})
public class ControllerTestConfig {
}
