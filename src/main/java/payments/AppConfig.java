package payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import datastore.Relation;
import payments.controllers.LogInSession;
import payments.entities.User;

@Configuration
public class AppConfig {
    @Bean
    public Relation<User> userRelation() {
        return new Relation<User>();
    }

    @Bean
    public LogInSession loginSession() {
        return new LogInSession();
    }
}
