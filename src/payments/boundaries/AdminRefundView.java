package payments.boundaries;

import java.util.ArrayList;
import java.util.Formatter;

import payments.entities.RefundRequest;
import payments.controllers.AdminRefundController;
import payments.entities.Transaction;
import datastore.Relation;
import payments.entities.User;

public class AdminRefundView {
    private AdminRefundController aRefund;


    public AdminRefundView(Relation<RefundRequest> refundRelation, Relation<User> userRelation, Relation<Transaction> transactionRelation){
        this.aRefund = new AdminRefundController(refundRelation, userRelation, transactionRelation);
    }

    public void displayRequests(){
        ArrayList<RefundRequest> refundRequests = aRefund.getRefundRequests();
        Formatter fmt = new Formatter();

        fmt.format("%15s %15s %15s %15s %15s \n", "id", "amount", "transactionId" ,"status", "User Email");



        for(RefundRequest request: refundRequests){
            fmt.flush();
            fmt.format("%15s %15s %15s %15s %15s\n", request.id, request.amount, request.transactionId, request.status, request.userEmail);

        }
        System.out.println(fmt);
    }
}
