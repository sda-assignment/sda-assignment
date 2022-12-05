package payments.boundaries.concretes.admin;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import payments.entities.RefundRequest;
import payments.controllers.admin.AdminRefundController;
import datastore.exceptions.EntityException;
import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.common.Response;

public class AdminRefund extends Frame {
    private AdminRefundController aRefund;
    private int refundRequestStatus;
    private int requestId;
    private Scanner adminInput;

    public AdminRefund(AdminRefundController aRefund) {
        this.aRefund = aRefund;
    }

    public FrameName getFrameName() {
        return FrameName.ADMIN_REFUND;
    }

    protected FrameName display() throws EntityException {

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
        System.out.println("Enter Request id: ");
        String input = adminInput.nextLine();

        if (input.equals("#")) {
            return FrameName.HOME_ADMIN;
        }
        requestId = Integer.valueOf(input);

        System.out.println("enter the response for the refund request");
        System.out.println("1- Accepted\n2- Rejected");
        refundRequestStatus = adminInput.nextInt();
        adminInput.close();
        if (refundRequestStatus == 1) {
            Response object = aRefund.acceptRefund(requestId);
            System.out.println(object.value);
            if (object.success) {
                return FrameName.HOME_ADMIN;
            }
            return FrameName.ADMIN_REFUND;

        } else if (refundRequestStatus == 2) {
            Response object = aRefund.rejectRefund(requestId);
            System.out.println(object.value);
            if (object.success) {
                return FrameName.HOME_ADMIN;
            }
            return FrameName.ADMIN_REFUND;

        } else {
            System.out.println("Please enter valid input ");
            return FrameName.ADMIN_REFUND;
        }

    }

}
