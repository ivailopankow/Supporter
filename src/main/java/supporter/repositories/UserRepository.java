package supporter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import supporter.models.User;

/**
 * Created by Ivaylo on 12-Nov-16.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
