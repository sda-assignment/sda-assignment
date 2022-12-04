package payments.boundaries.concretes.user;

import payments.controllers.RefundController;
import payments.controllers.TransactionController;
import java.util.ArrayList;
import java.util.Scanner;
import common.Util;
import datastore.exceptions.EntityException;
import payments.common.Response;
import payments.common.enums.TransactionType;
import payments.entities.Transaction;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;

public class RefundRequest extends Frame {
    private RefundController refundController;
    private TransactionController transactionController;

    public RefundRequest(RefundController refundController, TransactionController transactionController) {
        this.refundController = refundController;
        this.transactionController = transactionController;
    }

    public FrameName getFrameName() {
        return FrameName.HOME_USER;
    }

    protected FrameName display() throws EntityException {
        ArrayList<Transaction> array = transactionController.getTransactionsForUser();
        for (Transaction element : array) {
            System.out.println("id : " + element.id + " userEmail : " + element.userEmail +
                    " timestamp : " + element.timestamp + " amount : " + element.amount + " $ type : " + element.type
                    + " serviceName : " + element.serviceName + " provider Name : " + element.providerName);
        }

        System.out.println("enter  id : ");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        input.close();

        for (Transaction element : array)
        {
            if (element.id == Integer.parseInt(option))
            {
                if (element.type == TransactionType.REFUND)
                {
                    System.out.println("Error : Can't refund a transaction of type (refund) ");
                    return this.getFrameName();
                    
                }
            }
        } 

        if (option.equals("#")) {
            return FrameName.HOME_USER;
        } else if (Util.isPositiveInt(option)) {
            Response object = refundController.requestRefund(Integer.parseInt(option));
            System.out.println(object.value);
            if (object.success) {
                return FrameName.REFUND_REQUEST;
            } else {
                return FrameName.REFUND_REQUEST;
            }

        } else {
            System.out.println("Please enter a valid input ");
            return FrameName.REFUND_REQUEST;
        }

    }

}