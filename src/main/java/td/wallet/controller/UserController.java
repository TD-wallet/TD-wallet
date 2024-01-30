package td.wallet.controller;

import org.springframework.web.bind.annotation.*;
import td.wallet.models.User;
import td.wallet.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.findUserBydId(id);
    }
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAllUser();
    }

    @PostMapping("/new user")
    public User postNewUser(@RequestBody User user) {
        return userService.saveSingleUser(user);
    }
    @PostMapping("/new users")
    public List<User> postListOfUsers(@RequestBody List<User> users) {
        return userService.saveMultipleUser(users);
    }
    @DeleteMapping("/delete/{id}")
    public User deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }
}
