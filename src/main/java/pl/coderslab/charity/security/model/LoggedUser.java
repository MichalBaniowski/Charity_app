package pl.coderslab.charity.security.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class LoggedUser extends User {
    private pl.coderslab.charity.entity.authentication.User user;

    public LoggedUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities,
                      pl.coderslab.charity.entity.authentication.User user) {
        super(username, password, authorities);
        this.user = user;
    }
}
