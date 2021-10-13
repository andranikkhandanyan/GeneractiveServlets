package am.aca.generactive.security;

import am.aca.generactive.model.User;
import am.aca.generactive.model.UserAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class AppUserDetails implements UserDetails {

    private User user;

    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public AppUserDetails(User user) {
        this.user = user;
    }

    public AppUserDetails(String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user == null ? authorities
                :
                user.getAuthorities()
                .stream()
                .map(userAuthority -> new SimpleGrantedAuthority(userAuthority.getAuthority()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user == null ? username : user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user == null || user.isEnabled();
    }
}
