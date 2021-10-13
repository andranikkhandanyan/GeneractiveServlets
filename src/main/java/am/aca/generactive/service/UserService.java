package am.aca.generactive.service;

import am.aca.generactive.model.User;
import am.aca.generactive.repository.UserRepository;
import am.aca.generactive.security.AppUserDetails;
import am.aca.generactive.security.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getCurrentUser() {
        return userRepository.findByUsername(SecurityUtils.getAuthentication().getUsername());
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
