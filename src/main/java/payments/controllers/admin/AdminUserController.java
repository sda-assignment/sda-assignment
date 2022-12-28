package payments.controllers.admin;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import datastore.Model;
import payments.controllers.response.UserResponse;
import payments.entities.User;

@RestController
public class AdminUserController {
    private Model<User> userModel;

    public AdminUserController(Model<User> userModel) {
        this.userModel = userModel;
    }

    @GetMapping("/admin/users")
    public ArrayList<UserResponse> listUsers() {
        return new ArrayList<>(userModel.select(u -> true).stream()
                .map(u -> new UserResponse(u)).collect(Collectors.toList()));
    }

    @GetMapping("/admin/users/{email}")
    public UserResponse getUser(@PathVariable("email") String email) {
        User user = userModel.selectOne(u -> u.email.equals(email));
        if (user == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a user with this email");
        return new UserResponse(user);
    }
}
