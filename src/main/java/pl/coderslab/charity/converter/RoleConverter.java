package pl.coderslab.charity.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.charity.entity.authentication.Role;
import pl.coderslab.charity.repository.authentication.RoleRepository;

public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role convert(String id) {
        return roleRepository.findById(Long.parseLong(id)).get();
    }
}
