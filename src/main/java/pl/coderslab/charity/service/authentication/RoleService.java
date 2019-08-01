package pl.coderslab.charity.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.authentication.Role;
import pl.coderslab.charity.repository.authentication.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    private final String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";

    public Role findByRole(String role) {
        return roleRepository.findByName(role);
    }

    public List<Role> getAllRolesButSuperAdmin() {
        return roleRepository.findAllByNameNot(SUPER_ADMIN_ROLE);
    }
}
