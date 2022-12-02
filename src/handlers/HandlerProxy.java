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

public class HandlerProxy implements IHandler {
    private Map<HandlerName, AbstractHandler> handlers;
    private AbstractHandler currentHandler;

    public HandlerProxy() {
        AbstractHandler[] handlersArr = new AbstractHandler[] { new CancerHospitalDonationHandler(),
                new EtisalatInternetHandler(),
                new EtisalatRechargeHandler(), new MonthlyLandlineHandler(), new NgoDonationHandler(),
                new OrangeInternetHandler(), new OrangeRechargeHandler(), new QuarterlyLandlineHandler(),
                new SchoolDonationHandler(), new VodafoneInternetHandler(), new VodafoneRechargeHandler(),
                new WeInternetHandler(), new WeRechargeHandler(), new ErroneousHandler() };
        handlers = new HashMap<HandlerName, AbstractHandler>();
        for (AbstractHandler handler : handlersArr) {
            handlers.put(handler.getHandlerName(), handler);
        }
        this.currentHandler = handlers.get(HandlerName.ERRONEOUS);
    }

    public void setHandler(HandlerName handlerName) {
        if (!handlers.containsKey(handlerName)) {
            currentHandler = handlers.get(HandlerName.ERRONEOUS);
            return;
        }
        currentHandler = handlers.get(handlerName);
    }

    public HandlerName getHandlerName() {
        return currentHandler.getHandlerName();
    }

    public String[] getRequestKeys() {
        return currentHandler.getRequestKeys();
    }

    public String getConstraints() {
        return currentHandler.getConstraints();
    }

    public HandlerResponse validateAndHandleRequest(HashMap<String, String> request) {
        return currentHandler.validateAndHandleRequest(request);
    }
}
