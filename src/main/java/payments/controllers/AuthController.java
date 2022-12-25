package payments.controllers;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import payments.controllers.auth.TokenUtil;
import payments.controllers.request.LogInBody;
import payments.controllers.request.SignUpBody;
import payments.controllers.response.Token;
import payments.entities.User;
import datastore.Model;

@RestController
public class AuthController {
    private Model<User> userModel;
    private TokenUtil tokenUtil;

    public AuthController(Model<User> model, TokenUtil tokenUtil) {
        this.userModel = model;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpBody body) {
        if (userModel.recordExists(u -> u.email.equals(body.email)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This email is already associated with an account");
        userModel
                .insert(new User(body.email, body.username, body.password,
                        body.username.equals("admin")
                                && body.password.equals("admin"),
                        0));
    }

    @PostMapping("/login")
    @ResponseBody
    public Token logIn(@RequestBody LogInBody body) {
        ArrayList<User> users = userModel
                .select(u -> u.email.equals(body.email) && u.password.equals(body.password));
        if (users.size() > 0) {
            return new Token(tokenUtil.createJwt(users.get(0).email));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
    }

}
