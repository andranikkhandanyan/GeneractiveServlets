package am.aca.generactive.controller;

import am.aca.generactive.model.User;
import am.aca.generactive.security.AppUserDetails;
import am.aca.generactive.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String getCurrentUserUsername() {
        Optional<User> user = userService.getCurrentUser();
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("no current user");
        }
        return user.get().getUsername();
    }
}
