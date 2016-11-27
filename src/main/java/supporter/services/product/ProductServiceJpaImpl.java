package supporter.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supporter.models.Product;
import supporter.repositories.ProductRepository;

import java.util.List;

/**
 * Created by Ivaylo on 26-Nov-16.
 */
@Service
public class ProductServiceJpaImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void create(Product product) {
        this.productRepository.saveAndFlush(product);
    }

    @Override
    public boolean exists(int productId) {
        return this.productRepository.exists(productId);
    }

    @Override
    public Product findById(int productId) {
        return this.productRepository.findOne(productId);
    }

    @Override
    public void edit(Product product) {
        this.productRepository.saveAndFlush(product);
    }

    @Override
    public void deleteById(Integer productId) {
        this.productRepository.delete(productId);
    }
}
