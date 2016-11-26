package supporter.services.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supporter.models.Role;
import supporter.repositories.RoleRepository;

import java.util.List;

/**
 * Created by Ivaylo on 26-Nov-16.
 */
@Service
public class RoleServiceJpaImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return this.roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role findById(Integer roleId) {
        return this.roleRepository.findOne(roleId);
    }
}
