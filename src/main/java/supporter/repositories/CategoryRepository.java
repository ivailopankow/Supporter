package supporter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supporter.models.Category;

/**
 * Created by Ivaylo on 30-Nov-16.
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
