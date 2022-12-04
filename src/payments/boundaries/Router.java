package payments.boundaries;

import java.util.HashMap;
import java.util.LinkedList;

import datastore.exceptions.EntityException;
import handlers.HandlerName;
// import handlers.concrete.CancerHospitalDonationHandler;
// import handlers.concrete.ErroneousHandler;
// import handlers.concrete.EtisalatInternetHandler;
// import handlers.concrete.EtisalatRechargeHandler;
// import handlers.concrete.MonthlyLandlineHandler;
// import handlers.concrete.NgoDonationHandler;
// import handlers.concrete.OrangeInternetHandler;
// import handlers.concrete.OrangeRechargeHandler;
// import handlers.concrete.QuarterlyLandlineHandler;
// import handlers.concrete.SchoolDonationHandler;
// import handlers.concrete.VodafoneInternetHandler;
// import handlers.concrete.VodafoneRechargeHandler;
// import handlers.concrete.WeInternetHandler;
// import handlers.concrete.WeRechargeHandler;

public class Router {

    private LinkedList<Frame> frames;

    // private Map<Frame,> handlers;

    // public Router() {
    //     Handler[] handlersArr = new Handler[] { new CancerHospitalDonationHandler(),
    //             new EtisalatInternetHandler(),
    //             new EtisalatRechargeHandler(), new MonthlyLandlineHandler(), new NgoDonationHandler(),
    //             new OrangeInternetHandler(), new OrangeRechargeHandler(), new QuarterlyLandlineHandler(),
    //             new SchoolDonationHandler(), new VodafoneInternetHandler(), new VodafoneRechargeHandler(),
    //             new WeInternetHandler(), new WeRechargeHandler() };
    //     handlers = new HashMap<HandlerName, Handler>();
    //     for (Handler handler : handlersArr) {
    //         handlers.put(handler.getHandlerName(), handler);
    //     }
    // }

    // public Handler getHandler(HandlerName handlerName) {
    //     if (!handlers.containsKey(handlerName))
    //         return new ErroneousHandler();
    //     return handlers.get(handlerName);
    // }

    private Map <FrameName, Frame> Frames;

    public Router ()
    {
        Frame[] frameArr = new Frame []{
            
        }
    }










    public Router(Frame Home) {
        frames = new LinkedList<Frame>();
        frames.add(Home);
    }

    public void addFrame(Views view)
    {
        Frame obj = 
        frames.add(frame);
    }

    public void setNext(Frame frame)
    {
        frames.add(frame);
    }

    public void getPrevious ()
    {
        //ListIterator framesIterator = frames.listIterator(0);
        
    }

    public boolean display () throws EntityException
    {
        Views view = frames.getLast().display();
        // how to find if user is admin without accessing the 
        if (Views.homeUser == view)
        {
            return false;
        }
        else 
        {
            this.addFrame(view);

        }
    }

    // // public void setFrame(String frameName) {
    // //     if (!frames.containsKey(frameName)) {
    // //         currentFrame = frames.get("Home");
    // //         return;
    // //     }
    // //     currentFrame = frames.get(frameName);
    // // }

    // Guest View (#1)
    // Sign In (#2)
    // Sign Up (#3)
    // ----------------------------

    // Home User (#4)

    // Search Service Provider (#5)
    // Specific Service Provider Screen (#6)
    // Payment method Screen (#7)
    // ********************************
    // Refund Request Screen (#8)
    // ********************************
    // Add Amount to Wallet (#9)
    // Adding Result screen (#10)
    // ********************************
    // Discount Screen (#11)

    // ------------------------------

    // Home Admin (#12)

    // Choose Service of Sp to add Screen (#13)
    // Enter provider details Screen (#14)
    // Enter form elements for provider (#15)
    // Service provider addition result scree (#16)
    // ********************************
    // Add discount screen (#17)
    // Overall discount follow up screen (#18)
    // Specific discount follow up screen (#19)
    // discount result screen (#20)
    // **********************************
    // List Transactions screen (#21)
    // ***********************************
    // List Refund Requests screen (#22)
    // evalution screen (#23)
}
