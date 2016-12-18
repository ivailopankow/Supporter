package supporter.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import supporter.models.Product;

import java.util.List;

/**
 * Created by Ivaylo on 22-Nov-16.
 */
public interface ProductRepository extends JpaRepository<Product, Integer>{
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.producer ORDER BY p.date DESC")
    List<Product> findLatestFive(Pageable pageable);

}
