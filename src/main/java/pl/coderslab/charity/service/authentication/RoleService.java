package pl.coderslab.charity.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.authentication_model.Role;
import pl.coderslab.charity.repository.authentication.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role findByRole(String role) {
        return roleRepository.findByName(role);
    }
}
