package payments.controllers.payment_strategies;

import payments.entities.Provider;

public interface PaymentStrategyValidator {
    public boolean validate(Provider provider);
}
