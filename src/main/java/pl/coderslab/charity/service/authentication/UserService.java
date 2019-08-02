package pl.coderslab.charity.service.authentication;

import pl.coderslab.charity.entity.authentication.User;

import javax.naming.AuthenticationException;
import java.util.List;

public interface UserService {
    User findByUsername(String username);

    User findById(Long id);

    boolean saveUser(User user);

    boolean saveUser(User user, String roleName);

    List<User> findAllUsers();

    List<User> findAllAdmins();

    boolean updateUser(User user, String newPassword) throws AuthenticationException;

    boolean updateUserByAdmin(User user);

    boolean deleteUser(User user);

    Integer getUserCount();

    boolean activateUser(Long userId);
}
