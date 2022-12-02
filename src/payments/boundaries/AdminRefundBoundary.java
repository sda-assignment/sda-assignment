package payments.boundaries;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import payments.entities.RefundRequest;
import payments.controllers.AdminRefundController;
import payments.entities.Transaction;
import datastore.Relation;
import datastore.exceptions.EntitySaveException;
import payments.entities.User;

public class AdminRefundBoundary {
    private AdminRefundController aRefund;

    public AdminRefundBoundary(Relation<RefundRequest> refundRelation, Relation<User> userRelation,
            Relation<Transaction> transactionRelation) {
        this.aRefund = new AdminRefundController(refundRelation, userRelation, transactionRelation);
    }

    public void displayRequests() {
        ArrayList<RefundRequest> refundRequests = aRefund.getRefundRequests();
        Formatter fmt = new Formatter();

        fmt.format("%15s %15s %15s %15s\n", "id", "transactionId", "status", "User Email");

        for (RefundRequest request : refundRequests) {
            fmt.format("%15s %15s %15s %15s\n", request.id, request.transactionId, request.status,
                    request.userEmail);

        }
        System.out.println(fmt);
    }

    public void handleRequest() throws EntitySaveException {
        Scanner adminInput = new Scanner(System.in);

        System.out.println("which Request do you want to handle");
        System.out.print("Enter Request id: ");
        int requestId = adminInput.nextInt();

        System.out.println("enter the response for the refund request");
        System.out.println("1- Accepted\n2- Rejected");
        int refundRequestStatus = adminInput.nextInt();

        if (refundRequestStatus == 1) {
            aRefund.acceptRefund(requestId);
        }

        else if (refundRequestStatus == 2) {
            aRefund.rejectRefund(requestId);
        }

        else {
            System.out.println("please enter a valid choice");
        }
        adminInput.close();

    }

}
