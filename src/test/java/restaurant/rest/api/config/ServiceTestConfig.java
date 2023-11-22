package config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"restaurant.rest.api.repository.**", "restaurant.rest.api.service"})
public class ServiceTestConfig {

}
