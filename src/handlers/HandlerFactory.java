package handlers;

import java.util.HashMap;
import java.util.Map;

import handlers.concrete.CancerHospitalDonationHandler;
import handlers.concrete.ErroneousHandler;
import handlers.concrete.EtisalatInternetHandler;
import handlers.concrete.EtisalatRechargeHandler;
import handlers.concrete.MonthlyLandlineHandler;
import handlers.concrete.NgoDonationHandler;
import handlers.concrete.OrangeInternetHandler;
import handlers.concrete.OrangeRechargeHandler;
import handlers.concrete.QuarterlyLandlineHandler;
import handlers.concrete.SchoolDonationHandler;
import handlers.concrete.VodafoneInternetHandler;
import handlers.concrete.VodafoneRechargeHandler;
import handlers.concrete.WeInternetHandler;
import handlers.concrete.WeRechargeHandler;

public class HandlerFactory {
    private Map<HandlerName, Handler> handlers;

    public HandlerFactory() {
        Handler[] handlersArr = new Handler[] { new CancerHospitalDonationHandler(),
                new EtisalatInternetHandler(),
                new EtisalatRechargeHandler(), new MonthlyLandlineHandler(), new NgoDonationHandler(),
                new OrangeInternetHandler(), new OrangeRechargeHandler(), new QuarterlyLandlineHandler(),
                new SchoolDonationHandler(), new VodafoneInternetHandler(), new VodafoneRechargeHandler(),
                new WeInternetHandler(), new WeRechargeHandler() };
        handlers = new HashMap<HandlerName, Handler>();
        for (Handler handler : handlersArr) {
            handlers.put(handler.getHandlerName(), handler);
        }
    }

    public Handler getHandler(HandlerName handlerName) {
        if (!handlers.containsKey(handlerName))
            return new ErroneousHandler();
        return handlers.get(handlerName);
    }

}
