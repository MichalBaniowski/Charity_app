package pl.coderslab.charity.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.authentication.Role;
import pl.coderslab.charity.entity.authentication.User;
import pl.coderslab.charity.exception.ActionForbiddenException;
import pl.coderslab.charity.exception.ResourceNotFoundException;
import pl.coderslab.charity.repository.authentication.RoleRepository;
import pl.coderslab.charity.repository.authentication.UserRepository;
import pl.coderslab.charity.service.ActivationService;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final String DEFAULT_USER_ROLE = "ROLE_USER";
    private final String ADMIN_ROLE = "ROLE_ADMIN";
    private final String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ActivationService activationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           ActivationService activationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.activationService = activationService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public boolean saveUser(User user) {
        return saveUser(user, DEFAULT_USER_ROLE);
    }

    @Override
    public boolean saveUser(User user, String roleName) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        System.out.println(activationService.getActivationLink(savedUser));
        return savedUser.getId() != null;
    }

    @Override
    public boolean activateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
        user.setEnabled(true);
        user = userRepository.save(user);
        return user.isEnabled();
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
    public boolean updateUser(User editedUser, String oldPassword) throws AuthenticationException {
        User user = userRepository.findById(editedUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AuthenticationException("Wrong password");
        }
        editedUser.setEnabled(user.isEnabled());
        editedUser.setRoles(user.getRoles());
        editedUser.setEmail(user.getEmail());
        editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword()));
        userRepository.save(editedUser);
        return true;
    }

    @Override
    public boolean updateUserByAdmin(User user) {
        User userById = userRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setUsername(userById.getUsername());
        user.setPassword(userById.getPassword());
        user.setEmail(userById.getEmail());
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
        return !userRepository.existsById(user.getId());
    }

    @Override
    public Integer getUserCount() {
        Role role = roleRepository.findByName(DEFAULT_USER_ROLE);
        return userRepository.countAllByRoles(role);
    }
}
