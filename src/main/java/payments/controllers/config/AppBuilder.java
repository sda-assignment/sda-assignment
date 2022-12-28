package payments.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

import datastore.Model;
import payments.controllers.auth.Authenticator;
import payments.controllers.interceptors.AdminInterceptor;
import payments.controllers.interceptors.AuthInterceptor;
import payments.entities.Discount;
import payments.entities.FormElement;
import payments.entities.FormElementChoice;
import payments.entities.Provider;
import payments.entities.RefundRequest;
import payments.entities.Service;
import payments.entities.Transaction;
import payments.entities.UsedDiscount;
import payments.entities.User;

@Configuration
public class AppBuilder {
    @Bean
    Authenticator authenticator() {
        return new Authenticator();
    }

    @Bean
    MappedInterceptor authInterceptor() {
        String[] exPaths = new String[] { "/login", "/signup", "/error" };
        return new MappedInterceptor(null, exPaths, new AuthInterceptor(authenticator()));
    }

    @Bean
    MappedInterceptor adminInterceptor() {
        String[] incPaths = new String[] { "/admin/*" };
        return new MappedInterceptor(incPaths, new AdminInterceptor());
    }

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
    public Model<FormElement> formElementModel() {
        return new Model<FormElement>();
    }

    @Bean
    public Model<FormElementChoice> formElementChoiceModel() {
        return new Model<FormElementChoice>();
    }

    @Bean
    public Model<Service> serviceModel() {
        return new Model<Service>();
    }
}
