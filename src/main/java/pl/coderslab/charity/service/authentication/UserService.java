package pl.coderslab.charity.service.authentication;

import pl.coderslab.charity.authentication_model.User;

public interface UserService {
    User findByUsername(String username);
    boolean saveUser(User user);
    boolean saveUser(User user, String roleName);
}
