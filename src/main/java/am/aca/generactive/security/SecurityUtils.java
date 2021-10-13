package am.aca.generactive.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    public static AppUserDetails getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (AppUserDetails) authentication.getPrincipal();
    }
}
