package supporter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supporter.models.User;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
