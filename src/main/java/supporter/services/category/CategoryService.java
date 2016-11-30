package supporter.services.category;

import supporter.models.Category;

import java.util.List;

/**
 * Created by Ivaylo on 30-Nov-16.
 */
public interface CategoryService {
    List<Category> findAll(boolean sorted);
}
