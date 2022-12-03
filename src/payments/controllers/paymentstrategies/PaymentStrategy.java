package payments.controllers.paymentstrategies;

import datastore.exceptions.EntitySaveException;
import payments.common.Response;

public interface PaymentStrategy {
    Response pay(double amount) throws EntitySaveException;
}
