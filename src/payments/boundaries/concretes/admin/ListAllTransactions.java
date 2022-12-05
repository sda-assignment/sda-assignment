package payments.boundaries.concretes.admin;

import java.util.ArrayList;
import java.util.Scanner;

import payments.boundaries.Frame;
import payments.boundaries.FrameName;
import payments.controllers.admin.AdminTransactionController;
import payments.entities.Transaction;
public class ListAllTransactions extends Frame{
    private AdminTransactionController adminTransactionController;

    public ListAllTransactions(AdminTransactionController adminTransactionController)
    {
        this.adminTransactionController = adminTransactionController;
    }

    @Override
    public FrameName getFrameName() {
        return FrameName.LIST_ALL_TRANSACTIONS;
    }

    //int id, String email, LocalDateTime timestamp, double amount, TransactionType type,
    //String serviceName, String providerName
    @Override
    protected FrameName display() {
        System.out.format("%15s","List all transactions ");
        System.out.println();
        ArrayList <Transaction> transactions = adminTransactionController.getAllTransactions();
        for (Transaction element : transactions)
        {
            System.out.print("Id : " + element.id + " Email : "+ element.userEmail +" Time Stamp : " + element.timestamp + " Amount : " + element.amount + " type : " + element.type +
            " Service Name : " + element.serviceName + "Provider Name : " + element.providerName + " \n");
        }

        System.out.print("Go Back to home (only option, press any key ) :  ");
        Scanner input = new Scanner(System.in);
        String option = input.nextLine();
        input.close();
        return FrameName.HOME_ADMIN;
    }
    
    
}
