package supporter.services.product;

import supporter.models.Product;

import java.util.List;

/**
 * Created by Ivaylo on 26-Nov-16.
 */
public interface ProductService {
    List<Product> findAll(boolean sorted);

    List<Product> findLatestFive();

    void create(Product product);

    boolean exists(int productId);

    Product findById(int productId);

    void edit(Product product);

    void deleteById(Integer productId);
}
