package payments.boundaries.concretes.user;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.common.Response;
import payments.controllers.PaymentController;
import payments.controllers.ProviderController;
import payments.entities.FormElement;

public class PayForService extends Frame {
    PaymentController paymentController;
    ProviderController providerController;

    public PayForService(PaymentController paymentController, ProviderController providerController)
    {
        this.paymentController = paymentController;
        this.providerController = providerController;
    }

    @Override
    public FrameName getFrameName() {
        return FrameName.PAYMENT_METHOD;
    }

    @Override
    protected FrameName display(Scanner input) throws EntityException {
        System.out.format("%15s","Pay For Service \n");
        System.out.println("Enter the name of the service you want to pay (case sensitive): ");
        String serviceName = input.nextLine();
        if (serviceName.equals("#")) {
            return FrameName.HOME_USER;
        }
        System.out.println("Enter the name of the service provider you want to pay to (case sensitive): ");
        String providerName = input.nextLine();

        ArrayList<FormElement> fe = providerController.getProviderFormElements(serviceName, providerName);
        if (fe.size() == 0) {
            System.out.println("Service name doesn't exist");
            return FrameName.HOME_USER;
        }

        HashMap<String, String> request = new HashMap<String, String>();

        for (FormElement element : fe) {
            System.out.println(element.info);
            String option = input.nextLine();
            request.put(element.name, option);
        }

        System.out.println("Enter a payment method :\n1. Credit Card\n2. Wallet");
        if (providerController.supportsCashOnDelivery(serviceName, providerName)) {
            System.out.println("3. Cash on delivery \n");
        }
        System.out.println("choice : ");
        String option = input.nextLine();
        if (option.equals("2")) {
            Response response = paymentController.payUsingWallet(serviceName, providerName, request);
            System.out.println(response.value);
        } else if (option.equals("3")) {
            Response response = paymentController.payCashOnDelivery(serviceName, providerName, request);
            System.out.println(response.value);
        }
        else {
            System.out.println("Enter credit card number:");
            String cardNumber = input.nextLine();
            Response response = paymentController.payUsingCreditCard(serviceName, providerName, request, cardNumber);
            System.out.println(response.value);
        }

        return FrameName.HOME_USER;
    }

}
