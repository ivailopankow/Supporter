package supporter.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import supporter.models.Category;
import supporter.repositories.CategoryRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ivaylo on 30-Nov-16.
 */
@Service
public class CategoryServiceJpaImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll(boolean sorted) {
        List<Category> entities = this.categoryRepository.findAll();
        if (sorted) {
            entities = entities.stream()
                    .sorted(Comparator.comparingInt(Category::getId))
                    .collect(Collectors.toList());
        }
        return entities;
    }
}
