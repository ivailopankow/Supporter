package supporter.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import supporter.models.Product;
import supporter.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivaylo on 26-Nov-16.
 */
@Service
public class ProductServiceJpaImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll(boolean sorted) {
        List<Product> result = productRepository.findAll();
        if (sorted) {
            return result.stream()
                    .sorted()
                    .collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public List<Product> findLatestFive() {
        return this.productRepository.findLatestFive(new PageRequest(0, 5));
//        return null;
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
