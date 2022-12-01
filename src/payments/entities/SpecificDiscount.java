package payments.entities;

import datastore.Entity;
import payments.Util;

public class SpecificDiscount implements Entity {
    public final int discountId;
    public final String serviceName;
    public final String providerName;

    public SpecificDiscount(int discountId, String serviceName, String providerName) {
        this.discountId = discountId;
        this.serviceName = serviceName;
        this.providerName = providerName;
    }

    public String storify() {
        return Util.separateWithColons(new Object[] { discountId, serviceName, providerName });
    }
}
