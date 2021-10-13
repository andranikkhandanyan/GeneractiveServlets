package am.aca.generactive.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user_authority")
public class UserAuthority implements Serializable {

    @Id
    private String username;

    @Id
    private String authority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthority that = (UserAuthority) o;
        return Objects.equals(username, that.username) && Objects.equals(authority, that.authority);
    }

    @Override
    public String toString() {
        return "UserAuthority{" +
                "username='" + username + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }

}
