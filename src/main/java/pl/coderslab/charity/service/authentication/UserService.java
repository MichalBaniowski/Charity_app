package pl.coderslab.charity.service.authentication;

import pl.coderslab.charity.authentication_model.User;

public interface UserService {
    User findByUsername(String username);
    void saveUser(User user);
    void saveUser(User user, String roleName);
}
