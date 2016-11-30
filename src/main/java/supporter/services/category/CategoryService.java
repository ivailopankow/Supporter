package supporter.services.category;

import supporter.exceptions.category.CategoryCreationException;
import supporter.models.Category;
import supporter.models.binding.CategoryBindingModel;

import java.util.List;

/**
 * Created by Ivaylo on 30-Nov-16.
 */
public interface CategoryService {
    List<Category> findAll(boolean sorted);

    void create(CategoryBindingModel categoryBindingModel) throws CategoryCreationException;
}
