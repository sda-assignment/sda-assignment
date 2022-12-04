package payments.boundaries;

import java.util.Stack;

import datastore.exceptions.EntityException;
import payments.common.Response;
import java.util.HashMap;
import java.util.Map;

public class Router {

    private Map<String, Frame> frames;
    private Frame currentFrame;

    public Router() {
        Frame[] frameArr = new Frame[] { new CancerHospitalDonationHandler(),
                new EtisalatInternetHandler(),
                new EtisalatRechargeHandler(), new MonthlyLandlineHandler(), new NgoDonationHandler(),
                new OrangeInternetHandler(), new OrangeRechargeHandler(), new QuarterlyLandlineHandler(),
                new SchoolDonationHandler(), new VodafoneInternetHandler(), new VodafoneRechargeHandler(),
                new WeInternetHandler(), new WeRechargeHandler(), new ErroneousHandler() };
        frames = new HashMap<String, Frame>();
        for (Frame frame : frameArr) {
            frames.put(frame.getFrameName(), frame);
        }
        this.currentFrame = frames.get("Home");
    }

    public void setFrame(String frameName) {
        if (!frames.containsKey(frameName)) {
            currentFrame = frames.get("Home");
            return;
        }
        currentFrame = frames.get(frameName);
    }

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
