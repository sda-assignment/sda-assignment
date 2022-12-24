package payments.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import datastore.Model;
import payments.controllers.auth.LogInSession;
import payments.entities.Discount;
import payments.entities.Provider;
import payments.entities.Transaction;
import payments.entities.UsedDiscount;
import payments.entities.User;

@Configuration
public class ModelBuilder {
    @Bean
    public Model<User> userModel() {
        return new Model<User>();
    }

    @Bean
    public Model<Discount> discountModel() {
        return new Model<Discount>();
    }

    @Bean
    public Model<UsedDiscount> usedDiscountModel() {
        return new Model<UsedDiscount>();
    }

    @Bean
    public Model<Provider> providerModel() {
        return new Model<Provider>();
    }

    @Bean
    public Model<Transaction> transactionModel() {
        return new Model<Transaction>();
    }

    @Bean
    public LogInSession loginSession() {
        return new LogInSession();
    }
}
