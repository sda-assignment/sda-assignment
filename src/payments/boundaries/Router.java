package payments.boundaries;

import java.util.HashMap;
import java.util.Scanner;

import datastore.exceptions.EntityException;

public class Router {
    private HashMap<FrameName, Frame> frames;
    private Frame currentFrame;

    public Router(Frame[] frameArr, Frame currentFrame) {
        frames = new HashMap<FrameName, Frame>();
        for (Frame frame : frameArr) {
            frames.put(frame.getFrameName(), frame);
        }
        this.currentFrame = currentFrame;
    }

    public void mainLoop() throws EntityException {
        Scanner scanner = new Scanner(System.in);
        while (currentFrame != null) {
            FrameName fName = currentFrame.displayWithInstruction(scanner);
            currentFrame = frames.get(fName);
        }
        scanner.close();

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
