package pl.coderslab.charity.authentication_model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class CurrentUser extends User {
    private pl.coderslab.charity.authentication_model.User user;

    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.coderslab.charity.authentication_model.User user) {
        super(username, password, authorities);
        this.user = user;
    }
}
