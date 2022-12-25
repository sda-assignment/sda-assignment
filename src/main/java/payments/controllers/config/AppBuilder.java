package payments.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import datastore.Model;
import payments.controllers.auth.TokenUtil;
import payments.entities.Discount;
import payments.entities.Provider;
import payments.entities.RefundRequest;
import payments.entities.Transaction;
import payments.entities.UsedDiscount;
import payments.entities.User;

@Configuration
public class AppBuilder {
    @Bean
    public Model<User> userModel() {
        Model<User> userModel = new Model<User>();
        userModel.insert(new User("admin", "admin", "admin", true, 0));
        return userModel;
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
    public Model<RefundRequest> refundRequestModel() {
        return new Model<RefundRequest>();
    }

    @Bean
    public TokenUtil tokenUtil() {
        return new TokenUtil();
    }
}
