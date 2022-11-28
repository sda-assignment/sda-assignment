package payments.entities;

import datastore.Entity;
import payments.entities.enums.RefundRequestStatus;

public class RefundRequest implements Entity {
    public final int id;
    public final double amount;
    public final String serviceName;
    public final RefundRequestStatus status;

    public RefundRequest(int id, double amount, String serviceName, RefundRequestStatus status) {
        this.id = id;
        this.amount = amount;
        this.serviceName = serviceName;
        this.status = status;
    }

    public String storify() {
        return id + ":" + amount + ":" + serviceName + ":" + status;
    }
}
