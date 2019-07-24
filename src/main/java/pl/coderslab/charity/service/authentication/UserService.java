package pl.coderslab.charity.service.authentication;

import pl.coderslab.charity.authentication_model.Role;
import pl.coderslab.charity.authentication_model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    User findById(Long id);

    boolean saveUser(User user);

    boolean saveUser(User user, String roleName);

    List<User> findAllUsers();

    List<User> findAllAdmins();

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean deactivateUser(User user);

    boolean activateUser(User user);
}
