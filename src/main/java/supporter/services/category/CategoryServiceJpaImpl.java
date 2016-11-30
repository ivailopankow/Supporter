package supporter.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import supporter.exceptions.ErrorMessages;
import supporter.exceptions.category.CategoryCreationException;
import supporter.models.Category;
import supporter.models.binding.CategoryBindingModel;
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

    @Override
    public void create(CategoryBindingModel categoryBindingModel) throws CategoryCreationException {
        validate(categoryBindingModel, ErrorMessages.CATEGORY_NAME_ERROR);
        Category category = new Category(categoryBindingModel.getName());
        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category findById(Integer categoryId) {
        return this.categoryRepository.findOne(categoryId);
    }

    @Override
    public boolean exists(Integer categoryId) {
        return this.categoryRepository.exists(categoryId);
    }

    @Override
    public void edit(Integer id, CategoryBindingModel categoryBindingModel) throws CategoryCreationException {
        validate(categoryBindingModel, ErrorMessages.CATEGORY_NAME_ERROR);
        Category category = this.categoryRepository.findOne(id);
        category.setName(categoryBindingModel.getName());
        this.categoryRepository.saveAndFlush(category);
    }

    private void validate(CategoryBindingModel categoryBindingModel, String errorMessage) throws CategoryCreationException {
        String categoryBindingName = categoryBindingModel.getName();
        if (StringUtils.isEmpty(categoryBindingModel)) {
            throw new CategoryCreationException(errorMessage);
        }
    }
}
