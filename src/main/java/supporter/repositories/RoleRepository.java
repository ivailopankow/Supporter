package supporter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supporter.models.Role;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
