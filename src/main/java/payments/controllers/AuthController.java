package payments.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import payments.controllers.auth.Authenticator;
import payments.controllers.request.LogInBody;
import payments.controllers.request.SignUpBody;
import payments.controllers.response.Token;
import payments.entities.User;
import datastore.Model;

@RestController
public class AuthController {
    private Model<User> userModel;
    private Authenticator authenticator;

    public AuthController(Model<User> model, Authenticator authenticator) {
        this.userModel = model;
        this.authenticator = authenticator;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpBody body) {
        if (userModel.recordExists(u -> u.email.equals(body.email)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This email is already associated with an account");
        userModel.insert(new User(body.email, body.username, body.password, false, 0));
    }

    @PostMapping("/login")
    @ResponseBody
    public Token logIn(@RequestBody LogInBody body) {
        User user = userModel
                .selectOne(u -> u.email.equals(body.email) && u.password.equals(body.password));
        if (user != null) {
            return new Token(authenticator.createJwt(user.email, user.isAdmin));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
    }

}
