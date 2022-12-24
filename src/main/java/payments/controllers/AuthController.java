package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import payments.controllers.request.LogInInfo;
import payments.controllers.request.SignUpInfo;
import payments.controllers.response.Token;
import payments.entities.User;
import datastore.Model;

@RestController
public class AuthController {
    private Model<User> userModel;
    private LogInSession logInSession;

    public AuthController(Model<User> model, LogInSession logInSession) {
        this.userModel = model;
        this.logInSession = logInSession;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpInfo signupInfo) {
        if (userModel.recordExists(u -> u.email.equals(signupInfo.email)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This email is already associated with an account");
        userModel
                .insert(new User(signupInfo.email, signupInfo.username, signupInfo.password,
                        signupInfo.username.equals("admin")
                                && signupInfo.password.equals("admin"),
                        0));
    }

    @PostMapping("/login")
    @ResponseBody
    public Token logIn(@RequestBody LogInInfo logInInfo) {
        ArrayList<User> users = userModel
                .select(u -> u.email.equals(logInInfo.email) && u.password.equals(logInInfo.password));
        if (users.size() > 0) {
            return new Token(logInSession.createJwt(users.get(0).email));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
    }

}
