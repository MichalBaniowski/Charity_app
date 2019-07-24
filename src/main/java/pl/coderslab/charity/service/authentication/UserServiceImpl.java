package pl.coderslab.charity.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.authentication_model.Role;
import pl.coderslab.charity.authentication_model.User;
import pl.coderslab.charity.exception.ActionForbiddenException;
import pl.coderslab.charity.exception.ElementNotFoundException;
import pl.coderslab.charity.repository.authentication.RoleRepository;
import pl.coderslab.charity.repository.authentication.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final String DEFAULT_USER_ROLE = "ROLE_USER";
    private final String ADMIN_ROLE = "ROLE_ADMIN";
    private final String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("User not found"));
    }

    @Override
    public boolean saveUser(User user) {
        return saveUser(user, DEFAULT_USER_ROLE);
    }

    @Override
    public boolean saveUser(User user, String roleName) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true); //TODO send email with activation token
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        return savedUser.getId() != null;
    }



    @Override
    public List<User> findAllUsers() {
        Role role = roleRepository.findByName(DEFAULT_USER_ROLE);
        return userRepository.findAllByRoles(role);
    }

    @Override
    public List<User> findAllAdmins() {
        Role role = roleRepository.findByName(ADMIN_ROLE);
        return userRepository.findAllByRoles(role);
    }

    @Override
    public boolean updateUser(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(User user) {
        Role role = roleRepository.findByName(SUPER_ADMIN_ROLE);
        if(user.getRoles().contains(role)) {
            throw new ActionForbiddenException("Cannot delete super admin");
        }
        userRepository.delete(user);
        return userRepository.existsById(user.getId());
    }

    @Override
    public boolean deactivateUser(User user) {
        user.setEnabled(false);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean activateUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }
}
