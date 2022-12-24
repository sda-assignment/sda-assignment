package payments.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import datastore.Model;
import payments.controllers.auth.LogInSession;
import payments.entities.User;

@Configuration
public class AppConfig {
    @Bean
    public Model<User> userModel() {
        return new Model<User>();
    }

    @Bean
    public LogInSession loginSession() {
        return new LogInSession();
    }
}
