package payments.boundaries;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import payments.entities.RefundRequest;
import payments.controllers.admin.AdminRefundController;
import datastore.exceptions.EntityException;
import payments.boundaries.EnumViews.FrameName;

public class AdminRefundView extends Frame {
    private AdminRefundController aRefund;
    private int refundRequestStatus;
    private int requestId;
    private Scanner adminInput;

    public AdminRefundView(AdminRefundController aRefund) {
        this.aRefund = aRefund;
        frameName = "adminRefundView";
    }

    public FrameName display() throws EntityException {

        ArrayList<RefundRequest> refundRequests = aRefund.getRefundRequests();
        Formatter fmt = new Formatter();

        fmt.format("%15s %15s %15s %15s\n", "id", "transactionId", "status", "User Email");

        for (RefundRequest request : refundRequests) {
            fmt.format("%15s %15s %15s %15s\n", request.id, request.transactionId, request.status,
                    request.userEmail);

        }
        System.out.println(fmt);

        adminInput = new Scanner(System.in);
        System.out.println("which Request do you want to handle");
        System.out.print("Enter Request id: ");
        String input = adminInput.nextLine();

        if (input.equals("#")) {
            return FrameName.homeAdmin;
        } else {
            requestId = Integer.valueOf(input);

            System.out.println("enter the response for the refund request");
            System.out.println("1- Accepted\n2- Rejected");
            refundRequestStatus = adminInput.nextInt();
            if (input.equals("1") || input.equals("2")) {
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

                return FrameName.homeAdmin;

            } else {
                return FrameName.error;
            }

        }

    }

}
