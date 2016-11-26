package supporter.services.role;

import supporter.models.Role;

import java.util.List;

/**
 * Created by Ivaylo on 26-Nov-16.
 */
public interface RoleService {
    Role findByName(String name);

    List<Role> findAll();

    Role findById(Integer roleId);
}
