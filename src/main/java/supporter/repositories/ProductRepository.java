package supporter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import supporter.models.Product;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
