package payments.boundaries;

import java.util.Stack;

import datastore.exceptions.EntityException;
import payments.common.Response;

public class Router {

    Stack<Frame> frames;

    public Router(Frame home) {
        frames = new Stack<Frame>();
        frames.add(home);
    }

    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    public void execute() throws EntityException {
        Frame temp = frames.peek();
        temp.display();
        Response object = temp.input();
        if (!object.success) {
            frames.pop();
        } else {
            temp.execute();

        }
    }
}
